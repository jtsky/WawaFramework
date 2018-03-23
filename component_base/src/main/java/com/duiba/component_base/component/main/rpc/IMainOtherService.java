package com.duiba.component_base.component.main.rpc;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author: jintai
 * @time: 2017/11/6-18:37
 * @Email: jintai@duiba.com.cn
 * @desc: 定义主模块组件对外提供的其他接口
 */
public interface IMainOtherService extends IProvider {
    /**
     * ss
     */
    String routerByMainPath();
}
