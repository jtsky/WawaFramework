package com.duiba.component_base.component;

import android.arch.lifecycle.ViewModel;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.ColorRes;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
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

import com.duiba.wsmanager.WsManager;
import com.duiba.wsmanager.WsManagerFactory;
import com.duiba.wsmanager.listener.AbstractWsStatusListener;

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

    public Model getViewModel() {
        return mViewModel;
    }

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
        if (isMVP()) {
            //初始化并订阅ViewModel
            subscribeViewModel();
            if (mViewModel == null) {
                throw new NullPointerException("请重写subscribeViewModel方法为mViewModel赋值并建立绑定");
            }
        }


        //初始化webSocket
        if (isOpenWebSocket()) {
            Logger.v("===========OpenWebSocket==============");
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
     * 抽象方法 初始化并订阅ViewModel
     */
    protected void subscribeViewModel() {

    }

    /**
     * 抽象方法 是否采用mvp模式
     *
     * @return true or false
     */
    protected abstract boolean isMVP();


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

    /**
     * 显示顶部snack bar
     *
     * @param message
     * @param textColor
     * @param bgColor
     */
    protected void showSnackBar(String message, @ColorRes int textColor, @ColorRes int bgColor) {
        showSnackBar(message, textColor, bgColor, 0, 24, 0, 24);
    }

    /**
     * 显示顶部snack bar 详细
     *
     * @param message
     * @param textColor
     * @param bgColor
     * @param leftIcon
     * @param leftSizeDp
     * @param rightIcon
     * @param rightSizeDp
     */
    protected void showSnackBar(String message, @ColorRes int textColor, @ColorRes int bgColor, @ColorRes int leftIcon, int leftSizeDp, @ColorRes int rightIcon, int rightSizeDp) {
        if (mRootView == null) {
            return;
        }
        TSnackbar tSnackbar = TSnackbar.make(mRootView, message, TSnackbar.LENGTH_SHORT);
        //Size in dp - 24 is great!
        if (leftIcon != 0) {
            tSnackbar.setIconLeft(leftIcon, leftSizeDp == 0 ? 24 : leftSizeDp);
        }

        //Resize to bigger dp
        if (rightIcon != 0) {
            tSnackbar.setIconRight(rightIcon, rightSizeDp == 0 ? 24 : rightSizeDp);
        }
        tSnackbar.setIconPadding(8);
        tSnackbar.setMaxWidth(3000);
        View snackbarView = tSnackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(bgColor));
        TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(textColor));
        tSnackbar.show();
    }

    /**
     * 页面的根布局
     **/
    View mRootView;

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityLifeCycleEvent.RESUME);
        MobclickAgent.onResume(this);
        mRootView = findViewById(android.R.id.content);
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
