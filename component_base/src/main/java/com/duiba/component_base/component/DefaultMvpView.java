package com.duiba.component_base.component;

import android.arch.lifecycle.ViewModel;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * @author: jintai
 * @time: 2018/3/23-15:20
 * @Email: jintai@qccr.com
 * @desc: 不建议被继承 如有需要请继承DuibaMvpView
 */
public class DefaultMvpView implements  DuibaMvpView{

    @Override
    public ViewModel getViewModel() {
        return null;
    }
}
