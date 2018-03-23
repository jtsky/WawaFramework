package com.duiba.component_base.component;

import android.arch.lifecycle.ViewModel;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.view.Window;

import com.duiba.component_base.BuildConfig;
import com.duiba.component_base.lifecycle.ActivityLifeCycleEvent;
import com.duiba.component_base.util.EventBusUtil;
import com.duiba.library_common.bean.Event;
import com.duiba.library_common.bean.EventCode;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

import io.reactivex.subjects.PublishSubject;
import wsmanager.WsManager;
import wsmanager.WsManagerFactory;
import wsmanager.listener.AbstractWsStatusListener;

/**
 * @author jintai
 * @date 2018/03/23
 * @desc 基础的BaseActivity
 */
public abstract class BaseActivity<Model extends ViewModel, V extends DuibaMvpView, P extends DuibaMvpPresenter<Model, V>>
        extends MvpActivity<V, P> {
    protected final String TAG = this.getClass().getSimpleName();
    protected final String TAG_CURRENR = "CurrentActivity";

    protected final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();
    /**
     * 基础的viewModel
     */
    protected Model mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //竖向
        int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        if (getRequestedOrientation() != orientation) {
            setRequestedOrientation(orientation);
        }
        //NO_TITLE
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //输出当前activity
        if (BuildConfig.DEBUG) {
            Logger.t(TAG_CURRENR).v("===CurrentActivity====>" + this.getClass().getCanonicalName());
        }

        //onCreat时候注册EventBus事件
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        //初始化并订阅ViewModel
        subscribeViewModel();
        if (mViewModel == null) {
            throw new NullPointerException("请初始化并订阅mViewModel");
        }

        //初始化webSocket
        if (isOpenWebSocket()) {
            createWSStatusListener();
            initWebSocket();
        }


        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE);
    }

    /**
     * 初始化WebSocket
     */
    public static final String WEBSOCKET_URL = "ws://echo.websocket.org";
    protected AbstractWsStatusListener mWsAbstractStatusListener;

    private void initWebSocket() {
        if (mWsAbstractStatusListener == null) {
            throw new NullPointerException("请在子Activity中重写createWSStatusListener方法并为mWsAbstractStatusListener赋值");
        }
        WsManager wsManager = WsManagerFactory.createWsManager(getApplicationContext(), WEBSOCKET_URL);
        wsManager.setWsStatusListener(mWsAbstractStatusListener);
        wsManager.startConnect();
    }


    /**
     * 是否启用webSocket并自动打开webSocket 默认返回false
     * 如果要启用 请重写该方法并返回true
     */
    public boolean isOpenWebSocket() {
        return false;
    }

    /**
     * 如果isOpenWebSocket  一定要重写该方法 并为mWsAbstractStatusListener赋值
     */
    public void createWSStatusListener() {

    }


    /**
     * 初始化并订阅ViewModel
     */
    public abstract void subscribeViewModel();

    /**
     * 是否注册EventBus
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    /**
     * 不要重写该方法 不设置权限为private的原因是因为 eventBus规定订阅方法必须为public
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event<Object> event) {
        if (registerEventCode() == null) {
            throw new NullPointerException("请实现相应的registerEventCode()方法");
        }

        if (event != null && Arrays.asList(registerEventCode()).contains(event.getEventCode())) {
            receiveEvent(event);
        }
    }

    /**
     * 不要重写该方法 不设置权限为private的原因是因为 eventBus规定订阅方法必须为public
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(
            Event<Object> event) {
        if (registerEventCode() == null) {
            throw new NullPointerException("请实现相应的registerEventCode()方法");
        }

        if (event != null && Arrays.asList(registerEventCode()).contains(event.getEventCode())) {
            receiveStickyEvent(event);
        }
    }

    /**
     * isRegisterEventBus 为true时需要重写
     * 注册指定的EventCode 并接收相应的事件
     * isRegisterEventBus() 返回true时重写
     */
    public EventCode[] registerEventCode() {
        return null;
    }

    /**
     * 接收到分发到事件 当重写isRegisterEventBus并返回true时重写
     *
     * @param event 事件
     */
    public void receiveEvent(Event<Object> event) {
        if (!isRegisterEventBus()) {
            throw new RuntimeException(
                    this.getClass().getSimpleName() + "==>请重写isRegisterEventBus()方法并返回true");
        }
    }

    /**
     * 接受到分发的粘性事件 当重写isRegisterEventBus并返回true时重写
     *
     * @param event 粘性事件
     */
    public void receiveStickyEvent(Event<Object> event) {
        if (!isRegisterEventBus()) {
            throw new RuntimeException(
                    this.getClass().getSimpleName() + "==>请重写isRegisterEventBus()方法并返回true");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityLifeCycleEvent.RESUME);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP);
        super.onStop();
    }


    @Override
    protected void onPause() {
        super.onPause();
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
        if (isRegisterEventBus()) {
            EventBusUtil.unRegister(this);
        }
    }


//    @NonNull
//    public <T extends CommResponse, R extends CommResponse> ObservableTransformer bindUntilEvent(
//            @NonNull final ActivityLifeCycleEvent event) {
//        return new ObservableTransformer<T, R>() {
//            @Override
//            public ObservableSource<R> apply(Observable<T> upstream) {
//                Observable<ActivityLifeCycleEvent> o = lifecycleSubject.takeFirst(
//                        activityLifeCycleEvent -> activityLifeCycleEvent.equals(event));
//                /*TakeUntil订阅原始的Observable并发射数据，此外它还监视你提供的第二个Observable。
//                 当第二个Observable发射了一项数据或者发射一项终止的通知时（onError通知或者onCompleted通知），
//                 TakeUntil返回的Observable会停止发射原始的Observable*/
//                return (ObservableSource<R>) upstream.takeUntil(o);
//            }
//
//
//        };
//    }
}
