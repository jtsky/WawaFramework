package com.duiba.component_main.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.duiba.component_base.component.BaseActivity;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.duiba.component_base.component.user.path.UserRouterPath;
import com.duiba.component_base.component.user.rpc.IUserFunService;
import com.duiba.component_base.component.user.rpc.IUserResService;
import com.duiba.component_main.R;
import com.duiba.component_main.R2;
import com.duiba.component_main.bean.TestBean;
import com.duiba.component_main.net.MainRESTApiImpl;
import com.duiba.library_network.action.WebSuccessAction;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: jintai
 * @time: 2017/11/9-18:36
 * @Email: jintai@duiba.com.cn
 * @desc: 主模块首页
 */
@Route(path = MainRouterPath.MAIN_ACTIVITY_MAIN)
public class MainActivity extends BaseActivity {

    @Autowired(name = "aaa")
    int aaa;

    /**
     * 服务是以单例形式存在的
     */
    @Autowired(name = UserRouterPath.USER_SERVER_RES)
    IUserResService mUserResService;

    @Autowired(name = UserRouterPath.USER_SERVER_FUN)
    IUserFunService mUserFunService;

    @BindView(R2.id.btn_net)
    Button mBtnNet;
    @BindView(R2.id.btn_arouter)
    Button mBtnArouter;
    @BindView(R2.id.btn_mvp)
    Button mBtnMvp;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        RxView.clicks(mBtnNet).subscribe(aVoid -> {
            //网络调用
            MainRESTApiImpl
                    .getData("Android", null)
                    .subscribe(new WebSuccessAction<List<TestBean>>() {
                        @Override
                        public void onSuccess(List<TestBean> testBeans) {
                            Logger.v("size===>" + testBeans.size());
                        }

                        @Override
                        public void onFailed(int code, String msg) {

                        }
                    });
        });

        RxView.clicks(mBtnArouter).subscribe(aVoid -> {
            Logger.v("aaa======>" + aaa);
            if (mUserResService != null) {
                Logger.v(mUserResService.provideText());
                Logger.v("mUserResService====>" + mUserResService.toString());
            }

            if (mUserFunService != null) {
                mUserFunService.provideObj().call();
            }

            //服务是以单例形式存在的
            IUserResService userResService = (IUserResService) ARouter.getInstance().build(UserRouterPath.USER_SERVER_RES).navigation();
            if (userResService != null) {
                Logger.v("userResService======>" + userResService.toString());
            }

        });

        RxView.clicks(mBtnMvp).subscribe(aVoid -> {
            ARouter.getInstance().build(UserRouterPath.USER_ACTIVITY_HOME).navigation();
        });
    }


    @Override
    protected boolean isMVP() {
        return false;
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return new MvpBasePresenter();
    }
}
