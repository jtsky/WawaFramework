package com.duiba.component_base.component;

import android.arch.lifecycle.ViewModel;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * @author: jintai
 * @time: 2018/3/23-15:20
 * @Email: jintai@qccr.com
 * @desc:
 */
public interface DuibaMvpView<Model extends ViewModel> extends MvpView {
    /**
     * 给presenter绑定ViewModel
     */
    Model getViewModel();
}
