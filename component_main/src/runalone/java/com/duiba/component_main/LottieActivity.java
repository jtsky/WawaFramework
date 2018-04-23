package com.duiba.component_main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.BaseActivity;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
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
@Route(path = MainRouterPath.MAIN_ACTIVITY_LOTTIE)
public class LottieActivity extends BaseActivity {


    @BindView(R2.id.lottie_boom)
    LottieAnimationView mLottieBoom;
    @BindView(R2.id.btn_play)
    Button mBtnPlay;
    @BindView(R2.id.tv_hit)
    TextView mTvHit;
    @BindView(R2.id.fl_wrap)
    FrameLayout mWrap;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_lottie);
        ButterKnife.bind(this);

        mLottieBoom.setImageAssetsFolder("boom/images");
        mLottieBoom.setAnimation("boom/boom.json");
        mLottieBoom.loop(false);

        RxView.clicks(mBtnPlay).subscribe(aVoid -> {
            mLottieBoom.playAnimation();
        });
        //增加整体布局监听
        ViewTreeObserver vto = mLottieBoom.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int width = mLottieBoom.getWidth();
                int height = mLottieBoom.getHeight();
                Logger.v("width==>" + width + "   height==>" + height);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mTvHit.getLayoutParams();
                params.setMargins((int) (width * 0.55 - mTvHit.getWidth() / 2.0), (int) (height * 0.39 - mTvHit.getHeight() / 2.0), 0, 0);
                mTvHit.setLayoutParams(params);
                if (width != 0) {
                    mLottieBoom.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

            }
        });

    }

    /**
     * 普通io
     */
    private void readTextWithIo(File file) {
        BufferedReader br = null;
        String sCurrentLine;
        try {
            br = new BufferedReader(
                    new FileReader(file));
            while ((sCurrentLine = br.readLine()) != null) {
                Logger.v(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * nio
     */
    private void readTextWithNio(File file) {

    }

    /**
     * okio
     */
    private void readTextWithOkio(File file) {

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

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return new MvpBasePresenter();
    }
}
