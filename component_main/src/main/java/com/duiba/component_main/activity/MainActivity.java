package com.duiba.component_main.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.duiba.component_base.component.BaseActivity;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.duiba.component_base.component.user.path.UserRouterPath;
import com.duiba.component_base.component.user.rpc.IUserFunService;
import com.duiba.component_base.component.user.rpc.IUserResService;
import com.duiba.component_base.interfaces.OnWawaSeekBarChangeListener;
import com.duiba.component_base.widget.WawaSeekBar;
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
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

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

    @BindView(R2.id.btn_h5)
    Button mBtnH5;
    @BindView(R2.id.btn_countdown)
    Button mBtnCountDown;
    @BindView(R2.id.btn_add)
    Button mBtnAdd;
    @BindView(R2.id.btn_reset)
    Button mBtnReset;
    @BindView(R2.id.btn_anim)
    Button mBtnAnim;
    @BindView(R2.id.btn_guide)
    Button mBtnGuide;

    @BindView(R2.id.wawa_countdown)
    WawaSeekBar mWawaCountdown;
    @BindView(R2.id.wawa_score)
    WawaSeekBar mWawaScore;

    /**
     * 倒计时Disposable
     */
    Disposable mDownDisposable;

    /**
     * 闪烁倒计时
     */
    //Disposable mBlingDisposable;
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
        RxView.clicks(mBtnH5).subscribe(aVoid -> {
            ARouter.getInstance()
                    .build(MainRouterPath.MAIN_ACTIVITY_WEB)
                    .withString("url", "file:////android_asset/schame-test.html")
                    .navigation();
        });
        RxView.clicks(mBtnCountDown).subscribe(aVoid -> {
            mDownDisposable = Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                    .subscribe(l -> {
                        mWawaCountdown.setProgress(mWawaCountdown.getProgress() - 1);
                    });
        });

        RxView.clicks(mBtnAdd).subscribe(aVoid -> {
            mWawaScore.setProgressWithAnim(mWawaScore.getProgress() + 10);
        });

        RxView.clicks(mBtnReset).subscribe(aVoid -> {
            mWawaScore.reset(20);
            //积分初始化
            float[] progress2 = new float[]{0.3f, 0.5f, 0.7f};
            String[] progressTicket2 = new String[]{"券x10", "券x10", "券x10"};
            mWawaScore.updateScoreData(progress2, progressTicket2);
        });

        RxView.clicks(mBtnGuide).subscribe(aVoid -> {
            ARouter.getInstance().build(MainRouterPath.MAIN_ACTIVITY_GUIDE).navigation();
        });

        RxView.clicks(mBtnAnim).subscribe(aVoid -> {
            ARouter.getInstance().build(MainRouterPath.MAIN_ACTIVITY_ANIM).navigation();
        });


        //倒计时初始化
        float[] progress = new float[]{0.1f, 0.5f, 0.9f};
        String[] progressTip = new String[]{"good", "great", "perfect"};
        mWawaCountdown.setCountdownData(progress, progressTip,0.3f);
        mWawaCountdown.setSeekBarChangeListener((seekBar, progress1, fromUser) -> {
            Logger.v("mWawaCountdown==>" + progress1);
        });

        //积分初始化
        float[] progress2 = new float[]{0.1f, 0.5f, 0.9f};
        String[] progressTicket2 = new String[]{"券x10", "券x10", "券x10"};
        mWawaScore.setScoreData(progress2, progressTicket2);
        mWawaScore.setSeekBarChangeListener((seekBar, progress12, fromUser) -> {
            Logger.v("mWawaScore==>" + progress12);
        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Logger.v("hasFocus===>" + hasFocus);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDownDisposable != null && !mDownDisposable.isDisposed()) {
            mDownDisposable.dispose();
            mDownDisposable = null;
        }

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
