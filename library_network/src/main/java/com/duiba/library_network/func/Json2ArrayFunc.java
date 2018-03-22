package com.duiba.library_network.func;


import com.duiba.library_network.bean.TestCommResponse;
import com.duiba.library_network.util.GsonUtil;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


/**
 * Created by jint
 */
public class Json2ArrayFunc<O> implements Function<Object, ObservableSource<TestCommResponse<List<O>>>> {
    Class<O> mClass;

    public Json2ArrayFunc(Class<O> aClass) {
        mClass = aClass;
    }


    @Override
    public ObservableSource<TestCommResponse<List<O>>> apply(Object result) throws Exception {
        if (result != null) {
            return Observable.just(GsonUtil.json2Array(result.toString(), mClass));
        }
        return Observable.empty();
    }
}
