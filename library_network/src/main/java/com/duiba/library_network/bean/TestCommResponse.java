package com.duiba.library_network.bean;

import com.google.gson.annotations.SerializedName;



/**
 * @author Jint
 */
public class TestCommResponse<T> {
    @SerializedName("results")
    T data;
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;

    public boolean isError() {
        return error;
    }

    private boolean error;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }



}
