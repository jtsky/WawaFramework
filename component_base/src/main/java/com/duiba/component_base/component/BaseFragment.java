package com.duiba.component_base.component;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duiba.component_base.BuildConfig;
import com.duiba.component_base.util.EventBusUtil;
import com.duiba.library_common.bean.Event;
import com.duiba.library_common.bean.EventCode;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.orhanobut.logger.Logger;

import java.util.Arrays;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.PublishSubject;
import com.duiba.wsmanager.WsManager;
import com.duiba.wsmanager.WsManagerFactory;
import com.duiba.wsmanager.listener.AbstractWsStatusListener;

import static com.duiba.component_base.component.BaseActivity.WEBSOCKET_URL;

/**
 * @author jintai
 * @date 2018/03/23
 * @desc 基础的BaseFragment
 */
public abstract class BaseFragment<Model extends ViewModel, V extends DuibaMvpView, P extends DuibaMvpPresenter<Model, V>>
        extends MvpFragment<V, P> {
    protected final String TAG = getClass().getSimpleName();
    protected final String TAG_CURRENR = "CurrentFragment";
    /**
     * 根布局
     */
    private View mContentView;
    /**
     * 父Activity
     */
    private BaseActivity mParentActivity;
    /**
     * 基础的viewModel
     */
    protected Model mViewModel;

    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParentActivity = (BaseActivity) getActivity();
        //是否注册EventBus事件
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        //输出当前fragment
        if (BuildConfig.DEBUG) {
            Logger.t(TAG_CURRENR).v("===CurrentFragment====>" + this.getClass().getCanonicalName());
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
            initWebSocket();
        }
    }

    @Deprecated
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mContentView = inflater.inflate(setLayoutResourceID(), container, false);

        mUnbinder = ButterKnife.bind(this, mContentView);
        init();
        return mContentView;
    }

    /**
     * 获取activity的ViewModel 拿到是否需要强转
     */
    protected ViewModel getParentViewModel() {
        ViewModel viewModel = mParentActivity.getViewModel();
        if (viewModel == null) {
            return null;
        }

        return viewModel;
    }


    /**
     * 初始化WebSocket
     */
    protected AbstractWsStatusListener mWsAbstractStatusListener;

    private void initWebSocket() {
        if (mWsAbstractStatusListener == null) {
            throw new NullPointerException("请在子Fragemnt中重写createWSStatusListener方法并为mWsAbstractStatusListener赋值");
        }
        WsManager wsManager = WsManagerFactory.createWsManager(getActivity().getApplicationContext(), WEBSOCKET_URL);
        wsManager.setWsStatusListener(mWsAbstractStatusListener);
        wsManager.startConnect();
    }

    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID
     *
     * @return 布局文件资源ID
     */
    protected abstract int setLayoutResourceID();

    /**
     * 此方法用于初始化成员变量及获取Intent传递过来的数据
     * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
     */
    protected void init() {
    }

    /**
     * 进行view的初始化等操作
     */
    protected void initView(View view, @Nullable Bundle savedInstanceState) {

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
    public void subscribeViewModel() {

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (isRegisterEventBus()) {
            EventBusUtil.unRegister(this);
        }
    }



}
