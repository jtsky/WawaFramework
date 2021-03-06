package com.duiba.component_base.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/7/2-19:22
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GlobalViewModel extends AndroidViewModel {
    private MutableLiveData<String> mValue;

    public GlobalViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    public MutableLiveData<String> getValue() {
        if (mValue == null) {
            mValue = new MutableLiveData<>();
        }
        return mValue;
    }
}
