package com.duiba.component_user.service_impl;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.user.path.UserRouterPath;
import com.duiba.component_base.component.user.rpc.IUserOtherService;

/**
 * @author: jintai
 * @time: 2017/11/6-19:02
 * @Email: jintai@duiba.com.cn
 * @desc:用户其他服务实现
 */
@Route(path = UserRouterPath.USER_SERVER_OTHER)
public class UserOtherServiceImpl implements IUserOtherService {
  private static final String TAG = "UserOtherServiceImpl";

  @Override public void printUserOtherService() {
    Log.v(TAG, "===UserOtherService===");
  }

  @Override public void init(Context context) {

  }
}
