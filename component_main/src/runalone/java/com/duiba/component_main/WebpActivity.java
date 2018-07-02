package com.duiba.component_main;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.BaseActivity;
import com.duiba.component_base.component.DefaultMvpPresenter;
import com.duiba.component_base.component.DuibaMvpPresenter;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: jintai
 * @time: 2017/11/9-18:36
 * @Email: jintai@duiba.com.cn
 * @desc: 新手引导页
 */
@Route(path = MainRouterPath.MAIN_ACTIVITY_WEBP)
public class WebpActivity extends BaseActivity {


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_webp);
        ButterKnife.bind(this);


    }

    @Override
    public boolean isLockedPortrait() {
        return true;
    }

    @Override
    protected ViewModel createViewModel() {
        return null;
    }

    @Override
    protected void performViewModelSubscribe(ViewModel viewModel) {

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected boolean isMVP() {
        return false;
    }

    @Override
    public DuibaMvpPresenter onCreatePresenter() {
        return new DefaultMvpPresenter();
    }
}
