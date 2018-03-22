package com.duiba.library_common.interfaces;

/**
 * Created by wlw-97 on 2016/12/21.
 */

public interface LoadCallBack {
    //下载完成
    void onCompleted(String filePath);

    //下载出错
    void onError(Throwable e);

    //下载进度
    void onProgress(String Percent, long downloadSize, long totalSize);
}
