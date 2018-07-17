package com.duiba.library_network.action;

import com.duiba.library_network.bean.TestCommResponse;
import io.reactivex.functions.Consumer;
/**
 * gank 接口专用
 */
public abstract class WebSuccessAction1<T> implements Consumer<TestCommResponse<T>> {

    @Override
    public void accept(TestCommResponse<T> response) throws Exception {
        if (response.isError()) {
            onFailed(response.getCode(), response.getMsg());
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
