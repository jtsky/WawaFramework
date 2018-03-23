package com.duiba.component_main.service_impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.duiba.component_base.component.main.rpc.IMainViewService;

/**
 * @author: jintai
 * @time: 2017/11/6-19:02
 * @Email: jintai@duiba.com.cn
 * @desc:主模块组件控件服务
 */
@Route(path = MainRouterPath.MAIN_SERVER_VIEW)
public class MainViewServiceImpl implements IMainViewService {

  @Override public void init(Context context) {

  }
}
