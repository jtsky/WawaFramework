package com.duiba.component_base.component.user.rpc;

import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author: jintai
 * @time: 2017/11/6-18:37
 * @Email: jintai@qccr.com
 * @desc: 定义用户组件对外提供的资源接口
 */
public interface IUserResService extends IProvider {

    @NonNull
    String provideText();
}
