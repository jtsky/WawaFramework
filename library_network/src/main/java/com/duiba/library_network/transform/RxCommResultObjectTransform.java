package com.duiba.library_network.transform;

import android.annotation.SuppressLint;

import com.duiba.library_network.bean.TestCommResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;


/**
 * @desc data 为JsonObject时的转换器
 * @param <R>
 */
public class RxCommResultObjectTransform<R> implements ObservableTransformer<JSONObject, TestCommResponse<R>> {

    @SuppressLint("CheckResult")
    @Override
    public ObservableSource<TestCommResponse<R>> apply(Observable<JSONObject> oldObservable) {
        return oldObservable
                .map(result -> {
                    TestCommResponse<R> commonResult = null;
                    if (result != null) {
                        try {
                            commonResult = new Gson().fromJson(result.toString(), TestCommResponse.class);
                        } catch (Exception e) {
                            Observable.error(e);
                        }
                    }
                    return commonResult;
                });
    }
}
