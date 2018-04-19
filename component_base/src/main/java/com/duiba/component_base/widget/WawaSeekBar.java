package com.duiba.component_base.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.blankj.utilcode.util.ConvertUtils;
import com.duiba.component_base.R;
import com.duiba.component_base.interfaces.OnWawaSeekBarChangeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/4/9-14:18
 * 描    述：倒计时进度条
 * 修订历史：
 * ================================================
 */
public class WawaSeekBar extends FrameLayout {
    private static final String TAG = "WawaSeekBar";
    private int mWidth;
    private int mHeight;

    int mProgressbarHeight;
    int mProgressbarMargin;
    int mDefaultProgress;
    boolean mIsCountDown;
    /**
     * 普通状态
     */
    int mProgressBarThumbCommResId;
    /**
     * 激活状态
     */
    int mProgressBarThumbActivityResId;
    /**
     * 普通进度条样式
     */
    int mProgressStyleCommon;
    /**
     * 倒计时最后的进度条样式
     */
    int mProgressStyleLast;
    /**
     * 倒计时最后进度条改变的时机
     */
    float mProgreeBarChangeByProgress;

    SeekBar mSeekBar;
    ImageView mThumb;


    /**
     * 静止状态下的进度条thumb
     */
    Drawable mCommonThumb;
    /**
     * 运动状态下的进度条thumb
     */
    Drawable mActivityThumb;
    LinearLayout mPointWrap;
    /**
     * 对应 mPointWrap
     */
    List<LinearLayout> mPointViews = new ArrayList<>();
    Context mContext;
    float[] mArgsProgress;
    String[] mArgsTicket;
    String[] mArgsTip;

    OnWawaSeekBarChangeListener mSeekBarChangeListener;

    public void setSeekBarChangeListener(OnWawaSeekBarChangeListener seekBarChangeListener) {
        this.mSeekBarChangeListener = seekBarChangeListener;
    }

    /**
     * 获取thumb在屏幕中的位置
     * https://blog.csdn.net/tmj2014/article/details/53283804 getLocationInWindow和getLocationOnScreen的异同
     * 当控件处于activity中时，两者的值是一致的
     *
     * @return point
     */
    public Point getSeekBarThumbPos() {
        Point point = new Point();
        int[] rect0 = new int[2];
        if (mCommonThumb != null) {
            //mSeekBar.getLocationInWindow(rect0);
            mSeekBar.getLocationOnScreen(rect0);
            //Logger.v("rect0[0]===>" + rect0[0] + "  rect0[1]===>"+ rect0[1]  + "  rect1[0]===>" + rect1[0] + "   rect1[1]===>"+rect1[1]);
        }

        point.x = (int) (rect0[0] + mWidth * mSeekBar.getProgress() / 100.0);
        point.y = rect0[1];

        return point;
    }

    public SeekBar getSeekBar() {
        if (mSeekBar != null) {

            return mSeekBar;
        }
        return null;

    }

    public WawaSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签
        //即属性集合的标签，在R文件中名称为R.styleable+name
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.base_wawa_seekbar);
        mProgressbarHeight = a.getDimensionPixelSize(R.styleable.base_wawa_seekbar_base_progressbar_height, ConvertUtils.dp2px(20));
        mProgressbarMargin = a.getDimensionPixelSize(R.styleable.base_wawa_seekbar_base_progressbar_margin, 0);
        mDefaultProgress = a.getInt(R.styleable.base_wawa_seekbar_base_default_progress, 0);
        mIsCountDown = a.getBoolean(R.styleable.base_wawa_seekbar_base_count_down, false);
        mProgressBarThumbCommResId = a.getResourceId(R.styleable.base_wawa_seekbar_base_progress_thumb_common, -1);
        mProgressBarThumbActivityResId = a.getResourceId(R.styleable.base_wawa_seekbar_base_progress_thumb_activity, -1);
        mProgressStyleCommon = a.getResourceId(R.styleable.base_wawa_seekbar_base_progress_style_common, R.drawable.base_countdown_progressbar);
        mProgressStyleLast = a.getResourceId(R.styleable.base_wawa_seekbar_base_progress_style_last, R.drawable.base_countdown_progressbar2);
        //回收
        a.recycle();
        addChildView(context);
        //添加最后整个控件的高度监听
        getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            mWidth = getWidth() - mProgressbarMargin * 2;
            mHeight = getHeight();
