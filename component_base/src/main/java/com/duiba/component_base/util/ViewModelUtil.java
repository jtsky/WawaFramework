package com.duiba.component_base.util;

import android.arch.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/7/2-16:52
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ViewModelUtil {
    private static Map<String, ViewModel> mViewModelMap = new HashMap<>();

    public static void put(String key, ViewModel value) {
        if (mViewModelMap.containsKey(key) || value == null) {
            return;
        }

        mViewModelMap.put(key, value);
    }

    public static void remove(String key) {
        if (mViewModelMap.containsKey(key)) {
            mViewModelMap.remove(key);
        }
    }

    public static ViewModel get(String key) {
        if (mViewModelMap.containsKey(key)) {
            return mViewModelMap.get(key);
        }

        return null;
    }

}
