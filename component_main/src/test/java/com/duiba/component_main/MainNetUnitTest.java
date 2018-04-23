package com.duiba.component_main;


import android.app.Activity;
import android.widget.Button;

import com.duiba.component_main.activity.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


/**
 * @author: jintai
 * @time: 2018/3/22-14:00
 * @Email: jintai@duiba.com.cn
 * @desc:主模块的网络单元测试
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class MainNetUnitTest  extends BaseNetUnitTest {

    @Test
    public void ActivityTest() {
        Activity activity = Robolectric.setupActivity(MainActivity.class);

        Button button = activity.findViewById(R.id.btn_arouter);

        button.performClick();
        //网络调用
       /* MainRESTApiImpl
                .getData("Android", null)
                .subscribe(new WebSuccessAction<List<TestBean>>() {
                    @Override
                    public void onSuccess(List<TestBean> testBeans) {
                        System.out.println(testBeans);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        System.out.println(msg);
                    }
                });*/
    }
}
