package com.duiba.library_network.bean;


/**
 * @author Jint
 */
public class ServerCallbackModel<T> {
    private T data;
    private boolean success;
    private String message;
    private int code;

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
