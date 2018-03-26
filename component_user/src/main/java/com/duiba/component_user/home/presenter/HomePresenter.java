package com.duiba.component_user.home.presenter;

import android.arch.lifecycle.ViewModel;

import com.duiba.component_base.component.DuibaMvpPresenter;
import com.duiba.component_base.component.DuibaMvpView;
import com.duiba.component_user.home.listener.HomeView;
import com.duiba.component_user.home.model.UserViewModel;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * @author: jintai
 * @time: 2018/3/23-11:33
 * @Email: jintai@duiba.com.cn
 * @desc:
 */
public class HomePresenter extends DuibaMvpPresenter<UserViewModel, HomeView> {

    public void login() {
        //获取view一定要这样调用
        ifViewAttached(v -> {
            v.print();
        });

        //获取viewModel一定要这样调用
        ifViewModelAttached(viewModel -> {
            viewModel.getName().setValue("来自Activity的更新");
        });

    }
}

