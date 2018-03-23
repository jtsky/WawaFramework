package com.duiba.component_share.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.duiba.component_base.component.BaseActivity;
import com.duiba.component_base.component.share.path.ShareRouterPath;
import com.duiba.component_base.component.user.path.UserRouterPath;
import com.duiba.component_base.component.user.rpc.IUserResService;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.jakewharton.rxbinding.view.RxView;
import com.duiba.component_share.R;
import com.duiba.component_share.R2;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: jintai
 * @time: 2017/11/9-18:36
 * @Email: jintai@duiba.com.cn
 * @desc:
 */
@Route(path = ShareRouterPath.SHARE_ACTIVITY_SHARE)
public class ShareActivity extends BaseActivity {
    @BindView(R2.id.iv)
    ImageView mIv;
    @Autowired(name = "aaa")
    int aaa;

    @Autowired(name = UserRouterPath.USER_SERVER_RES)
    IUserResService mUserResService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_activity_share);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        RxView.clicks(mIv).subscribe(aVoid -> {
            Logger.v("aaa======>" + aaa);
            Logger.v(mUserResService.provideText());

            //IMainOtherService mainOtherService = (IMainOtherService) ServiceManager.getService(IMainOtherService.class.getSimpleName());
            //ARouter.getInstance().build(mainOtherService.routerByMainPath()).navigation();
        });
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return null;
    }
}
