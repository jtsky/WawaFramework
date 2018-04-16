package com.duiba.component_main;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.BaseActivity;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.duiba.component_base.widget.WawaSeekBar;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: jintai
 * @time: 2017/11/9-18:36
 * @Email: jintai@duiba.com.cn
 * @desc: 动画页面
 */
@Route(path = MainRouterPath.MAIN_ACTIVITY_ANIM)
public class AnimActivity extends BaseActivity {


    @BindView(R2.id.wawa_score)
    WawaSeekBar mWawaScore;
    @BindView(R2.id.iv_snail)
    ImageView mIvSnail;
    @BindView(R2.id.ll_wrap)
    LinearLayout mllWrap;
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.btn_progress)
    Button mBtnAdd;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_anim);
        ButterKnife.bind(this);

        //积分初始化
        float[] progress2 = new float[]{0.1f, 0.5f, 0.9f};
        String[] progressTicket2 = new String[]{"券x10", "券x10", "券x10"};
        mWawaScore.setScoreData(progress2, progressTicket2);
        mWawaScore.setSeekBarChangeListener((seekBar, progress12, fromUser) -> {
            Logger.v("mWawaScore==>" + progress12);
        });

        RxView.clicks(mBtn).subscribe(aVoid -> {
            mEndPoint = mWawaScore.getSeekBarThumbPos();
            startAnimation();
        });

        RxView.clicks(mBtnAdd).subscribe(aVoid -> {
            mWawaScore.setProgressWithAnim(mWawaScore.getProgress() + 10);
        });


    }

    Point mEndPoint = null;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mEndPoint = mWawaScore.getSeekBarThumbPos();
        Logger.v("point===>" + mEndPoint.toString());
    }


    private void startAnimation() {
        int[] rect = new int[2];
        mllWrap.getLocationOnScreen(rect);
        AnimationSet set = new AnimationSet(false);
        Animation translateAnimation = new TranslateAnimation(0, mEndPoint.x - rect[0], 0, mEndPoint.y - rect[1]);
        //Animation translateAnimation = new TranslateAnimation(0, mEndPoint.x - rect[0], 0, 0);
        set.addAnimation(translateAnimation);

//        Animation scaleAnimation = new ScaleAnimation(2, 1, 2, 1);
//        set.addAnimation(scaleAnimation);

        set.setDuration(2000);
        mllWrap.startAnimation(set);

        Animation rotateAnimation = new RotateAnimation(0f, 360f * 3, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        mIvSnail.startAnimation(rotateAnimation);
    }

    public void setAnim(final View v) {


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
    protected boolean isMVP() {
        return false;
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return new MvpBasePresenter();
    }
}
