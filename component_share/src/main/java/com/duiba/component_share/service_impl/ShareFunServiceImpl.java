package com.duiba.component_share.service_impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.duiba.component_base.component.share.path.ShareRouterPath;
import com.duiba.component_base.component.share.rpc.IShareFunService;

/**
 * @author: jintai
 * @time: 2017/11/6-19:02
 * @Email: jintai@qccr.com
 * @desc:分享组件功能服务
 */
@Route(path = ShareRouterPath.SHARE_SERVER_FUN)
public class ShareFunServiceImpl implements IShareFunService {
    @Override
    public void init(Context context) {

    }
}
