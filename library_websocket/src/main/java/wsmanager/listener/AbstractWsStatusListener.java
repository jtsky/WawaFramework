package wsmanager.listener;

import android.util.Log;

import okhttp3.Response;
import okio.ByteString;

/**
 * @author jintai
 * @desc 可用于监听ws连接状态并进一步拓展
 */
public abstract class AbstractWsStatusListener {
    private static final String TAG = "AbstractWsStatusListener";
    public boolean isNetConnect = false;


    public void onOpen(Response response) {
        isNetConnect = true;
        Log.v(TAG,"========onOpen=======");
    }


    public void onMessage(String text) {
        isNetConnect = true;
        Log.v(TAG,"========onMessage===text====");
    }


    public void onMessage(ByteString bytes) {
        isNetConnect = true;
        Log.v(TAG,"========onMessage===bytes====");
    }


    public void onReconnect() {
        isNetConnect = true;
        Log.v(TAG,"========onReconnect=======");
    }


    public void onClosing(int code, String reason) {
        isNetConnect = true;
        Log.v(TAG,"========onClosing=======");
    }


    public void onClosed(int code, String reason) {
        isNetConnect = false;
        Log.v(TAG,"========onClosed=======");
    }


    public void onFailure(Throwable t, Response response) {
        isNetConnect = false;
        Log.v(TAG,"========onFailure=======");
    }


    public void onNetClosed() {
        isNetConnect = false;
        Log.v(TAG,"========onNetClosed=======");
    }


    public void onNetOpen() {
        isNetConnect = true;
        Log.v(TAG,"========onNetOpen=======");
    }
}
