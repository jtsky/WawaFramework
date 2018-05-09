package com.duiba.library_network.util;

import android.text.TextUtils;
import android.util.Log;

import com.facebook.stetho.common.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/5/9-11:05
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MockDataGenerator {
    private static final String TAG = "MockDataGenerator";

    public static String getMockDataFromJsonFile(String assetPath) {
        if (TextUtils.isEmpty(assetPath)) {
            return null;
        }
        final String data;
        try {
            data = convertInputStreamToString(
                    ApplicationUtil.instance.getAssets().open(assetPath));
            return data;
        } catch (IOException e) {
            LogUtil.e(TAG, "getMockDataFromJsonFile: ", e);
        }
        return null;
    }

    static String convertInputStreamToString(InputStream inputStream) {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = r.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } catch (IOException e) {
            LogUtil.e("error converting input stream to string", e);
        }
        return null;
    }
}
