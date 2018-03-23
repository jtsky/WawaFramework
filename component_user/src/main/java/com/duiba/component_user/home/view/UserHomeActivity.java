package com.duiba.component_user.home.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.duiba.component_base.component.BaseActivity;
import com.duiba.component_user.R;
import com.duiba.component_user.R2;
import com.duiba.component_user.home.listener.HomeView;
import com.duiba.component_user.home.model.UserViewModel;
import com.duiba.component_user.home.presenter.HomePresenter;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: jintai
 * @time: 2018/3/23-10:24
 * @Email: jintai@duiba.com.cn
 * @desc:用户测试主页
 */
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
        RxView.clicks(mBtn).subscribe(aVoid -> getPresenter().login());
    }

    @Override
    public void subscribeViewModel() {
        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        Observer<String> nameObserver = s -> mTv.setText(s);
        mViewModel.getName().observe(this, nameObserver);
    }

    @Override
    public ViewModel getViewModel() {
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


}
