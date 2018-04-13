package com.duiba.component_base.interfaces;

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
    void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);

}
