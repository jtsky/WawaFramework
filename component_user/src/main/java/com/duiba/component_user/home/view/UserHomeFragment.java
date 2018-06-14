package com.duiba.component_user.home.view;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.duiba.component_base.component.BaseFragment;
import com.duiba.component_base.component.BaseFragmentationFragment;
import com.duiba.component_base.component.DefaultMvpPresenter;
import com.duiba.component_base.component.DefaultMvpView;
import com.duiba.component_base.component.DefaultViewModel;
import com.duiba.component_user.R;
import com.duiba.component_user.R2;
import com.duiba.component_user.home.model.UserViewModel;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;

/**
 * @author: jintai
 * @time: 2018/3/26-14:13
 * @Email: jintai@qccr.com
 * @desc:
 */
public class UserHomeFragment extends BaseFragmentationFragment<DefaultViewModel, DefaultMvpView, DefaultMvpPresenter> {
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
    protected DefaultViewModel createViewModel() {
        userViewModel = (UserViewModel) getParentViewModel();
        return ViewModelProviders.of(this).get(DefaultViewModel.class);
    }

    @Override
    protected void performSubscribe(DefaultViewModel viewModel) {

    }


    @Override
    protected boolean isMVP() {
        return true;
    }

    @Override
    public DefaultMvpPresenter onCreatePresenter() {
        return new DefaultMvpPresenter();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
