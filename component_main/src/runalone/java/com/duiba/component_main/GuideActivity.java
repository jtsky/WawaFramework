package com.duiba.component_main;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
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
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: jintai
 * @time: 2017/11/9-18:36
 * @Email: jintai@duiba.com.cn
 * @desc: 新手引导页
 */
@Route(path = MainRouterPath.MAIN_ACTIVITY_GUIDE)
public class GuideActivity extends BaseActivity {
    @BindView(R2.id.video)
    VideoView mVideoView;
    @BindView(R2.id.iv_handle)
    ImageView mIvHandle;
    @BindView(R2.id.rl_wrap)
    RelativeLayout mRlWrap;

    List<Uri> mVideoPaths = new ArrayList<>();
    Map<Integer, Integer> mImageResMaps = new HashMap<>();
    private String VIDEO_PATH_ROOT;
    private Uri mCurrentUri;
    private int mCurrentImageResId;
    private boolean mLastIsImage = false;

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
            videoPlay();
        });
    }

    /**
     * 初始化资源图片id和视频地址
     */
    private void initRes() {
        //添加视频资源路径
        mVideoPaths.add(Uri.parse(VIDEO_PATH_ROOT + R.raw.guide0));
//        mVideoPaths.add(Uri.parse(VIDEO_PATH_ROOT + R.raw.guide1));
//        mVideoPaths.add(Uri.parse(VIDEO_PATH_ROOT + R.raw.guide2));
//        mVideoPaths.add(Uri.parse(VIDEO_PATH_ROOT + R.raw.guide3));
        //添加图片资源路径
        // mImageResMaps.put(0, R.mipmap.screen0);
        mImageResMaps.put(1, R.mipmap.screen1);
        //mImageResMaps.put(2, R.mipmap.screen2);
        //mImageResMaps.put(3, R.mipmap.screen3);
        //mImageResMaps.put(4, R.mipmap.screen4);

        //如果插入的图片资源大于视频资源时报错
        if (mImageResMaps.size() - mVideoPaths.size() > 1) {
            throw new RuntimeException("插入图片资源的数量大于视频的个数");
        }

    }

    /**
     * 返回出入的图片resId;
     *
     * @return
     */
    private int needInsertImage() {
        if (mLastIsImage) {
            return 0;
        }
        int index;
        if (mCurrentUri == null) {
            index = 0;
        } else {
            index = mVideoPaths.indexOf(mCurrentUri) + 1;
        }
        int resId;
        try {
            resId = mImageResMaps.get(index);
        } catch (Exception e) {
            resId = 0;
        }
        return resId;
    }

    private void setupVideo() {
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaybackVideo();
                videoPlay();
            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                stopPlaybackVideo();
                return true;
            }
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

            videoPlay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void needJump() {
        Toast.makeText(this, "jump", Toast.LENGTH_SHORT).show();
    }

    private void videoPlay() {
        int resId = needInsertImage();
        //显示视频
        if (resId == 0) {

            int index;
            if (mCurrentUri == null) {
                index = 0;
            } else {
                index = mVideoPaths.indexOf(mCurrentUri) + 1;
            }
            try {
                mCurrentUri = mVideoPaths.get(index);
                mVideoView.setVideoURI(mVideoPaths.get(index));
                mIvHandle.setVisibility(View.GONE);
                mRlWrap.setVisibility(View.VISIBLE);
                mLastIsImage = false;
            } catch (Exception e) {
                //结束进行跳转
                needJump();
            }
        }
        //显示图片
        else {
            if (mCurrentImageResId == resId) {
                //结束进行跳转
                needJump();
            } else {
                Glide.with(this)
                        .load(resId)
                        .into(mIvHandle);
                mCurrentImageResId = resId;
                mIvHandle.setVisibility(View.VISIBLE);
                mRlWrap.setVisibility(View.GONE);
                mLastIsImage = true;
            }
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
