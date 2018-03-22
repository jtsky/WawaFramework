package com.duiba.library_common.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;


import java.io.File;



import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * Created by wlw-97 on 2016/12/21.
 */

public class DownloadManage {
//    private static volatile DownloadManage instance;
//
//    Map<String, Subscription> mSubscriptions;
//    RxDownload rxDownload;
//
//    private DownloadManage() {
//        mSubscriptions = Maps.newHashMap();
//        rxDownload = RxDownload.getInstance()
//                .maxThread(10)
//                .maxRetryCount(2);
//    }
//
//    public static DownloadManage getInstance() {
//        if (instance == null) {
//            synchronized (DownloadManage.class) {
//                if (instance == null) {
//                    instance = new DownloadManage();
//                }
//            }
//        }
//
//        return instance;
//    }
//
//
//    public synchronized void download(@NonNull String url, @NonNull String fileName, @Nullable String folderPath, LoadCallBack loadCallBack) {
//        if (TextUtils.isEmpty(url))
//            throw new NullPointerException("url 不能为空");
//        if (TextUtils.isEmpty(fileName))
//            throw new NullPointerException("fileName 不能为空");
//
//
//        if (mSubscriptions.get(url) == null) {
//            Subscription subscription = rxDownload.download(url, fileName, folderPath)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<DownloadStatus>() {
//                        @Override //下载完成
//                        public void onCompleted() {
//                            mSubscriptions.remove(url);
//                            if (TextUtils.isEmpty(folderPath)) {
//                                loadCallBack.onCompleted(getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath() + File.separator + fileName);
//                            } else {
//                                String filePath = folderPath + File.separator + fileName;
//                                if (filePath.contains("//"))
//                                    filePath = filePath.replaceAll("//", "/");
//                                loadCallBack.onCompleted(filePath);
//                            }
//
//                        }
//
//                        @Override //下载出错
//                        public void onError(Throwable e) {
//                            if (mSubscriptions.get(url) != null && !mSubscriptions.get(url).isUnsubscribed())
//                                mSubscriptions.get(url).unsubscribe();
//                            mSubscriptions.remove(url);
//                            loadCallBack.onError(e);
//                        }
//
//                        @Override //下载状态
//                        public void onNext(final DownloadStatus status) {
//                            loadCallBack.onProgress(status.getPercent(), status.getDownloadSize(), status.getTotalSize());
//                        }
//                    });
//            mSubscriptions.put(url, subscription);
//        } else {
//            Toast.makeText(ApplicationUtil.instance, "当前任务正在下载中==>" + fileName, Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    public synchronized void cancel(String url) {
//        if (mSubscriptions.get(url) != null && !mSubscriptions.get(url).isUnsubscribed()) {
//            mSubscriptions.get(url).unsubscribe();
//            mSubscriptions.remove(url);
//        }
//    }


}
