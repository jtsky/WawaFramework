package com.duiba.component_base.component.user.rpc;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.duiba.component_base.component.user.interfaces.IUserFunObj;

/**
 * @author: jintai
 * @time: 2017/11/6-18:37
 * @Email: jintai@duiba.com.cn
 * @desc: 定义用户组件对外提供的功能接口
 */
public interface IUserFunService extends IProvider {

  IUserFunObj provideObj();
}
