package com.duiba.component_user.home.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.duiba.component_base.component.BaseFragment;
import com.duiba.component_base.component.DuibaMvpPresenter;
import com.duiba.component_user.R;
import com.duiba.component_user.R2;
import com.duiba.component_user.home.listener.HomeView;
import com.duiba.component_user.home.model.UserViewModel;
import com.duiba.component_user.home.presenter.HomePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;

/**
 * @author: jintai
 * @time: 2018/3/26-14:13
 * @Email: jintai@qccr.com
 * @desc:
 */
public class UserHomeFragment extends BaseFragment {
    @BindView(R2.id.btn)
    Button mBtn;
    @BindView(R2.id.tv)
    TextView mTv;

    UserViewModel userViewModel;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.user_fragment_home;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        RxView.clicks(mBtn).subscribe(aVoid -> {
            userViewModel.getName().setValue("来自Fragment的更新");
        });
    }

    @Override
    public void subscribeViewModel() {
        mViewModel = userViewModel = (UserViewModel) getParentViewModel();
        Observer<String> nameObserver = s -> mTv.setText(s);
        userViewModel.getName().observe(this, nameObserver);
    }

    @Override
    protected boolean isMVP() {
        return true;
    }

    @Override
    public DuibaMvpPresenter onCreatePresenter() {
        return new HomePresenter();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
