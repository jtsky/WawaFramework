package com.duiba.component_user;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;


/**
 * @author: jintai
 * @time: 2018/3/22-14:00
 * @Email: jintai@duiba.com.cn
 * @desc:用户模块的网络单元测试
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class UserNetUnitTest extends BaseNetUnitTest {

    @Test
    public void getUserTest() {
        assertEquals(4, 2 + 2);
        //网络调用
//        MainRESTApiImpl
//                .getData("Android", null)
//                .subscribe(new WebSuccessAction<List<TestBean>>() {
//                    @Override
//                    public void onSuccess(List<TestBean> testBeans) {
//                        System.out.println(testBeans);
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        System.out.println(msg);
//                    }
//                });
    }
}
