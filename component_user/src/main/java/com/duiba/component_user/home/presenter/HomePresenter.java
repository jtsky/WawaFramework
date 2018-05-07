package com.duiba.component_user.home.presenter;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModel;

import com.duiba.component_base.component.DuibaMvpPresenter;
import com.duiba.component_base.component.DuibaMvpView;
import com.duiba.component_user.bean.GankBean;
import com.duiba.component_user.home.listener.HomeView;
import com.duiba.component_user.home.model.UserViewModel;
import com.duiba.component_user.net.UserRESTApiImpl;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;


/**
 * @author: jintai
 * @time: 2018/3/23-11:33
 * @Email: jintai@duiba.com.cn
 * @desc:
 */
public class HomePresenter extends DuibaMvpPresenter<UserViewModel, HomeView> {

    @SuppressLint("CheckResult")
    public void login() {
        //获取view一定要这样调用
        ifViewAttached(v -> {
            v.print();
        });

        //获取viewModel一定要这样调用
        ifViewModelAttached(viewModel -> {
            viewModel.getName().setValue("来自Activity的更新");
        });

        Observable.timer(3, TimeUnit.SECONDS).subscribe(l->{
            UserRESTApiImpl.getData("Android",null)
                    .compose(bindUntilEvent(Lifecycle.State.DESTROYED))
                    .subscribe(response->{
                        GankBean bean  = response.getData().get(0);
                        getView().setResponse(bean.toString());
                    });

        });

    }

}

