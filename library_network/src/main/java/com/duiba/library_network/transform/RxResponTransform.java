package com.duiba.library_network.transform;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.duiba.library_network.BuildConfig;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jin on 2016/3/9.
 */
public class RxResponTransform implements ObservableTransformer<String, JSONObject> {
    private ProgressDialog mProgressDialog;

    public RxResponTransform(ProgressDialog myProgressDialog) {
        this.mProgressDialog = myProgressDialog;
    }


    @Override
    public ObservableSource<JSONObject> apply(Observable<String> oldObservable) {
        return oldObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> {
                    try {
                        return Observable.just(new JSONObject(result));
                    } catch (JSONException e) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("code", -200);
                        jsonObject.put("msg", "网络错误");
                        jsonObject.put("data", "请检查网络配置");
                        return Observable.just(jsonObject);
                    }
                })
                .filter(responsObject -> responsObject != null)
                .doOnNext(responsObject -> {
                    if (BuildConfig.DEBUG) {
                        //日志输出
                        Logger.i("===================================response json===================================");
                        Logger.json(responsObject.toString());
                        Logger.i("===================================response String===================================");
                        Logger.i(responsObject.toString());
                    }

                    //隐藏进度条
                    if (mProgressDialog != null && mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                })
                .doOnSubscribe(value -> {
                    if (mProgressDialog != null)
                        mProgressDialog.show();
                })
                .doOnError(e -> {
                    if (mProgressDialog != null && mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                    if (mProgressDialog != null && e instanceof UnknownHostException)
                        Toast.makeText(mProgressDialog.getContext(), "请检查网络是否连接", Toast.LENGTH_SHORT).show();
                    Logger.e(e.getMessage());
                })
                .doOnComplete(() -> {
                    if (mProgressDialog != null && mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                }).doFinally(() -> {
                    if (BuildConfig.DEBUG) {
                        //Logger.v("============doFinally============");
                    }
                });
    }
}
