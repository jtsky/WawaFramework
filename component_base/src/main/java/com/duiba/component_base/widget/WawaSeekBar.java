package com.duiba.component_base.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.duiba.component_base.R;
import com.duiba.component_base.interfaces.OnWawaSeekBarChangeListener;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
    int mDefaultProgress;
    boolean mIsCountDown;
    int mProgressThumbOffset;
    int mProgressBarThumb;
    int mProgressStyleCommon;
    int mProgressStyleLast;


    SeekBar mSeekBar;
    LinearLayout mPointWrap;
    Context mContext;
    float[] mArgsProgress;
    String[] mArgsTicket;
    String[] mArgsTip;

    OnWawaSeekBarChangeListener mSeekBarChangeListener;

    public void setSeekBarChangeListener(OnWawaSeekBarChangeListener seekBarChangeListener) {
        this.mSeekBarChangeListener = seekBarChangeListener;
    }

    public WawaSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScoreData(context, attrs);
    }


    private void setScoreData(Context context, AttributeSet attrs) {
        mContext = context;
        //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签
        //即属性集合的标签，在R文件中名称为R.styleable+name
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.base_wawa_seekbar);
        mProgressbarHeight = a.getDimensionPixelSize(R.styleable.base_wawa_seekbar_base_progressbar_height, ConvertUtils.dp2px(20));
        mDefaultProgress = a.getInt(R.styleable.base_wawa_seekbar_base_default_progress, 0);
        mIsCountDown = a.getBoolean(R.styleable.base_wawa_seekbar_base_count_down, false);
        mProgressThumbOffset = a.getDimensionPixelOffset(R.styleable.base_wawa_seekbar_base_progress_thumbOffset, ConvertUtils.dp2px(20));
        mProgressBarThumb = a.getResourceId(R.styleable.base_wawa_seekbar_base_progress_thumb, R.mipmap.base_ic_cursor);
        mProgressStyleCommon = a.getResourceId(R.styleable.base_wawa_seekbar_base_progress_style_common, R.drawable.base_countdown_progressbar);
        mProgressStyleLast = a.getResourceId(R.styleable.base_wawa_seekbar_base_progress_style_last, R.drawable.base_countdown_progressbar2);
        //回收
        a.recycle();
        addChildView(context);
        //添加最后整个控件的高度监听
        getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            mWidth = getWidth();
            mHeight = getHeight();
//            Logger.t(TAG).v("width===>" + mWidth + "  height==>" + mHeight);

            //测量完毕并添加指针views
            if (mPointWrap.getChildCount() == 0 && mArgsProgress != null) {
                addPointViews();
            }

        });

    }

    private int mPointWidth = 120;

    /**
     * 动态添加指针views
     */
    private void addPointViews() {
        mPointWrap.removeAllViews();

        for (int i = 0; i < mArgsProgress.length; i++) {
            LinearLayout pointView = (LinearLayout) View.inflate(mContext, R.layout.base_point, null);
            mPointWrap.addView(pointView);
            LinearLayout.LayoutParams pointParams = new LinearLayout.LayoutParams(ConvertUtils.dp2px(mPointWidth), LinearLayout.LayoutParams.WRAP_CONTENT);
            int marginLeft;
            if (i == 0) {
                marginLeft = (int) (mWidth * mArgsProgress[i] - ConvertUtils.dp2px(mPointWidth / 2));
            } else {
                marginLeft = (int) (mWidth * (mArgsProgress[i] - mArgsProgress[i - 1]) - ConvertUtils.dp2px(mPointWidth));
            }

            pointParams.setMargins(marginLeft, 0, 0, 0);
            pointView.setLayoutParams(pointParams);
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
                tvTip.setVisibility(INVISIBLE);
                tvTicket.setVisibility(VISIBLE);
                if (mArgsTicket != null) {
                    tvTicket.setText(mArgsTicket[i]);
                }

            }

        }
        if (mIsCountDown) {
            hideAllChild();
            mPointWrap.getChildAt(mPointWrap.getChildCount() - 1).setVisibility(VISIBLE);
        }
        //Logger.t(TAG).v("childCount====>" + mPointWrap.getChildCount());

    }

    /**
     * 添加子控件
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void addChildView(Context context) {
        View countDownView = View.inflate(context, R.layout.base_seekbar, this);
        //设置SeekBar的高度
        mSeekBar = countDownView.findViewById(R.id.seekbar);
        mSeekBar.setMinimumHeight(mProgressbarHeight);
        mSeekBar.setProgressDrawable(getResources().getDrawable(mProgressStyleCommon));
        mSeekBar.setProgress(mDefaultProgress);
        mSeekBar.setSecondaryProgress(mDefaultProgress);
        mSeekBar.setOnTouchListener((v, event) -> true);
        if (mIsCountDown) {
            mSeekBar.setThumb(null);
            mSeekBar.setThumbOffset(0);
            mSeekBar.setSplitTrack(false);
        } else {
            try {
                Drawable thumb = getResources().getDrawable(mProgressBarThumb);
                if (thumb != null) {
                    mSeekBar.setThumb(thumb);
                    if (thumb instanceof AnimationDrawable) {
                        ((AnimationDrawable) thumb).start();
                    }
                } else {
                    mSeekBar.setThumb(null);
                }
            } catch (Exception e) {
                mSeekBar.setThumb(null);
            }
            mSeekBar.setThumbOffset(mProgressThumbOffset);
        }
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //倒计时进度条的判定
                if (mIsCountDown) {
                    for (int i = 0; i < mArgsProgress.length; i++) {
                        if (progress == (int) (mArgsProgress[i] * 100)) {
                            Logger.t(TAG).v("progress == mArgsProgress" + mArgsProgress[i]);
                            hideAllChild();
                            if (mPointWrap.getChildAt(i - 1) != null) {
                                mPointWrap.getChildAt(i - 1).setVisibility(VISIBLE);
                            }
                        } else {
                            continue;
                        }
                    }
                }
                //积分的判定 当处于重置状态下的处理
                else if (isReseting) {
                    if (progress == 100) {
                        if (mAddDisposable != null) {
                            mAddDisposable.dispose();
                            mAddDisposable = null;
                        }
                        mReduceDisposable = Observable.interval(0, 5, TimeUnit.MILLISECONDS).subscribe(l -> {
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

                        mResetDisposable = Observable.interval(0, 60, TimeUnit.MILLISECONDS).subscribe(l -> {
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

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //获取包装LinearLayout
        mPointWrap = countDownView.findViewById(R.id.ll_point);
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
     * @param argsProgress
     * @param argsTip
     */
    public void setCountdownData(float[] argsProgress, String[] argsTip) {
        if (argsProgress.length != argsTip.length) {
            throw new RuntimeException("argsProgrss argsTicket 两者的长度必须一致");

        }
        mArgsProgress = argsProgress;
        mArgsTip = argsTip;
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
        mAddDisposable = Observable.interval(0, 5, TimeUnit.MILLISECONDS).subscribe(l -> {
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
        if (Math.abs(progress - getProgress()) <= 5) {
            setProgress(progress);
        } else {
            mCommonDisposable = Observable.interval(0, 100, TimeUnit.MILLISECONDS).subscribe(l -> {
                if (getProgress() == progress) {
                    if (mCommonDisposable != null) {
                        mCommonDisposable.dispose();
                        mCommonDisposable = null;
                    }
                }
                setProgress(getProgress() + 1);
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
