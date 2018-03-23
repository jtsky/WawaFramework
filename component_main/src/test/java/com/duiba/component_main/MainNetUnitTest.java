package com.duiba.component_main;


import com.duiba.component_main.bean.TestBean;
import com.duiba.component_main.net.MainRESTApiImpl;
import com.duiba.library_network.action.WebSuccessAction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;


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
    public void getUserTest() {
        //网络调用
        MainRESTApiImpl
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
                });
    }
}
