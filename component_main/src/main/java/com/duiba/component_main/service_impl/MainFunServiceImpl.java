package com.duiba.component_main.service_impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.duiba.component_base.component.main.rpc.IMainFunService;

/**
 * @author: jintai
 * @time: 2017/11/6-19:02
 * @Email: jintai@qccr.com
 * @desc:主模块组件功能服务
 */
@Route(path = MainRouterPath.MAIN_SERVER_FUN)
public class MainFunServiceImpl implements IMainFunService {
    @Override
    public void init(Context context) {

    }
}
