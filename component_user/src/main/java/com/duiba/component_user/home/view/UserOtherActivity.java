package com.duiba.component_user.home.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.application.BaseApplication;
import com.duiba.component_base.component.BaseFragmentationActivity;
import com.duiba.component_base.component.user.path.UserRouterPath;
import com.duiba.component_base.util.ViewModelUtil;
import com.duiba.component_base.vm.GlobalViewModel;
import com.duiba.component_user.R;
import com.duiba.component_user.R2;
import com.duiba.component_user.bean.GankBean;
import com.duiba.component_user.home.listener.HomeView;
import com.duiba.component_user.home.model.UserViewModel;
import com.duiba.component_user.home.presenter.HomePresenter;
import com.duiba.wsmanager.listener.AbstractWsStatusListener;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;
import okio.ByteString;

/**
 * @author: jintai
 * @time: 2018/3/23-10:24
 * @Email: jintai@duiba.com.cn
 * @desc:用户测试主页
 */
@Route(path = UserRouterPath.USER_ACTIVITY_OTHER)
public class UserOtherActivity extends BaseFragmentationActivity<UserViewModel, HomeView, HomePresenter> implements HomeView {

    @BindView(R2.id.tv)
    TextView mTv;
    @BindView(R2.id.btn)
    Button mBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_other);
        ButterKnife.bind(this);


        RxView.clicks(mBtn).subscribe(aVoid -> {
            // getViewModel().getName().setValue("我来自UserOtherActivity");
            GlobalViewModel globalViewModel = (GlobalViewModel) BaseApplication.getApplication().getGlobalViewModel(GlobalViewModel.class.getSimpleName());
            globalViewModel.getValue().setValue("我来自UserOtherActivity");
        });
    }


    @Override
    protected UserViewModel createViewModel() {
        return ViewModelProviders.of(this).get(UserViewModel.class);
    }


    @Override
    protected void performViewModelSubscribe(UserViewModel viewModel) {

        Observer<String> nameObserver = s -> mTv.setText(s);
        GlobalViewModel globalViewModel = (GlobalViewModel) BaseApplication.getApplication().getGlobalViewModel(GlobalViewModel.class.getSimpleName());
        globalViewModel.getValue().observe(this, nameObserver);
        //viewModel.getName().observe(this, nameObserver);
    }

    @Override
    protected boolean isMVP() {
        return true;
    }


    @NonNull
    @Override
    public HomePresenter onCreatePresenter() {
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
        return false;
    }


}
