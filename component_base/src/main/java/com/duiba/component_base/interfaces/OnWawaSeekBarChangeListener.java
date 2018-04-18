package com.duiba.component_base.interfaces;

import android.view.View;
import android.widget.SeekBar;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/4/12-17:57
 * 描    述：自定义seekbar进度变化接口
 * 修订历史：
 * ================================================
 */
public interface OnWawaSeekBarChangeListener {
    /**
     * 进度条进度改变时的回调函数
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);

    /**
     * 当进度条到达指定进度时的回调
     * @param view 指定位置的view
     * @param progress 当前进度
     */
    void onProgressArrive2Point(View view, int progress);

}
