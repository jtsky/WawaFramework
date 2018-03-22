package com.duiba.library_network;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RESTApi {


    @GET("api/data/{type}/10/1")
    Observable<String> getData(@Path("type") String type);

}
