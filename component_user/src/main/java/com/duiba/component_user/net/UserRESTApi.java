package com.duiba.component_user.net;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * @author: jintai
 * @time: 2018/3/21-16:12
 * @Email: jintai@duiba.com.cn
 * @desc:主模块组件网络接口 不要直接调用 应该调用MainRestApiImpl的静态方法
 */
@Deprecated
public interface UserRESTApi {
    @GET("api/data/{type}/10/1")
    Observable<String> getData(@Path("type") String type);

    @POST("login/login")
    Observable<String> getBannersCall(@QueryMap Map<String, String> map);
}
