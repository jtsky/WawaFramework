package com.duiba.rxnetwork;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;


/**
 * Observe the network change state.
 * <p/>
 * To use this, you need to add this code to AndroidManifest.xml
 * <p/>
 * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}
 * <p/>
 * <pre>
 */
public class RxNetwork {
    private RxNetwork() {
        // No instances
    }

    /**
     * Helper function that returns the connectivity state
     *
     * @param context Context
     * @return Connectivity State
     */
    public static boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return null != activeNetwork && activeNetwork.isConnected();

    }

    /**
     * Creates an observable that listens to connectivity changes
     */
    public static Observable<Boolean> stream(Context context) {
        final Context applicationContext = context.getApplicationContext();
        final IntentFilter action = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        return ContentObservable.fromBroadcast(context, action)
                // To get initial connectivity status
                //.startWith((Intent) null)
                .filter(new Predicate<Intent>() {
                    @Override
                    public boolean test(Intent intent) throws Exception {
                        return intent != null;
                    }
                })
                .map(new Function<Intent, Boolean>() {
                    @Override
                    public Boolean apply(Intent intent) throws Exception {
                        return getConnectivityStatus(applicationContext);
                    }
                }).distinctUntilChanged();
    }
}
