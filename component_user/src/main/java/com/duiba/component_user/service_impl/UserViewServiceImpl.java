package com.duiba.component_user.service_impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.user.path.UserRouterPath;
import com.duiba.component_base.component.user.rpc.IUserViewService;

/**
 * @author: jintai
 * @time: 2017/11/6-19:02
 * @Email: jintai@duiba.com.cn
 * @desc:用户控件服务实现
 */
@Route(path = UserRouterPath.USER_SERVER_VIEW)
public class UserViewServiceImpl implements IUserViewService {
    @Override
    public void init(Context context) {

    }
}
