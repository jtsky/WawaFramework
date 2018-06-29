package com.duiba.component_user.home.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.duiba.component_user.bean.GankBean;

/**
 * @author: jintai
 * @time: 2018/3/23-14:45
 * @Email: jintai@qccr.com
 * @desc:存储跟页面有关的数据元素
 */
public class UserViewModel extends ViewModel {
    private MutableLiveData<String> mName;

    private MutableLiveData<GankBean> mGankBean;

    @NonNull
    public MutableLiveData<String> getName() {
        if (mName == null) {
            mName = new MutableLiveData<>();
        }
        return mName;
    }


    @NonNull
    public MutableLiveData<GankBean> getGankBean() {
        if (mGankBean == null) {
            mGankBean = new MutableLiveData<>();
        }
        return mGankBean;
    }
}
