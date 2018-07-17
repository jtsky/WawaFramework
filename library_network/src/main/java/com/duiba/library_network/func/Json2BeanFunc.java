package com.duiba.library_network.func;


import com.duiba.library_network.bean.TestCommResponse;
import com.duiba.library_network.util.GsonUtil;
import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * Created by wlw-97 on 2016/11/21.
 */

public class Json2BeanFunc<O> implements Function<Object, Observable<TestCommResponse<O>>> {
    Class<O> mClass;

    public Json2BeanFunc(Class<O> aClass) {
        mClass = aClass;
    }


    @Override
    public Observable<TestCommResponse<O>> apply(Object result) throws Exception {

        if (result != null) {
            return Observable.just(GsonUtil.json2Object(result.toString(), mClass));
        }

        return Observable.empty();
    }
}
