package com.duiba.component_base.interfaces;

import android.widget.ImageView;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/4/19-21:36
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface DataMatchListener {
    /**
     * 普通字符匹配方法
     *
     * @param text
     * @return
     */
    ImageView matchString(String text);

    /**
     * 数字字符匹配方法
     *
     * @param num
     * @return
     */
    ImageView matchNum(String num);
}

