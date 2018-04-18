package com.duiba.component_main;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.duiba.component_base.component.BaseActivity;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.duiba.component_main.bean.GuideBean;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: jintai
 * @time: 2017/11/9-18:36
 * @Email: jintai@duiba.com.cn
 * @desc: 新手引导页
 */
@Route(path = MainRouterPath.MAIN_ACTIVITY_GUIDE)
public class Guide2Activity extends BaseActivity {
    @BindView(R2.id.video)
    VideoView mVideoView;
    @BindView(R2.id.iv_handle)
    ImageView mIvHandle;
    @BindView(R2.id.rl_wrap)
    RelativeLayout mRlWrap;

    private String VIDEO_PATH_ROOT;

    List<GuideBean> mAllGuide = new ArrayList<>();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_guide);
        ButterKnife.bind(this);
        VIDEO_PATH_ROOT = "android.resource://" + getPackageName() + "/raw/";
        initRes();
        setupVideo();
        RxView.clicks(mIvHandle).subscribe(aVoid -> {
            play();
        });
    }

    /**
     * 初始化资源图片id和视频地址
     */
    private void initRes() {
        mAllGuide.add(new GuideBean(GuideBean.GuideType.IMAGE, R.mipmap.screen0));
        mAllGuide.add(new GuideBean(GuideBean.GuideType.VODEO, Uri.parse(VIDEO_PATH_ROOT + R.raw.guide0)));
        mAllGuide.add(new GuideBean(GuideBean.GuideType.IMAGE, R.mipmap.screen1));
        mAllGuide.add(new GuideBean(GuideBean.GuideType.VODEO, Uri.parse(VIDEO_PATH_ROOT + R.raw.guide1)));
        mAllGuide.add(new GuideBean(GuideBean.GuideType.IMAGE, R.mipmap.screen2));
        mAllGuide.add(new GuideBean(GuideBean.GuideType.VODEO, Uri.parse(VIDEO_PATH_ROOT + R.raw.guide2)));
        mAllGuide.add(new GuideBean(GuideBean.GuideType.IMAGE, R.mipmap.screen3));
        mAllGuide.add(new GuideBean(GuideBean.GuideType.VODEO, Uri.parse(VIDEO_PATH_ROOT + R.raw.guide3)));
        mAllGuide.add(new GuideBean(GuideBean.GuideType.IMAGE, R.mipmap.screen4));
    }


    private void setupVideo() {
        mVideoView.setOnPreparedListener(mp -> mVideoView.start());
        mVideoView.setOnCompletionListener(mp -> {
            stopPlaybackVideo();
            play();
        });
        mVideoView.setOnErrorListener((mp, what, extra) -> {
            stopPlaybackVideo();
            return true;
        });

        try {
            //解决VideoView不能全屏的问题
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mVideoView.setLayoutParams(layoutParams);

            play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void needJump() {
        Toast.makeText(this, "jump", Toast.LENGTH_SHORT).show();
    }

    private int mCurrentPos = 0;

    private void play() {
        try {
            GuideBean guideBean = mAllGuide.get(mCurrentPos);
            mCurrentPos = mCurrentPos + 1;
            switch (guideBean.getType()) {
                case VODEO:
                    mVideoView.setVideoURI((Uri) guideBean.getPath());
                    mIvHandle.setVisibility(View.GONE);
                    mRlWrap.setVisibility(View.VISIBLE);
                    break;
                case IMAGE:
                    Glide.with(this)
                            .load(guideBean.getPath())
                            .into(mIvHandle);
                    mIvHandle.setVisibility(View.VISIBLE);
                    mRlWrap.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            needJump();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mVideoView.isPlaying()) {
            mVideoView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView.canPause()) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlaybackVideo();
    }

    private void stopPlaybackVideo() {
        try {
            mVideoView.stopPlayback();
        } catch (Exception e) {
            e.printStackTrace();
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
