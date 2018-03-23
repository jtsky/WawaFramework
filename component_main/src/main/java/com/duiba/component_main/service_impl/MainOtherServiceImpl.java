package com.duiba.component_main.service_impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.duiba.component_base.component.main.rpc.IMainOtherService;

/**
 * @author: jintai
 * @time: 2017/11/6-19:02
 * @Email: jintai@duiba.com.cn
 * @desc:主模块组件其他服务
 */
@Route(path = MainRouterPath.MAIN_SERVER_OTHER)
public class MainOtherServiceImpl implements IMainOtherService {
  private static final String TAG = "ShareOtherService";


  @Override public void init(Context context) {

  }

  @Override
  public String routerByMainPath() {
    return null;
  }
}
