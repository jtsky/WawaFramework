/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.duiba.component_user.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.duiba.component_base.application.BaseApplication;
import com.duiba.component_user.home.view.UserHomeActivity;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


/**
 * 微信客户端回调activity示例
 * @author Jin
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    private BaseResp resp = null;

    public static final int LOGIN_SUCCESS = 1000001;
    public static final int LOGIN_FAILURE = 1000002;
    public static final int LOGIN_CANCEL = 1000003;

    public static final String INTENT_KEY = "WXEntryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getApplication().getWxAPI().handleIntent(getIntent(), this);
    }


    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        finish();
    }


    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        Logger.v(resp.toString());
        Intent intent = new Intent(this,UserHomeActivity.class);
        if (resp instanceof SendAuth.Resp) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
//                    String code = ((SendAuth.Resp) resp).code;
//                    Setting.getInstance().setWechatCode(code);
//                    intent.putExtra(INTENT_KEY, LOGIN_SUCCESS);
//                    startActivity(intent);
//                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    intent.putExtra(INTENT_KEY, LOGIN_CANCEL);
                    startActivity(intent);
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    intent.putExtra(INTENT_KEY, LOGIN_FAILURE);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    finish();
                    break;
            }
        } else {
//            switch (resp.errCode) {
//                case BaseResp.ErrCode.ERR_OK:
//                    Util.showToast("分享成功");
//                    break;
//                case BaseResp.ErrCode.ERR_USER_CANCEL:
//                    Util.showToast("分享取消");
//                    break;
//                case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                    break;
//            }
            finish();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

}
