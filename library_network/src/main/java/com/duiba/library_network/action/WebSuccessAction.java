package com.duiba.library_network.action;

import com.duiba.library_network.bean.TestCommResponse;
import com.duiba.library_network.bean.NetCode;

import io.reactivex.functions.Consumer;
/**
 * Created by wlw-97 on 2016/11/3.
 */
public abstract class WebSuccessAction<T> implements Consumer<TestCommResponse<T>> {


    @Override
    public void accept(TestCommResponse<T> response) throws Exception {
        if (response.getCode() != NetCode.OK) {
            onFailed(response.getCode(), response.getMsg());
            return;
        }

        onSuccess(response.getData());
    }

    /**
     * 状态等于200
     */
    public abstract void onSuccess(T model);

    /**
     * 状态不等于200
     */
    public abstract void onFailed(int code, String msg);
}
