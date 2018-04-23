package com.duiba.component_base.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.duiba.component_base.bean.WawaNumBean;
import com.duiba.component_base.interfaces.DataMatchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/4/19-20:51
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class WawaNumView extends LinearLayout {
    private static final String TAG = "WawaNumView";

    public WawaNumView(Context context) {
        super(context);
    }

    public WawaNumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public void setData(List<WawaNumBean> datas, DataMatchListener dataMatch) {
        List<ImageView> imageViews = new ArrayList<>();
        for (WawaNumBean numBean : datas) {
            if (numBean.getDataType() == WawaNumBean.WawaNumDataType.STRING) {
                String text = (String) numBean.getData();
                //匹配普通字符
                if (!TextUtils.isEmpty(text)) {
                    imageViews.add(dataMatch.matchString(text));
                }
            } else if (numBean.getDataType() == WawaNumBean.WawaNumDataType.NUM) {
                String num = numBean.getData().toString();
                String[] nums = num.split("");
                for (String s : nums) {
                    //匹配数字字符
                    if (!TextUtils.isEmpty(s)) {
                        imageViews.add(dataMatch.matchNum(s));
                    }

                }

            }
        }
        removeAllViews();
        for (ImageView imageView : imageViews) {

            addView(imageView);
            LinearLayout.LayoutParams linearParam = (LayoutParams) imageView.getLayoutParams();
            linearParam.gravity = Gravity.CENTER_VERTICAL;
        }

    }


}
