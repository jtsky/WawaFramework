package com.duiba.library_network.interceptor;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wlw-97
 * @title addQueryParameterInterceptor
 * @description
 * @modifier
 * @date
 * @since 2016/6/17 14:14
 **/
public class addQueryParameterInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request request;
        String method = originalRequest.method();
        Headers headers = originalRequest.headers();


        HttpUrl modifiedUrl = originalRequest.url().newBuilder()

                // Provide your custom parameter here
                .addQueryParameter("platform", "android")
                .addQueryParameter("version", "1.0.0")
                .build();
        request = originalRequest
                .newBuilder()
                .url(modifiedUrl)
                .build();
        return chain.proceed(request);
    }
}
