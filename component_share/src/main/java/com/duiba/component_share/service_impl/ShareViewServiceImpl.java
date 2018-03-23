package com.duiba.component_share.service_impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.share.path.ShareRouterPath;
import com.duiba.component_base.component.share.rpc.IShareViewService;

/**
 * @author: jintai
 * @time: 2017/11/6-19:02
 * @Email: jintai@duiba.com.cn
 * @desc:分享组件控件服务
 */
@Route(path = ShareRouterPath.SHARE_SERVER_VIEW)
public class ShareViewServiceImpl implements IShareViewService {

    @Override
    public void init(Context context) {

    }
}
