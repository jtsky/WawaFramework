package com.duiba.library_network.interceptor;

import android.text.TextUtils;

import com.duiba.library_network.BuildConfig;
import com.duiba.library_network.util.MockDataGenerator;
import com.facebook.stetho.common.LogUtil;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/5/9-10:55
 * 描    述：自定义okhttp拦截器，可定制接口伪造Http响应数据
 * 修订历史：
 * ================================================
 */

public final class MockDataApiInterceptor implements Interceptor {
    public static final String TAG = MockDataApiInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        String path = chain.request().url().uri().getPath();
        LogUtil.d(TAG, "intercept: path=" + path);
        response = interceptRequestWhenDebug(chain);
        if (null == response) {
            LogUtil.i(TAG, "intercept: null == response");
            response = chain.proceed(chain.request());
        }
        return response;
    }

    /**
     * 测试环境下拦截需要的接口请求，伪造数据返回
     *
     * @param chain 拦截器链
     * @return 伪造的请求Response，有可能为null
     */
    private Response interceptRequestWhenDebug(Chain chain) {
        Response response = null;
        if (BuildConfig.DEBUG) {
            Request request = chain.request();
            response = getMockEventListResponse(chain,request);

        }
        return response;
    }


    /**
     * 伪造活接口数据
     *
     * @param request 用户的请求
     * @return 伪造的活动列表HTTP响应
     */
    private Response getMockEventListResponse(Chain chain, Request request) {
        Response response;
        String path = request.url().uri().getPath();
        String data;
        String assetPath = "";
        switch (path) {
            case "/api/test0":
                assetPath = "mock/test0.json";
                break;
            case "/api/test1":
                assetPath = "mock/test1.json";
                break;
            default:
                //不再匹配名单中 直接返回上一次的response
                try {
                    return chain.proceed(chain.request());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        data = MockDataGenerator.getMockDataFromJsonFile(assetPath);
        response = getHttpSuccessResponse(request, data);
        return response;
    }

    /**
     * 根据数据JSON字符串构造HTTP响应，在JSON数据不为空的情况下返回200响应，否则返回500响应
     *
     * @param request  用户的请求
     * @param dataJson 响应数据，JSON格式
     * @return 构造的HTTP响应
     */
    private Response getHttpSuccessResponse(Request request, String dataJson) {
        Response response;
        if (TextUtils.isEmpty(dataJson)) {
            LogUtil.w(TAG, "getHttpSuccessResponse: dataJson is empty!");

            response = new Response.Builder()
                    .code(500)
                    .protocol(Protocol.HTTP_1_0)
                    .message("dataJson is empty!")
                    .request(request)
                    .body(ResponseBody.create(MediaType.parse("application/json"), ""))
                    //必须设置protocol&request，否则会抛出异常
                    .build();
        } else {
            response = new Response.Builder()
                    .code(200)
                    .message(dataJson)
                    .request(request)
                    .protocol(Protocol.HTTP_1_0)
                    .addHeader("Content-Type", "application/json")
                    .body(ResponseBody.create(MediaType.parse("application/json"), dataJson))
                    .build();
        }
        return response;
    }

    private Response getHttpFailedResponse(Chain chain, int errorCode, String errorMsg) {
        if (errorCode < 0) {
            throw new IllegalArgumentException("httpCode must not be negative");
        }
        Response response;
        response = new Response.Builder()
                .code(errorCode)
                .message(errorMsg)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .build();
        return response;
    }
}


