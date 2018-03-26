package com.duiba.component_user.home.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.BaseActivity;
import com.duiba.component_base.component.user.path.UserRouterPath;
import com.duiba.component_user.R;
import com.duiba.component_user.R2;
import com.duiba.component_user.home.listener.HomeView;
import com.duiba.component_user.home.model.UserViewModel;
import com.duiba.component_user.home.presenter.HomePresenter;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;
import okio.ByteString;
import wsmanager.listener.AbstractWsStatusListener;

import static com.duiba.component_base.component.user.path.UserRouterPath.USER_ACTIVITY_HOME;

/**
 * @author: jintai
 * @time: 2018/3/23-10:24
 * @Email: jintai@duiba.com.cn
 * @desc:用户测试主页
 */
@Route(path = UserRouterPath.USER_ACTIVITY_HOME)
public class UserHomeActivity extends BaseActivity<UserViewModel, HomeView, HomePresenter> implements HomeView {

    @BindView(R2.id.tv)
    TextView mTv;
    @BindView(R2.id.btn)
    Button mBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_home);
        ButterKnife.bind(this);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl, new UserHomeFragment());
        transaction.commit();
        RxView.clicks(mBtn).subscribe(aVoid -> getPresenter().login());
    }

    @Override
    public void subscribeViewModel() {
        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        Observer<String> nameObserver = s -> mTv.setText(s);
        mViewModel.getName().observe(this, nameObserver);
    }

    @Override
    protected boolean isMVP() {
        return true;
    }

    @Override
    public UserViewModel getViewModel() {
        return mViewModel;
    }

    @NonNull
    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }


    @Override
    public void print() {
        Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
    }


    /**
     * ################################WebSocket 重写方法#####################################
     */
    @Override
    public boolean isOpenWebSocket() {
        return true;
    }

    @Override
    public void createWSStatusListener() {
        super.createWSStatusListener();
        mWsAbstractStatusListener = new AbstractWsStatusListener() {
            @Override
            public void onOpen(Response response) {
                super.onOpen(response);
                Log.d(TAG, "WsManager-----onOpen");
            }

            @Override
            public void onMessage(String text) {
                super.onMessage(text);
                Log.d(TAG, "WsManager-----onMessage");
            }

            @Override
            public void onMessage(ByteString bytes) {
                super.onMessage(bytes);
                Log.d(TAG, "WsManager-----onMessage");
            }

            @Override
            public void onReconnect() {
                super.onReconnect();
                Log.d(TAG, "WsManager-----onReconnect");
            }

            @Override
            public void onClosing(int code, String reason) {
                super.onClosing(code, reason);
                Log.d(TAG, "WsManager-----onClosing");
            }

            @Override
            public void onClosed(int code, String reason) {
                super.onClosed(code, reason);
                Log.d(TAG, "WsManager-----onClosed");
            }

            @Override
            public void onFailure(Throwable t, Response response) {
                super.onFailure(t, response);
                Log.d(TAG, "WsManager-----onFailure");
            }

            @Override
            public void onNetClosed() {
                super.onNetClosed();
                Log.d(TAG, "WsManager-----onNetClosed");
            }

            @Override
            public void onNetOpen() {
                super.onNetOpen();
                Log.d(TAG, "WsManager-----onNetOpen");
            }

        };
    }


}
