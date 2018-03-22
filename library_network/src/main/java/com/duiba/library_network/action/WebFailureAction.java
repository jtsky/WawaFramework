package com.duiba.library_network.action;

import android.util.Log;
import android.widget.Toast;

import com.duiba.library_network.util.ApplicationUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;


/**
 * Created by wlw-97 on 2016/11/3.
 */

public class WebFailureAction implements Consumer<Throwable> {
    private static final String TAG = "WebFailureAction";

    @Override
    public void accept(Throwable throwable) throws Exception {
        String errorMsg;
        if (throwable instanceof IOException) {
            errorMsg = "请检查网络状态";
        } else if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            errorMsg = httpException.response().message();
        } else {
            errorMsg = throwable.getMessage();
        }
        Logger.v(errorMsg);
//        if (ApplicationUtil.instance == null) {
//            throw new RuntimeException("请初始化ApplicationUtil.instance");
//        }
//
//        Toast.makeText(ApplicationUtil.instance, errorMsg, Toast.LENGTH_SHORT).show();
    }
}
