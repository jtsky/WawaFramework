package com.duiba.component_base.util;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.lifecycle.ViewModelStores;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/7/2-19:10
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GlobalViewModelProviders extends ViewModelProviders {
    @NonNull
    @MainThread
    public static ViewModelProvider of(@NonNull Application application) {
        return of(application, null);
    }

    @NonNull
    @MainThread
    public static ViewModelProvider of(@NonNull Application application,
                                       @Nullable ViewModelProvider.Factory factory) {
        if (factory == null) {
            factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
        }
        return new ViewModelProvider(GlobalViewModelStores.of(application), factory);
    }
}
