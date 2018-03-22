package com.duiba.library_network.transform;

import com.duiba.library_network.bean.TestCommResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;


/**
 * Created by Jin on 2016/3/9.
 */
public class RxCommonResultTransform<T> implements ObservableTransformer<JSONObject, TestCommResponse<T>> {

    @Override
    public ObservableSource<TestCommResponse<T>> apply(Observable<JSONObject> oldObservable) {
        return oldObservable
                .map(result -> {
                    TestCommResponse<T> commonResult = null;
                    if (result != null) {
                        try {
                            commonResult = new Gson().fromJson(result.toString(), TestCommResponse.class);
                        } catch (Exception e) {
                            Observable.error(e);
                        }
                        return commonResult;
                    }
                    return commonResult;
                });
    }
}
