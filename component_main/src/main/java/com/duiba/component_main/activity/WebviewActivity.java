package com.duiba.component_main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duiba.component_base.component.main.path.MainRouterPath;
import com.duiba.component_main.R;

@Route(path = MainRouterPath.MAIN_ACTIVITY_WEB)
public class WebviewActivity extends Activity {

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_webview);

        webview = (WebView) findViewById(R.id.webview);
        String url = getIntent().getStringExtra("url");
        webview.loadUrl(url);
    }
}
