package com.duiba.component_share.service_impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.share.path.ShareRouterPath;
import com.duiba.component_base.component.share.rpc.IShareResService;
import com.duiba.component_share.R;

/**
 * @author: jintai
 * @time: 2017/11/6-19:02
 * @Email: jintai@qccr.com
 * @desc:分享组件资源服务
 */
@Route(path = ShareRouterPath.SHARE_SERVER_RES)
public class ShareResServiceImpl implements IShareResService {

    @Override
    public int provideGirl() {
        return R.mipmap.share_girl;
    }

    @Override
    public void init(Context context) {

    }
}
