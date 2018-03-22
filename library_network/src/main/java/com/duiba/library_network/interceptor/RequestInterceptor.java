package com.duiba.library_network.interceptor;

import android.util.Log;

import com.jin.library_network.BuildConfig;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Jin on 2016/3/24.
 * http://www.jianshu.com/p/2710ed1e6b48
 */
public class RequestInterceptor implements Interceptor {
    private static final String TAG = "RequestInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Response response;
        try {
            response = chain.proceed(creatNewRequest(original));
        } catch (SocketTimeoutException e) {
            return createErrorRespon(original);
        }
        Logger.t(TAG).i(String.format("\nResponse====>%s\n%s", response, response.headers()));

        return createNewRespon(original, response);
    }


    private Response createNewRespon(Request request, Response response) throws IOException {
        //如果网络状态不等于200 重定向为200直接返回
        if (response.code() != 200) {
            return new Response.Builder()
                    .code(200)
                    .message(response.message())
                    .protocol(Protocol.HTTP_1_1)
                    .request(request)
                    .body(response.body())
                    .build();
        }

        FormBody formBody = (FormBody) request.body();
        if(formBody == null){
            return response;
        }
        String responBody = response.body().string();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        JSONObject oldJsonObject;
        try {
            oldJsonObject = new JSONObject(responBody);
            JSONObject result = new JSONObject(oldJsonObject.getString("result"));
            for (int i = 0; i < formBody.size(); i++) {
                if ("oper".equals(formBody.name(i))) {
                    result.put("action", formBody.value(i));
                }
                if ("type".equals(formBody.name(i))) {
                    result.put("controller", formBody.value(i));
                }
            }
            JSONObject newJsonObject = new JSONObject();
            newJsonObject.put("result", result);
            ResponseBody responseBody = ResponseBody.create(mediaType, newJsonObject.toString());
            return new Response.Builder()
                    .code(response.code())
                    .message(response.message())
                    .protocol(Protocol.HTTP_1_1)
                    .request(request)
                    .body(responseBody)
                    .build();
        } catch (JSONException e) {
            JSONObject error = new JSONObject();
            try {
                error.put("code", "409");
                error.put("msg", "返回json格式错误");
                error.put("content", responBody);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            ResponseBody responseBody = ResponseBody.create(mediaType, error.toString());
            return new Response.Builder()
                    .code(response.code())
                    .message("返回json格式错误")
                    .protocol(Protocol.HTTP_1_1)
                    .request(request)
                    .body(responseBody)
                    .build();
        }

    }

    private Response createErrorRespon(Request request) {
        FormBody formBody = (FormBody) request.body();
        String action = "";
        String controller = "";
        for (int i = 0; i < formBody.size(); i++) {
            if ("oper".equals(formBody.name(i))) {
                action = formBody.value(i);
            }
            if ("type".equals(formBody.name(i))) {
                controller = formBody.value(i);
            }
        }
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        JSONObject error = new JSONObject();
        try {
            error.put("code", "408");
            error.put("msg", "网络请求超时" + "action==>" + action + "  controller==>" + controller);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        ResponseBody responseBody = ResponseBody.create(mediaType, error.toString());
        return new Response.Builder()
                .code(408)
                .message("网络请求超时" + "action==>" + action + "  controller==>" + controller)
                .protocol(Protocol.HTTP_1_1)
                .request(request)
                .body(responseBody)
                .build();


    }

    private Request creatNewRequestBak(Request oldRequest) throws UnsupportedEncodingException {
        //请求体定制：统一添加source参数
        Request.Builder requestBuilder = oldRequest.newBuilder();
        //requestBuilder.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        if (oldRequest.body() instanceof FormBody) {
            FormBody.Builder newFormBodyBuilder = new FormBody.Builder();
            FormBody oldFormBody = (FormBody) oldRequest.body();
            Map<String, String> params = new HashMap<>();
            params.put("key", "value");
            for (int i = 0; i < oldFormBody.size(); i++) {
                params.put(oldFormBody.name(i), oldFormBody.value(i));
                //newFormBodyBuilder.addEncoded(oldFormBody.name(i), oldFormBody.value(i));
            }

            Logger.t(TAG).i(TAG, "-------------------------post请求参数Start--------------");
            if (BuildConfig.DEBUG) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    Log.i(TAG, "name=====>" + entry.getKey() + "   value====>" + entry.getValue());
                }
            }

            Logger.t(TAG).i(TAG, "-------------------------post请求参数End--------------");

            for (Map.Entry<String, String> entry : params.entrySet()) {
                newFormBodyBuilder.addEncoded(URLEncoder.encode(entry.getKey(), "utf-8"), URLEncoder.encode(entry.getValue(), "utf-8"));
            }

            FormBody newFormBody = newFormBodyBuilder.build();


            requestBuilder.method(oldRequest.method(), newFormBody);
        }
        Request newRequest = requestBuilder.build();

        Logger.t(TAG).i(String.format("request===>%s\n%s", newRequest, newRequest.headers()));

        return newRequest;
    }

    private Request creatNewRequest(Request oldRequest) throws UnsupportedEncodingException {
        FormBody oldFormBody = (FormBody) oldRequest.body();
        if (oldFormBody == null) {
            return new Request.Builder()
                    .url(oldRequest.url().toString())
                    .addHeader("cache-control", "no-cache")
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();
        }

        Map<String, String> params = new HashMap<>();
        params.put("key", "value");
        for (int i = 0; i < oldFormBody.size(); i++) {
            params.put(oldFormBody.name(i), oldFormBody.value(i));
        }
        //日志输出

        Logger.t(TAG).i("-------------------------post请求参数Start--------------");
        if (BuildConfig.DEBUG) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                Log.i(TAG, "name=====>" + entry.getKey() + "   value====>" + entry.getValue());
            }
        }


        Logger.t(TAG).i("-------------------------post请求参数End--------------");


        String content = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            content += entry.getKey() + "=" + entry.getValue() + "&";
        }
        content = content.substring(0, content.length() - 1);
        Logger.t(TAG).i(TAG, "构造的post参数====》" + content);
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, content);
        Request newRequest = new Request.Builder()
                .url(oldRequest.url().toString())
                .post(body)
                .addHeader("cache-control", "no-cache")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        Logger.t(TAG).i(String.format("\nrequest===>%s\n%s", newRequest, newRequest.headers()));

        return newRequest;
    }


}
