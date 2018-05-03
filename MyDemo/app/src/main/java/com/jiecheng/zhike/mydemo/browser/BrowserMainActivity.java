package com.jiecheng.zhike.mydemo.browser;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.comm.BaseActivity;

public class BrowserMainActivity extends BaseActivity implements View.OnClickListener {
    public static final int UPDATE_TEXT = 1;
    private static String HTTP = "http://";
    private static String HTTPS = "https://";
    private Button btn;
    private EditText et;
    private WebView webView;
    private String request;

//    private Handler hd = new Handler(){
//        public void handleMessage(Message msg){
//            switch (msg.what){
//                case UPDATE_TEXT:
//                    //z在这里进行UI操作；
////                    et.setText("我要改变");
//                    request = HTTP+et.getText().toString();
//                    toast("request:"+request);
//                    webView.loadUrl(request);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_activity_main);
        et = (EditText)findViewById(R.id.url_text);
        btn = (Button)findViewById(R.id.find);
//        request = HTTP+et.getText().toString();
        webView = (WebView)findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(HTTP+"www.baidu.com");
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        //在主线程中运行
        String uri = et.getText().toString();
        request = HTTP+uri;
        if(view.getId()==R.id.find){
            Log.d("按钮触发:",request);
            if(uri.contains(HTTP)||uri.contains(HTTPS)){
                webView.loadUrl(uri);
            }else {
                webView.loadUrl(request);
            }
        }

        //在子线程中运行；
//        toast("点击了搜索");
//        switch (view.getId()){
//            case R.id.find:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Message message = new Message();
//                        message.what=UPDATE_TEXT;
//                        hd.sendMessage(message);
//                    }
//                }).start();
//                break;
//            default:
//                break;
//        }
    }
}
