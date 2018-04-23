package com.duiba.component_base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.duiba.component_base.bean.WawaNumBean;
import com.duiba.component_base.interfaces.DataMatchListener;

import java.util.List;


/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/4/19-18:47
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ScoreBoomView extends FrameLayout {
    private static final String TAG = "ScoreBoomView";
    private int mWidth;
    private int mHeight;
    int mLayoutWidth;
    int mLayoutHeight;
    private Context mContext;

    public ScoreBoomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (mWidth != 0 || mLayoutWidth == -2) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                mWidth = getWidth();
                mHeight = getHeight();
                Log.v(TAG, "mWidth===>" + mWidth + "  mHeight===>" + mHeight);
                mLayoutWidth = getLayoutParams().width;
                mLayoutHeight = getLayoutParams().height;
                if (mWidth != 0 || mLayoutWidth == -2) {
                    locationChild();
                }

            }
        });

    }

    WawaNumView mBottomView;
    WawaNumView mTopView;
    LottieAnimationView mLottie;

    private void init(AttributeSet attrs) {
        mBottomView = new WawaNumView(mContext);

        mTopView = new WawaNumView(mContext);

        mLottie = new LottieAnimationView(mContext);

        addView(mBottomView);
        addView(mLottie);
        addView(mTopView);
        mBottomView.setVisibility(INVISIBLE);
        mTopView.setVisibility(INVISIBLE);
        //设置bottomView布局参数
        FrameLayout.LayoutParams bottomParam = (LayoutParams) mBottomView.getLayoutParams();
        bottomParam.width = LayoutParams.WRAP_CONTENT;
        bottomParam.height = LayoutParams.WRAP_CONTENT;
        //设置centerview布局参数
        FrameLayout.LayoutParams centerParam = (LayoutParams) mLottie.getLayoutParams();
        centerParam.width = LayoutParams.WRAP_CONTENT;
        centerParam.height = LayoutParams.WRAP_CONTENT;
        centerParam.gravity = Gravity.CENTER;
        //设置topView布局参数
        FrameLayout.LayoutParams topParam = (LayoutParams) mTopView.getLayoutParams();
        topParam.width = LayoutParams.WRAP_CONTENT;
        topParam.height = LayoutParams.WRAP_CONTENT;


    }

    int mLottieWidth;
    int mLottieHeight;

    //private boolean hasLoc
    private void locationChild() {
        ViewTreeObserver vto = mLottie.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLottieWidth = mLottie.getWidth();
                mLottieHeight = mLottie.getHeight();
                //Log.v(TAG, "mLottieWidth===>" + mLottieWidth + "  mLottieHeight===>" + mLottieHeight);
                if (mLottieWidth != 0) {
                    //设置底部view的位置
                    locationBottomView();
                    //设置顶部view的位置
                    locationTopView();
                    mLottie.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    //先刷新一下的目的是为了去除上一次的残影
                    invalidate();
                }
            }
        });
    }

    private void locationTopView() {
        FrameLayout.LayoutParams topParam = (LayoutParams) mTopView.getLayoutParams();
        if (mLottieWidth == 0 || mTopView.getWidth() == 0 || topParam.leftMargin > 0) {
            return;
        }
        //设置顶部view的位置
        int topWidth = mTopView.getWidth();
        int topHeight = mTopView.getHeight();

        //Log.v(TAG, "topWidth===>" + topWidth + "  topHeight===>" + topHeight);
        topParam.setMargins((int) (mLottieWidth * 0.5 - topWidth / 2.0), (int) (mLottieHeight * 0.39 - topHeight / 2.0), 0, 0);
        mTopView.setLayoutParams(topParam);
    }

    private void locationBottomView() {
        FrameLayout.LayoutParams bottomParam = (LayoutParams) mTopView.getLayoutParams();
        if (mLottieWidth == 0 || mBottomView.getWidth() == 0 || bottomParam.leftMargin > 0) {
            return;
        }
        int bottomWidth = mBottomView.getWidth();
        int bottomHeight = mBottomView.getHeight();
        //Log.v(TAG, "bottomWidth===>" + bottomWidth + "  bottomHeight===>" + bottomHeight);
        bottomParam.setMargins((int) (mLottieWidth * 0.5 - bottomWidth / 2.0), (int) (mLottieHeight * 0.39 - bottomHeight / 2.0), 0, 0);
        mBottomView.setLayoutParams(bottomParam);
    }

    private List<WawaNumBean> mTopBeans;
    private DataMatchListener mTopDataMatch;
    private List<WawaNumBean> mBootomBeans;
    private DataMatchListener mBottomDataMatch;
    private String mLottieImageAssetsFolder;
    private String mLottieAnimation;
    private boolean mLoop;

    public void setTopBeans(List<WawaNumBean> mTopBeans, DataMatchListener topDataMatch) {
        this.mTopBeans = mTopBeans;
        this.mTopDataMatch = topDataMatch;
    }

    public void setBootomBeans(List<WawaNumBean> mBootomBeans, DataMatchListener bottomDataMatch) {
        this.mBootomBeans = mBootomBeans;
        this.mBottomDataMatch = bottomDataMatch;
    }

    public void setLottieIfo(String mLottieImageAssetsFolder, String mLottieAnimation, boolean loop) {
        this.mLottieImageAssetsFolder = mLottieImageAssetsFolder;
        this.mLottieAnimation = mLottieAnimation;
        this.mLoop = loop;
    }


    /**
     * 播放抓中娃娃的动画
     */
    @SuppressLint("CheckResult")
    public void playScoreAnim() {
        //设置底部view的位置
        locationBottomView();

        //设置顶部view的位置
        locationTopView();
        if (mBootomBeans != null && mBottomDataMatch != null) {
            mBottomView.setData(mBootomBeans, mBottomDataMatch);
        }

        if (mTopBeans != null && mTopDataMatch != null) {
            mTopView.setData(mTopBeans, mTopDataMatch);
        }

        if (!TextUtils.isEmpty(mLottieImageAssetsFolder) && !TextUtils.isEmpty(mLottieAnimation)) {
            mLottie.setImageAssetsFolder(mLottieImageAssetsFolder);
            mLottie.setAnimation(mLottieAnimation);
            mLottie.loop(mLoop);
        }


        Animation topScaleBigAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        topScaleBigAnim.setDuration(500);
        topScaleBigAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mTopView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation topScaleSmallAnim = new ScaleAnimation(1.2f, 0.1f, 1.2f, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                topScaleSmallAnim.setDuration(800);
                topScaleSmallAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mTopView.setVisibility(INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                if (mTopBeans != null && mTopDataMatch != null) {
                    mTopView.startAnimation(topScaleSmallAnim);
                }

                Animation bottomScaleBigAnim = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                bottomScaleBigAnim.setDuration(800);
                bottomScaleBigAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mBottomView.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mBottomView.setVisibility(INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                if (mBootomBeans != null && mBottomDataMatch != null) {
                    mBottomView.startAnimation(bottomScaleBigAnim);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (mTopBeans != null && mTopDataMatch != null) {
            mTopView.startAnimation(topScaleBigAnim);
        }

        if (mTopBeans == null && mBootomBeans != null) {
            Animation bottomScaleBigAnim = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            bottomScaleBigAnim.setDuration(1300);
            bottomScaleBigAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mBottomView.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mBottomView.setVisibility(INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mBottomView.startAnimation(bottomScaleBigAnim);
        }

        if (!TextUtils.isEmpty(mLottieImageAssetsFolder) && !TextUtils.isEmpty(mLottieAnimation)) {
            mLottie.playAnimation();
        }

    }


}
