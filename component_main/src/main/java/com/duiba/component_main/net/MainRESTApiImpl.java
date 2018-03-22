package com.duiba.component_main.net;

import android.app.ProgressDialog;

import com.duiba.component_main.bean.TestBean;
import com.duiba.library_network.RetrofitHelp;
import com.duiba.library_network.bean.TestCommResponse;
import com.duiba.library_network.transform.RxCommonResultTransform;
import com.duiba.library_network.transform.RxResponTransform;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: jintai
 * @time: 2018/3/21-16:12
 * @Email: jintai@qccr.com
 * @desc:主模块组件网络接口
 */
public class MainRESTApiImpl {
    static MainRESTApi api = RetrofitHelp.getRetrofit().create(MainRESTApi.class);

    public static Observable<TestCommResponse<List<TestBean>>> getData(String type, ProgressDialog progressDialog) {
        return api.getData(type)
                .compose(new RxResponTransform(progressDialog))
                .compose(new RxCommonResultTransform<List<TestBean>>());
    }
}
