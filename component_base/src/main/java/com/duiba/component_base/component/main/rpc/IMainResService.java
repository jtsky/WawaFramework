package com.duiba.component_base.component.main.rpc;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.duiba.component_base.callback.CallBack;

/**
 * @author: jintai
 * @time: 2017/11/6-18:37
 * @Email: jintai@qccr.com
 * @desc: 定义主模块组件对外提供的资源接口
 */
public interface IMainResService extends IProvider {
    int provideGirl(CallBack callBack);
}
