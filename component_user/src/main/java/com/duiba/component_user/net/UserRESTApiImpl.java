package com.duiba.component_user.net;

import android.app.ProgressDialog;

import com.duiba.component_user.bean.GankBean;
import com.duiba.library_network.RetrofitHelp;
import com.duiba.library_network.bean.TestCommResponse;
import com.duiba.library_network.transform.RxCommResultArrayTransform;
import com.duiba.library_network.transform.RxResponTransform;


import java.util.List;

import io.reactivex.Observable;

/**
 * @author: jintai
 * @time: 2018/3/21-16:12
 * @Email: jintai@duiba.com.cn
 * @desc:主模块组件网络接口
 */
public class UserRESTApiImpl {
    static UserRESTApi api = RetrofitHelp.getRetrofit().create(UserRESTApi.class);

    public static Observable<TestCommResponse<List<GankBean>>> getData(String type, ProgressDialog progressDialog) {
        return api.getData(type)
                .compose(new RxResponTransform(progressDialog))
                .compose(new RxCommResultArrayTransform<>(GankBean.class));
    }
}
