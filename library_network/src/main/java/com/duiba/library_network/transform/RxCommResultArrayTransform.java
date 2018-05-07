package com.duiba.library_network.transform;

import android.annotation.SuppressLint;
import android.util.Log;

import com.duiba.library_network.bean.TestCommResponse;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import ikidou.reflect.TypeBuilder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;


/**
 * @desc data 为JsonArray时的转换器
 * @param <R>
 */
public class RxCommResultArrayTransform<R> implements ObservableTransformer<JSONObject, TestCommResponse<R>> {
    Class mClazz;

    public RxCommResultArrayTransform(Class clazz) {
        this.mClazz = clazz;
    }

    @SuppressLint("CheckResult")
    @Override
    public ObservableSource<TestCommResponse<R>> apply(Observable<JSONObject> oldObservable) {
        return oldObservable
                .map(result -> {
                    Type type = TypeBuilder
                            .newInstance(TestCommResponse.class)
                            .beginSubType(List.class)
                            .addTypeParam(mClazz)
                            .endSubType()
                            .build();

                    TestCommResponse<R> commonResult = null;
                    if (result != null) {
                        try {
                            commonResult = new Gson().fromJson(result.toString(), type);
                        } catch (Exception e) {
                            Observable.error(e);
                        }
                    }
                    return commonResult;
                });
    }
}
