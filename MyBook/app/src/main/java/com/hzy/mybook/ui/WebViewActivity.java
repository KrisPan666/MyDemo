package com.hzy.mybook.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.hzy.mybook.R;
import com.hzy.mybook.controllor.ActivityCollector;
import com.hzy.mybook.utils.CommUtils;
import com.hzy.mybook.view.WebViewWrap;
import com.hzy.mybook.widget.BaseJsIfInterface;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener{
    private WebViewWrap webWrap;
    private ViewGroup webviewcontainer;
    private AVLoadingIndicatorView loadingView;
    private View loadFail;
    private WebView mWebView;
    private static String HTTP = "http://";

    //增加下部菜单栏：书架、推荐商城、我的
    private TextView mshelf_tv ;
    private TextView mshop_tv ;
    private TextView myself_tv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluate);
        initUI();
        ActivityCollector.addActivity(this);
        mWebView = new WebView(getApplication());
        webviewcontainer = (ViewGroup) findViewById(R.id.webview_container);
//		mWebView = new WebView(getApplication());
        mWebView.getSettings().setJavaScriptEnabled(true);//
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());//
        webviewcontainer.addView(mWebView);
        loadingView = (AVLoadingIndicatorView) findViewById(R.id.fl_progress);
        loadFail = findViewById(R.id.loadfail);
        webWrap = new WebViewWrap(mWebView, this, new MyJsIf(this), "JsIf", loadingView, loadFail);
        webWrap.loadUrl(HTTP+"www.qisuu.com/");
    }

    private void initUI() {
        mshelf_tv = (TextView)findViewById(R.id.myshelf);
        mshop_tv = (TextView)findViewById(R.id.bookshop);
        myself_tv = (TextView)findViewById(R.id.myself);
        mshelf_tv.setOnClickListener(this);
        mshop_tv.setOnClickListener(this);
        myself_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myshelf:
                showShelf();
                break;
            case R.id.bookshop:
//                gotoBookShop();
                break;
            case R.id.myself:
                gotoMyCenter();
                break;
            default:
                break;
        }
    }

    //展示书架；
    private void showShelf() {
        Intent intent = new Intent(this,MyBookActivity.class);
        startActivity(intent);
    }

    //进入个人中心；
    private void gotoMyCenter() {
        Intent intent = new Intent(this,MyCenterActivity.class);
        startActivity(intent);
    }

    /**
     * js接口
     * */
    public static class MyJsIf extends BaseJsIfInterface {
        private static final String TAG = "MyJsIf";
        public static final String TOAST = "toast";
        private WebViewActivity mAluate;

        public MyJsIf(WebViewActivity activity) {
            super(activity);
            mAluate = activity;
        }

        @Override
        public void sendAndroidFunctionUi(String funcname, String p1
                , String p2, String p3, String p4, String p5) {
            if(TOAST.equals(funcname)){
                toast(p1);
            }
            else if ("openPreviewImge".equals(funcname)) {
                int index = CommUtils.toInteger(p2, 0);
                List<String> imgUri = CommUtils.JsonArrayToListArray(p1);
                if (mAluate != null) {
//                    mAluate.showPreviewImges(imgUri, index);
                }
            }
            else if ("loginfo".equals(funcname)) {
                Log.d("webview", p1 == null ? "null" : p1);
            }
        }

        @JavascriptInterface
        public void close() {
            if (mAluate != null) {
                mAluate.closeReportView();
            }
        }
    }
    /**
     * 关闭报告界面
     */
    public void closeReportView() {
        finish();
    }

    private class DownloadTask extends AsyncTask<String, Void, Void> {
        // 传递两个参数：URL 和 目标路径
        private String url;
        private String destPath;
        @Override
        protected void onPreExecute() {
            Log.d("开始下载","");
        }
        @Override
        protected Void doInBackground(String... params) {
//			log.debug("doInBackground. url:{}, dest:{}", params[0], params[1]);
            url = params[0];
            destPath = params[1];
            OutputStream out = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = null;
                try {
                    url = new URL(params[0]);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                urlConnection.setConnectTimeout(15000);
                urlConnection.setReadTimeout(15000);
                InputStream in = urlConnection.getInputStream();
                out = new FileOutputStream(params[1]);
                byte[] buffer = new byte[10 * 1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                in.close();
            } catch (IOException e) {
                Log.d("Io异常：",e+"");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.d("Io异常：",e+"");
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(),"下载完成!",Toast.LENGTH_SHORT).show();
            Intent handlerIntent = new Intent(Intent.ACTION_VIEW);
            String mimeType = getMIMEType(url);
            Uri uri = Uri.fromFile(new File(destPath));
//			log.debug("mimiType:{}, uri:{}", mimeType, uri);
            handlerIntent.setDataAndType(uri, mimeType);
            startActivity(handlerIntent);
        }
    }

    private String getMIMEType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        Log.d("extension:{}", extension);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

//	//  使用
//        mWebView.setDownloadListener(new DownloadListener() {
//		@Override
//		public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
//			String fileName = URLUtil.guessFileName(url, contentDisposition, mimeType);
//			String destPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//					.getAbsolutePath() + File.separator + fileName;
//			new DownloadTask().execute(url, destPath);
//		}
//	});

    //webview下载监听；
    private class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//			String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
//			String fileName = null;
            try {
                String fileName = URLDecoder.decode(URLUtil.guessFileName(url, contentDisposition, mimetype), "UTF-8");
                String destPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + fileName;
//			String destPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + fileName;
                Toast.makeText(getApplicationContext(),"任务正在后台下载，下载完成会提示！",Toast.LENGTH_SHORT).show();
                new DownloadTask().execute(url, destPath);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }else if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dialog();
            return false;
        }
        return false;
    }

    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("温馨提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ActivityCollector.finishAll();
                // 返回home
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