//            Logger.t(TAG).v("width===>" + mWidth + "  height==>" + mHeight);

            //测量完毕并添加指针views
            if (mPointWrap.getChildCount() == 0 && mArgsProgress != null) {
                addPointViews();
            }

        });

    }


    private int mPointWidth_dp = 70;
    private int mPointIcWidth_px = 40;
    /**
     * thumb中完整显示星星的偏移量
     */
    private int mThumbStarOffset_px;
    /**
     * seekbar 距离底部的margin
     */
    private int mSeekBarBottom_px = 0;
    /**
     * thumb 的高度
     */
    private int mThumbHeight_px = 0;
    /**
     * thumb 的宽度
     */
    private int mThumbWidth_px = 0;
    /**
     * thumb 图片的宽高比
     */
    private float mThumbRatio = (float) (5 / 2.0);

    /**
     * 动态添加指针views
     */
    private void addPointViews() {

        if (mThumb != null && mWidth != 0) {
            FrameLayout.LayoutParams thumbParams = (LayoutParams) mThumb.getLayoutParams();
            thumbParams.height = mThumbHeight_px;
            thumbParams.width = mThumbWidth_px;
            thumbParams.setMargins(((int) (mWidth * mSeekBar.getProgress() / 100.0) - mThumbWidth_px + mThumbStarOffset_px), 0, 0, 0);
            startThumbAnim(mCommonThumb);
        }
        //设置llWrap的宽度
        ViewGroup.LayoutParams params = mPointWrap.getLayoutParams();
        params.width = mWidth;
        params.height = LayoutParams.WRAP_CONTENT;
        mPointWrap.setLayoutParams(params);

        mPointWrap.removeAllViews();
        mPointViews.clear();
        for (int i = 0; i < mArgsProgress.length; i++) {
            LinearLayout pointView = (LinearLayout) View.inflate(mContext, R.layout.base_point, null);
            mPointWrap.addView(pointView);
            mPointViews.add(pointView);
            TextView tvTicket = pointView.findViewById(R.id.tv_ticket);
            TextView tvTip = pointView.findViewById(R.id.tv_tip);
            ImageView ivPoint = pointView.findViewById(R.id.iv_point);
            if (mIsCountDown) {
                tvTicket.setVisibility(GONE);
                tvTip.setVisibility(VISIBLE);
                if (mArgsTip != null) {
                    tvTip.setText(mArgsTip[i]);
                }

            } else {
                tvTip.setVisibility(GONE);
                tvTicket.setVisibility(VISIBLE);
                if (mArgsTicket != null) {
                    tvTicket.setText(mArgsTicket[i]);
                }
            }

            LinearLayout.LayoutParams pointParams = new LinearLayout.LayoutParams(ConvertUtils.dp2px(mPointWidth_dp), LinearLayout.LayoutParams.WRAP_CONTENT);
            int marginLeft;
            //积分
            if (!mIsCountDown) {
                if (i == 0) {
                    marginLeft = (int) (mWidth * mArgsProgress[i] - ConvertUtils.dp2px(mPointWidth_dp / 3));
                } else {
                    marginLeft = (int) (mWidth * (mArgsProgress[i] - mArgsProgress[i - 1]) - ConvertUtils.dp2px(mPointWidth_dp));
                }
                pointParams.setMargins(marginLeft, 0, 0, mProgressbarHeight + mSeekBarBottom_px - 10);
            } else {//倒计时
                if (i == 0) {
                    //40为图标的宽度
                    marginLeft = (int) (mWidth * mArgsProgress[i] - ConvertUtils.dp2px((float) (mPointWidth_dp / 1.8)));
                } else {
                    marginLeft = (int) (mWidth * (mArgsProgress[i] - mArgsProgress[i - 1]) - ConvertUtils.dp2px(mPointWidth_dp));
                }
                pointParams.setMargins(marginLeft, 0, 0, (int) ((mProgressbarHeight - tvTip.getTextSize()) / 2));
            }

            pointView.setLayoutParams(pointParams);

        }

        if (mIsCountDown) {
            hideAllChild();
            mPointWrap.getChildAt(mPointWrap.getChildCount() - 1).setVisibility(VISIBLE);
        }
    }

    /**
     * 启动动画
     *
     * @param thumb
     */
    private void startThumbAnim(Drawable thumb) {
        if (thumb != null) {
            mThumb.setImageDrawable(thumb);
            //thumb赋值
            if (thumb instanceof AnimationDrawable) {
                ((AnimationDrawable) thumb).start();
            }
        }
    }

    /**
     * 添加子控件
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void addChildView(Context context) {
        View countDownView = View.inflate(context, R.layout.base_seekbar, this);
        //获取thumb
        try {
            mCommonThumb = getResources().getDrawable(mProgressBarThumbCommResId);
            mActivityThumb = getResources().getDrawable(mProgressBarThumbActivityResId);
            //为了居中
            mThumbHeight_px = mProgressbarHeight * 2;
            mThumbWidth_px = (int) (mThumbHeight_px * mThumbRatio);
            //因为星星占图片高度的1/2 并且上下留白对称
            mSeekBarBottom_px = (int) (mProgressbarHeight / 2.0);
            mThumbStarOffset_px = (int) (mThumbWidth_px * 0.3) + mProgressbarMargin;
        } catch (Resources.NotFoundException e) {
            mCommonThumb = null;
            mActivityThumb = null;
        }

        //设置SeekBar的高度
        mSeekBar = countDownView.findViewById(R.id.seekbar);
        FrameLayout.LayoutParams seekbarParams = (FrameLayout.LayoutParams) mSeekBar.getLayoutParams();
        seekbarParams.height = mProgressbarHeight;
        seekbarParams.leftMargin = mProgressbarMargin;
        seekbarParams.rightMargin = mProgressbarMargin;
        seekbarParams.bottomMargin = mSeekBarBottom_px;

        mSeekBar.setProgressDrawable(getResources().getDrawable(mProgressStyleCommon));
        mSeekBar.setProgress(mDefaultProgress);
        mSeekBar.setSecondaryProgress(mDefaultProgress);
        //设置thumb不能手动拖动
        mSeekBar.setOnTouchListener((v, event) -> true);
        mSeekBar.setSplitTrack(false);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //积分条进度条thumb的位置判定
                //不用原生的原因 参考：SeekBar的thumbOffset属性 https://blog.csdn.net/xiao5678yun/article/details/77774607
                if (mCommonThumb != null) {
                    FrameLayout.LayoutParams params = (LayoutParams) mThumb.getLayoutParams();
                    params.setMargins(((int) (mWidth * progress / 100.0) - mThumbWidth_px + mThumbStarOffset_px), 0, 0, 0);
                    mThumb.setLayoutParams(params);
                }
                //倒计时
                if (mIsCountDown) {
                    //隐藏当前指针 显示下一个指针
                    if (mArgsProgress != null) {
                        for (int i = 0; i < mArgsProgress.length; i++) {
                            if (progress == (int) (mArgsProgress[i] * 100)) {
                                //Logger.t(TAG).v("progress == mArgsProgress" + mArgsProgress[i]);
                                hideAllChild();
                                if (mPointWrap.getChildAt(i - 1) != null) {
                                    mPointWrap.getChildAt(i - 1).setVisibility(VISIBLE);
                                }
                            } else {
                                continue;
                            }
                        }
                    }

                    //改变进度条样式
                    if (progress == (int) (mProgreeBarChangeByProgress * 100)) {
                        mSeekBar.setProgressDrawable(getResources().getDrawable(mProgressStyleLast));
                    }
                    //回调进度
                    if (mSeekBarChangeListener != null) {
                        mSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
                    }

                }
                //积分的判定 当处于重置状态下的处理
                else if (isReseting) {
                    if (progress == 100) {
                        if (mAddDisposable != null) {
                            mAddDisposable.dispose();
                            mAddDisposable = null;
                        }
                        mReduceDisposable = Observable.interval(0, 5, TimeUnit.MILLISECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(l -> {
                                    if (mReduceDisposable == null || mReduceDisposable.isDisposed()) {
                                        return;
                                    }

                                    if (Math.abs(getProgress() - mResetProgress) == 1) {
                                        setProgress(getProgress() - 2);
                                    } else {
                                        setProgress(getProgress() - 1);
                                    }

                                });
                    } else if (progress == 0) {
                        if (mReduceDisposable != null) {
                            mReduceDisposable.dispose();
                            mReduceDisposable = null;
                        }

                        mResetDisposable = Observable.interval(0, 60, TimeUnit.MILLISECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(l -> {
                                    if (mResetDisposable == null || mResetDisposable.isDisposed()) {
                                        return;
                                    }
                                    setProgress(getProgress() + 1);

                                });

                    } else if (progress == mResetProgress) {
                        if (mResetDisposable != null) {
                            mResetDisposable.dispose();
                            mResetDisposable = null;
                        }
                        isReseting = false;
                    }
                } else if (mSeekBarChangeListener != null && !isReseting) {
                    cancleDisposables();
                    mSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
                }

                //判断进度条时候到达指定的位置
                if (mArgsProgress != null && mPointViews.size() == mArgsProgress.length) {
                    for (int i = 0; i < mArgsProgress.length; i++) {
                        int targetProgress = (int) (mArgsProgress[i] * 100);
                        if (targetProgress == progress) {
                            ImageView point = mPointViews.get(i).findViewById(R.id.iv_point);
                            mSeekBarChangeListener.onProgressArrive2Point(point, progress);
                        }
                    }
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //获取自定义thumb
        mThumb = countDownView.findViewById(R.id.iv_thumb);

        //获取包装LinearLayout
        mPointWrap = countDownView.findViewById(R.id.ll_point);
    }


    public void updateScoreData(float[] argsProgress, String[] argsTicket) {
        if (mIsCountDown) {
            return;
        }
        if (argsProgress.length != argsTicket.length) {
            throw new RuntimeException("argsProgrss argsTicket 两者的长度必须一致");

        }
        mArgsProgress = argsProgress;
        mArgsTicket = argsTicket;
        //表示当前控件还没测量完毕，直接返回等待测量完成以后在添加子view
        if (mWidth == 0) {
            return;
        }
        addPointViews();
    }

    /**
     * 设置积分的指针的显示位置
     *
     * @param argsProgress
     * @param argsTicket
     */
    public void setScoreData(float[] argsProgress, String[] argsTicket) {
        if (argsProgress.length != argsTicket.length) {
            throw new RuntimeException("argsProgrss argsTicket 两者的长度必须一致");

        }
        mArgsProgress = argsProgress;
        mArgsTicket = argsTicket;
        //表示当前控件还没测量完毕，直接返回等待测量完成以后在添加子view
        if (mWidth == 0) {
            return;
        }
        addPointViews();
    }

    /**
     * 设置倒计时的指针的显示位置
     *
     * @param argsProgress               进度数组 只允许小数
     * @param argsTip
     * @param progreeBarChangeByProgress 改变进度条样式进度
     */
    public void setCountdownData(float[] argsProgress, String[] argsTip, float progreeBarChangeByProgress) {
        if (argsProgress != null && argsTip != null) {
            if (argsProgress.length != argsTip.length) {
                throw new RuntimeException("argsProgrss argsTicket 两者的长度必须一致");
            }
        }
        mArgsProgress = argsProgress;
        mArgsTip = argsTip;
        mProgreeBarChangeByProgress = progreeBarChangeByProgress;
        //表示当前控件还没测量完毕，直接返回等待测量完成以后在添加子view
        if (mWidth == 0) {
            return;
        }
        addPointViews();
    }

    Disposable mAddDisposable;
    Disposable mReduceDisposable;
    Disposable mResetDisposable;
    Disposable mCommonDisposable;
    boolean isReseting;
    int mResetProgress;

    /**
     * 取消所有定时器
     */
    private void cancleDisposables() {
        if (mAddDisposable != null) {
            mAddDisposable.dispose();
            mAddDisposable = null;
        }
        if (mReduceDisposable != null) {
            mReduceDisposable.dispose();
            mReduceDisposable = null;
        }
        if (mResetDisposable != null) {
            mResetDisposable.dispose();
            mResetDisposable = null;
        }

    }


    /**
     * 积分专用
     *
     * @param progress 需要重置的进度
     */
    public void reset(int progress) {
        isReseting = true;
        if (mSeekBar == null) {
            return;
        }
        if (mIsCountDown) {
            return;
        }
        mResetProgress = progress;
        if (getProgress() == 100) {
            mReduceDisposable = Observable.interval(0, 5, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(l -> {
                        if (mReduceDisposable == null || mReduceDisposable.isDisposed()) {
                            return;
                        }

                        if (Math.abs(getProgress() - mResetProgress) == 1) {
                            setProgress(getProgress() - 2);
                        } else {
                            setProgress(getProgress() - 1);
                        }

                    });
        } else {
            mAddDisposable = Observable.interval(0, 5, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(l -> {
                        if (mAddDisposable == null || mAddDisposable.isDisposed()) {
                            return;
                        }

                        if (Math.abs(getProgress() - mResetProgress) == 1) {
                            setProgress(getProgress() + 2);
                        } else {
                            setProgress(getProgress() + 1);
                        }
                    });
        }


    }

    public void setProgress(int progress) {
        if (mSeekBar == null) {
            return;
        }
        mSeekBar.setProgress(progress);
        mSeekBar.setSecondaryProgress(progress);
    }

    /**
     * 积分专用 动效递增
     *
     * @param progress
     */
    public void setProgressWithAnim(int progress) {
        if (mSeekBar == null) {
            return;
        }

        //启动激活动画
        startThumbAnim(mActivityThumb);

        if (Math.abs(progress - getProgress()) <= 5) {
            setProgress(progress);
        } else {
            mCommonDisposable = Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(l -> {
                        if (getProgress() == progress) {
                            if (mCommonDisposable != null) {
                                mCommonDisposable.dispose();
                                mCommonDisposable = null;
                                //重置为静止动画
                                startThumbAnim(mCommonThumb);
                            }
                        } else {
                            setProgress(getProgress() + 1);
                        }

                    });
        }
    }

    public int getProgress() {
        if (mSeekBar == null) {
            return 0;
        }
        return mSeekBar.getProgress();
    }

    private void hideAllChild() {
        for (int i = 0; i < mPointWrap.getChildCount(); i++) {
            mPointWrap.getChildAt(i).setVisibility(INVISIBLE);
        }
    }


}
