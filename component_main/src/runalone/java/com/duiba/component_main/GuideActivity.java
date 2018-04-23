package com.duiba.component_main;

import android.annotation.SuppressLint;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.duiba.component_base.component.BaseActivity;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.duiba.component_main.bean.GuideBean;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.Arrays;
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
public class GuideActivity extends BaseActivity {
    @BindView(R2.id.video)
    VideoView mVideoView;
    @BindView(R2.id.iv_handle)
    ImageView mIvHandle;
    @BindView(R2.id.rl_wrap)
    RelativeLayout mRlWrap;

    private String VIDEO_PATH_ROOT;
    //设置可重复播放的位置
    private int[] mGifRepetIndexs;
    private int[] mAudioRepetIndexs;
    private static final int MESSAGE_SUCCESS = 200;
    List<GuideBean> mAllGuide = new ArrayList<>();
    List<Integer> mAllGuideAudio = new ArrayList<>();
    MediaPlayer mMediaPlayer;
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MESSAGE_SUCCESS && !hasJump) {
                play();
            }
        }
    };

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_guide);
        ButterKnife.bind(this);
        VIDEO_PATH_ROOT = "android.resource://" + getPackageName() + "/raw/";
        initRes();
        setupVideo();
        RxView.clicks(mIvHandle).subscribe(aVoid -> play());
    }

    /**
     * 初始化资源图片id和视频地址
     */
    private void initRes() {
        mGifRepetIndexs = new int[]{0, 2, 4};
        mAudioRepetIndexs = new int[]{0, 8};
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setVolume(1.0f, 1.0f);
        //初始化gif列表
        mAllGuide.add(new GuideBean(GuideBean.GuideType.IMAGE, R.mipmap.guide_g2));
        mAllGuide.add(new GuideBean(GuideBean.GuideType.IMAGE, R.mipmap.guide_g5));
        mAllGuide.add(new GuideBean(GuideBean.GuideType.IMAGE, R.mipmap.guide_g8));
        mAllGuide.add(new GuideBean(GuideBean.GuideType.IMAGE, R.mipmap.guide_g11));
        mAllGuide.add(new GuideBean(GuideBean.GuideType.IMAGE, R.mipmap.guide_g13));
        //初始化音频列表
        mAllGuideAudio.add(R.raw.guide_a0);
        mAllGuideAudio.add(R.raw.guide_a1);
        mAllGuideAudio.add(R.raw.guide_a2);
        mAllGuideAudio.add(R.raw.guide_a3);
        //表示不播放该段音频
        mAllGuideAudio.add(-4);
        mAllGuideAudio.add(R.raw.guide_a5);
        mAllGuideAudio.add(R.raw.guide_a6);
        //表示不播放该段音频
        mAllGuideAudio.add(-7);
        mAllGuideAudio.add(R.raw.guide_a8);
        mAllGuideAudio.add(R.raw.guide_a9_loop);
        //表示不播放该段音频
        mAllGuideAudio.add(-10);
        mAllGuideAudio.add(R.raw.guide_a11_loop);
        mAllGuideAudio.add(R.raw.guide_a12);
        mAllGuideAudio.add(R.raw.guide_a13);
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

    private boolean hasJump;

    private void needJump() {
        hasJump = true;
        Toast.makeText(this, "jump", Toast.LENGTH_SHORT).show();
    }

    private int mCurrentPos = 0;
    private static final String TAG = "GuideActivity";

    private void play() {
        try {
            GuideBean guideBean = mAllGuide.get(mCurrentPos);

            switch (guideBean.getType()) {
                case VODEO:
                    mVideoView.setVideoURI((Uri) guideBean.getPath());
                    mIvHandle.setVisibility(View.INVISIBLE);
                    mRlWrap.setVisibility(View.VISIBLE);
                    playAudio();
                    break;
                case IMAGE:
                    //是否循环播放gif
                    int gifCanRepet = Arrays.binarySearch(mGifRepetIndexs, mCurrentPos);
                    if (gifCanRepet >= 0) {
                        repetalways((Integer) guideBean.getPath());
                    } else {
                        repetOnce((Integer) guideBean.getPath());
                    }

                    mIvHandle.setVisibility(View.VISIBLE);
                    mRlWrap.setVisibility(View.INVISIBLE);
                    playAudio();

                    break;
                default:
                    break;
            }
            //mCurrentPos+1
            mCurrentPos = mCurrentPos + 1;
        } catch (Exception e) {
            needJump();
        }

    }

    /**
     * 播放音频
     */
    private void playAudio() throws Exception {
        //是否循环播放音频
        int audioCanRepet = Arrays.binarySearch(mAudioRepetIndexs, mCurrentPos);
        if (audioCanRepet >= 0) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
            }
            int audioResId = mAllGuideAudio.get(mCurrentPos);
            if (audioResId > 0) {
                AssetFileDescriptor file = getResources().openRawResourceFd(audioResId);
                mMediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                file.close();
                mMediaPlayer.start();
            }

        } else {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
            }
            int audioResId = mAllGuideAudio.get(mCurrentPos);
            if (audioResId > 0) {
                AssetFileDescriptor file = getResources().openRawResourceFd(audioResId);
                mMediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                mMediaPlayer.setLooping(false);
                mMediaPlayer.prepare();
                file.close();
                mMediaPlayer.start();
            }

        }

    }

    /**
     * 只重复一次
     *
     * @param resId
     */
    private void repetOnce(int resId) {
        Glide.with(this)
                .load(resId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<Integer, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception arg0, Integer arg1, Target<GlideDrawable> arg2, boolean arg3) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // 计算动画时长
                        int duration = 0;
                        GifDrawable drawable = (GifDrawable) resource;
                        GifDecoder decoder = drawable.getDecoder();
                        for (int i = 0; i < drawable.getFrameCount(); i++) {
                            duration += decoder.getDelay(i);
                        }
                        //发送延时消息，通知动画结束

                        mHandler.sendEmptyMessageDelayed(MESSAGE_SUCCESS, duration);
                        return false;
                    }
                })
                .dontAnimate()
                //仅仅加载一次gif动画
                .into(new GlideDrawableImageViewTarget(mIvHandle, 1));
        //设置成不能点击
        mIvHandle.setClickable(false);
    }

    /**
     * 一直重复
     *
     * @param resId
     */
    private void repetalways(int resId) {
        Glide.with(this)
                .load(resId)
                .asGif()
                .dontAnimate()
                .into(mIvHandle);
        //设置为可点击
        mIvHandle.setClickable(true);
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
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();

            //关键语句
            mMediaPlayer.reset();

            mMediaPlayer.release();
            mMediaPlayer = null;
        }
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
