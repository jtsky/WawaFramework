package wsmanager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author: jintai
 * @time: 2018/3/22-10:16
 * @Email: jintai@qccr.com
 * @desc:
 */
public class WsManagerFactory {
    private volatile static WsManager instance;
    private static final String TAG = "WsManagerFactory";

    public static WsManager createWsManager(Context context, String url) {
        if (instance == null) {
            synchronized (WsManager.class) {
                if (instance == null) {
                    instance = new WsManager.Builder(context)
                            .client(
                                    new OkHttpClient().newBuilder()
                                            .pingInterval(15, TimeUnit.SECONDS)
                                            .retryOnConnectionFailure(true)
                                            .build())
                            .needReconnect(true)
                            .wsUrl(url)
                            .build();
                }
            }
        }
        return instance;
    }

    @Nullable
    public static WsManager getWsManager() {
        if (instance == null) {
            Log.v(TAG, "请确认WsManager已初始化");
        }
        return instance;
    }

}
