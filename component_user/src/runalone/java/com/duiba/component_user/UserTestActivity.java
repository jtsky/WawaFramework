package com.duiba.component_user;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.duiba.component_base.application.BaseApplication;
import com.duiba.component_user.home.view.UserHomeActivity;
import com.jakewharton.rxbinding2.view.RxView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Jin
 */
public class UserTestActivity extends AppCompatActivity {
    @BindView(R2.id.btn)
    Button mBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_test);
        ButterKnife.bind(this);
        RxView.clicks(mBtn).subscribe(aVoid -> {
            Intent intent = new Intent(this, UserHomeActivity.class);
            startActivity(intent);
        });
    }

    public void wxLogin() {
        if (!checkApkExist(getApplication(), "com.tencent.mm")) {
            Toast.makeText(this, "请先安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        weixinLogin();
    }

    public static void weixinLogin() {
        IWXAPI mApi = BaseApplication.getApplication().getWxAPI();
        if (mApi != null && mApi.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test_neng";
            mApi.sendReq(req);
        }
    }

    /**
     * 检查是否安装应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
