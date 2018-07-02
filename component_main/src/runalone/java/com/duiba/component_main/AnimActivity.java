package com.duiba.component_main;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.BaseActivity;
import com.duiba.component_base.component.DuibaMvpPresenter;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.duiba.component_base.interfaces.OnWawaSeekBarChangeListener;
import com.duiba.component_base.widget.WawaSeekBar;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
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
 * @desc: 动画页面
 */
@Route(path = MainRouterPath.MAIN_ACTIVITY_ANIM)
public class AnimActivity extends BaseActivity {


    @BindView(R2.id.wawa_score)
    WawaSeekBar mWawaScore;
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.btn_progress)
    Button mBtnAdd;
    @BindView(R2.id.iv_target)
    ImageView mIvTarget;

    @BindView(R.id.iv_source0)
    ImageView mIvSource0;
    @BindView(R.id.iv_source1)
    ImageView mIvSource1;
    @BindView(R.id.iv_source2)
    ImageView mIvSource2;
    @BindView(R.id.iv_source3)
    ImageView mIvSource3;
    @BindView(R.id.iv_source4)
    ImageView mIvSource4;
    @BindView(R.id.iv_source5)
    ImageView mIvSource5;
    List<ImageView> list = new ArrayList<>();


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_anim);
        ButterKnife.bind(this);
        list.add(mIvSource0);
        list.add(mIvSource1);
        list.add(mIvSource2);
        list.add(mIvSource3);
        list.add(mIvSource4);
        list.add(mIvSource5);

        //积分初始化
        float[] progress2 = new float[]{0.1f, 0.5f, 0.9f};
        String[] progressTicket2 = new String[]{"券x10", "券x10", "券x10"};
        mWawaScore.setScoreData(progress2, progressTicket2);
        mWawaScore.setSeekBarChangeListener(new OnWawaSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onProgressArrive2Point(View view, int progress) {

            }
        });

        RxView.clicks(mBtn).subscribe(aVoid -> {
            mEndPoint = mWawaScore.getSeekBarThumbPos();
            startAnimation();
        });

        RxView.clicks(mBtnAdd).subscribe(aVoid -> {
            mWawaScore.setProgressWithAnim(mWawaScore.getProgress() + 10);
        });


    }

    @Override
    protected ViewModel createViewModel() {
        return null;
    }

    @Override
    protected void performViewModelSubscribe(ViewModel viewModel) {

    }

    Point mEndPoint = null;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mEndPoint = mWawaScore.getSeekBarThumbPos();
        Logger.v("point===>" + mEndPoint.toString());
    }


    private int[] getTargetPos() {
        int[] rect = new int[2];
        if (mIvTarget != null) {
            mIvTarget.getLocationOnScreen(rect);
        }
        return rect;
    }

    private int[] getSourcePos(View view) {
        int[] rect = new int[2];
        if (view != null) {
            view.getLocationOnScreen(rect);
        }
        return rect;
    }

    Disposable mDisposable;
    int mCount;

    private void startAnimation() {

        mDisposable = Observable.interval(0, 200, TimeUnit.MILLISECONDS)
                .subscribe(l -> {
                    if (mCount == 6) {
                        if (mDisposable != null) {
                            mDisposable.dispose();
                            mDisposable = null;
                            mCount = 0;

                        }
                        return;
                    }
                    //解决一边移动一遍放大时位置估算不准的bug
                    int endPosX = (int) ((getTargetPos()[0] - getSourcePos(list.get(mCount))[0]) * (1 / 1.5));
                    int endPosY = (int) ((getTargetPos()[1] - getSourcePos(list.get(mCount))[1]) * (1 / 1.5));
//                    int endPosX = (int) (getTargetPos()[0] - getSourcePos(list.get(mCount))[0]);
//                    int endPosY = (int) (getTargetPos()[1] - getSourcePos(list.get(mCount))[1]);
                    AnimationSet set = new AnimationSet(false);
                    Animation translateAnimation = new TranslateAnimation(0, endPosX, 0, endPosY);
                    set.addAnimation(translateAnimation);

                    Animation scaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    set.addAnimation(scaleAnimation);
                    set.setDuration(500);
                    list.get(mCount).startAnimation(set);
                    mCount++;

                });


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


    @Override
    public DuibaMvpPresenter onCreatePresenter() {
        return null;
    }
}
