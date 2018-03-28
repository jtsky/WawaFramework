package com.duiba.component_user.bean;

import com.duiba.component_base.component.user.interfaces.IUserFunObj;
import com.orhanobut.logger.Logger;

/**
 * @author: jintai
 * @time: 2018/3/28-16:52
 * @Email: jintai@qccr.com
 * @desc:
 */
public class UserFunObj implements IUserFunObj {
    @Override
    public void call() {
        Logger.v("我是来自user模块的回调");
    }
}
