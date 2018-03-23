package com.duiba.component_share.service_impl;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.share.path.ShareRouterPath;
import com.duiba.component_base.component.share.rpc.IShareOtherService;

/**
 * @author: jintai
 * @time: 2017/11/6-19:02
 * @Email: jintai@duiba.com.cn
 * @desc:分享组件其他服务
 */
@Route(path = ShareRouterPath.SHARE_SERVER_OTHER)
public class ShareOtherServiceImpl implements IShareOtherService {

    @Override
    public void printShareService() {

    }

    @Override
    public void init(Context context) {

    }
}
