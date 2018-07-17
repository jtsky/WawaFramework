package com.duiba.component_base.application;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.duiba.component_base.BuildConfig;
import com.duiba.component_base.StethoUtil;
import com.duiba.component_base.config.AppDefine;
import com.duiba.component_base.util.GlobalViewModelProviders;
import com.duiba.component_base.vm.GlobalViewModel;
import com.duiba.library_network.util.ApplicationUtil;
import com.duiba.rxnetwork.RxNetwork;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

import com.duiba.wsmanager.WsManager;
import com.duiba.wsmanager.WsManagerFactory;
import com.duiba.wsmanager.listener.AbstractWsStatusListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: jintai
 * @time: 2018/3/20-20:10
 * @Email: jintai@duiba.com.cn
 * @desc:BaseApplication
 */
public class BaseApplication extends Application implements ViewModelStoreOwner {
    /**
     * 微信api
     */
    private IWXAPI mWxAPI;
    private static BaseApplication application;
    private static final String TAG = "BaseApplication";
    Disposable mNetWorkChangeDisposable;
    Map<String, ViewModel> mViewModelMap = new HashMap<>();
    /**
     * Application ViewModelStore
     */
    private ViewModelStore mViewModelStore;

    @Override
    @NonNull
    public ViewModelStore getViewModelStore() {
        if (mViewModelStore == null) {
            mViewModelStore = new ViewModelStore();
        }
        return mViewModelStore;
    }

    //https://stackoverflow.com/questions/44148966/androidviewmodel-vs-viewmodel
    private void initGlobalVewModel() {
        mViewModelMap.put(GlobalViewModel.class.getSimpleName(), GlobalViewModelProviders.of(this).get(GlobalViewModel.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationUtil.instance = this;
        application = this;
        //工具类初始化
        Utils.init(application);
        //LeakCanary install
        LeakCanary.install(this);
        //chrome 调试
        StethoUtil.init(this);
        initARouterAndLog();
        initWechatLogin();
        initNetworkStatusChangeObservable();
        initFragmentation();
        initGlobalVewModel();
    }

    /**
     * 初始化Fragment管理
     */
    private void initFragmentation() {
        // 建议在Application里初始化
        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(@NonNull Exception e) {
                        Logger.e(e.getMessage());
                    }
                })
                .install();
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


    private void initNetworkStatusChangeObservable() {
        mNetWorkChangeDisposable = RxNetwork.stream(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hasNetWork -> {
                    WsManager wsManager = WsManagerFactory.getWsManager();
                    if (wsManager == null) {
                        return;
                    }
                    List<AbstractWsStatusListener> wsStatusListeners = wsManager.getWsStatusListeners();
                    if (wsStatusListeners == null) {
                        return;
                    }

                    if (hasNetWork) {
                        for (AbstractWsStatusListener abstractWsStatusListener : wsStatusListeners) {
                            abstractWsStatusListener.onNetOpen();
                        }
                    } else {
                        for (AbstractWsStatusListener abstractWsStatusListener : wsStatusListeners) {
                            abstractWsStatusListener.onNetClosed();
                        }
                    }

                });
    }

    private void initWechatLogin() {
        mWxAPI = WXAPIFactory.createWXAPI(this, AppDefine.WECHAT_APPID, true);
        mWxAPI.registerApp(AppDefine.WECHAT_APPID);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            MultiDex.install(this);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public IWXAPI getWxAPI() {
        return mWxAPI;
    }

    public static BaseApplication getApplication() {
        return application;
    }

    public ViewModel getGlobalViewModel(String key) {
        if (!mViewModelMap.containsKey(key)) {
            throw new NullPointerException("不存在全局的" + key + "类型的ViewModel");
        }
        return mViewModelMap.get(key);
    }

}
