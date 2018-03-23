package com.duiba.component_main.service_impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.callback.CallBack;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.duiba.component_base.component.main.rpc.IMainResService;

/**
 * @author: jintai
 * @time: 2017/11/6-19:02
 * @Email: jintai@duiba.com.cn
 * @desc:主模块组件资源服务
 */
@Route(path = MainRouterPath.MAIN_SERVER_RES)
public class MainResServiceImpl implements IMainResService {


    @Override
    public void init(Context context) {

    }

    @Override
    public int provideGirl(CallBack callBack) {
        return 0;
    }
}
