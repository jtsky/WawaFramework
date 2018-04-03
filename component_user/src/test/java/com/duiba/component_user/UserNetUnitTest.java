package com.duiba.component_user;



import com.duiba.component_user.home.view.UserHomeActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
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
    public void presenterTest() {
        UserHomeActivity userHomeActivity = Robolectric.setupActivity(UserHomeActivity.class);
        userHomeActivity.getPresenter().login();

    }
}
