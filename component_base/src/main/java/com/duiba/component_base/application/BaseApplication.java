package com.duiba.component_base.application;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.duiba.component_base.BuildConfig;
import com.duiba.component_base.config.AppDefine;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author: jintai
 * @time: 2018/3/20-20:10
 * @Email: jintai@duiba.com.cn
 * @desc:
 */
public class BaseApplication extends Application {
    /**
     * 微信api
     */
    private IWXAPI mWxAPI;
    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initARouterAndLog();
        initWechatLogin();
    }

    private void initARouterAndLog() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
        //日志初始化
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void initWechatLogin() {
        mWxAPI = WXAPIFactory.createWXAPI(this, AppDefine.WECHAT_APPID, true);
        mWxAPI.registerApp(AppDefine.WECHAT_APPID);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @NonNull
    public  IWXAPI getWxAPI() {
        return mWxAPI;
    }

    public static BaseApplication getApplication() {
        return application;
    }
}
