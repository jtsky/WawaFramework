package com.duiba.component_base.util;

import android.app.Application;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.arch.lifecycle.ViewModelStores;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import static android.arch.lifecycle.HolderFragment.holderFragmentFor;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/7/2-19:14
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GlobalViewModelStores {
    @NonNull
    @MainThread
    public static ViewModelStore of(@NonNull Application application) {
        return ((ViewModelStoreOwner) application).getViewModelStore();

    }
}
