package com.duiba.component_user.service_impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.user.path.UserRouterPath;
import com.duiba.component_base.component.user.rpc.IUserResService;
import com.orhanobut.logger.Logger;

/**
 * @author: jintai
 * @time: 2017/11/6-19:02
 * @Email: jintai@duiba.com.cn
 * @desc:用户资源服务实现
 */
@Route(path = UserRouterPath.USER_SERVER_RES)
public class UserResServiceImpl implements IUserResService {
    @Override
    public void init(Context context) {
        Logger.v("UserResServiceImpl=====>Init");
    }

    @Override
    public String provideText() {
        String text = "我来自UserResService";
        return text;
    }
}
