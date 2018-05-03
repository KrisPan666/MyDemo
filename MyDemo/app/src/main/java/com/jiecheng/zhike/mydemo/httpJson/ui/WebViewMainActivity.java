package com.jiecheng.zhike.mydemo.httpJson.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.comm.BaseActivity;

public class WebViewMainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.httpjson_webview_activity_main);
        WebView webView = (WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://mdm.changyan.com/ireport/record/list?teacherid=1010000001000161460&page=1&limit=10&classid=");
    }

    @Override
    public void onClick(View view) {

    }
}
