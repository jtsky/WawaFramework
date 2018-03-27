package com.duiba.component_base;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * @author: jintai
 * @time: 2018/3/27-11:27
 * @Email: jintai@qccr.com
 * @desc: Stetho工具类
 */
public class StethoUtil {
   public static void init(Application application) {
        //chrome 调试
        Stetho.initializeWithDefaults(application);
    }
}
