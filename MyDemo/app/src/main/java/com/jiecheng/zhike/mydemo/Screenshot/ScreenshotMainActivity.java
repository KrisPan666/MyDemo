package com.jiecheng.zhike.mydemo.Screenshot;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.comm.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenshotMainActivity extends BaseActivity implements View.OnClickListener {
    private static String path;
    private static String afterPath;
    private Button btn_start;
    private Button btn_look;
    private TextView show_tv;
    private ScrollView sv;
    private WebView webView;
    private String HTTP = "http://";
    private Bitmap bmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screenshot_main_layout);
        btn_start = (Button)findViewById(R.id.start);
        btn_look = (Button)findViewById(R.id.look);
        webView = (WebView)findViewById(R.id.web);
        sv = (ScrollView)findViewById(R.id.webview);
        show_tv = (TextView)findViewById(R.id.show_bmt);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(HTTP+"www.baidu.com/");
        btn_start.setOnClickListener(this);
        btn_look.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.start){
            bmp = captureScreen(webView);
            if(saveCameraBitmap(bmp,"我的")){
                btn_look.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.GONE);
                toast("截图成功，请点击“查看”来查看截图！");
            }else {
                toast("截屏或保存失败！");
            }
        }else if(view.getId() == R.id.look){
            sv.setVisibility(View.GONE);
            show_tv.setVisibility(View.VISIBLE);
            afterPath = ImageCompressUtil.compressSave(path);//压缩图片
            toast(afterPath);
            show_tv.setText("压缩后的图片存储路径："+afterPath);

        }
    }


    /**
     * 截取webView可视区域的截图
     * webView.setDrawingCacheEnabled(true)
     * @return
     */
    private Bitmap captureWebViewVisibleSize(View view){
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        bmp = bmp.createBitmap(bmp);
        view.destroyDrawingCache();
        return bmp;
    }
    /**
     * lxz
     */
    public static Bitmap getBitmapByView(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(),
                view.getMeasuredHeight());

        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return bmp;
    }

    /**
     * 快照
     *
     * @param context
     * @return
     */
    private Bitmap captureScreen(WebView view) {
        Picture snapShot = view.capturePicture();
        Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(),
                snapShot.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bmp);
        snapShot.draw(canvas);
        return bmp;
    }

    /**
     * 保存截屏
     * @param bm
     * @return
     */
    public static boolean saveCameraBitmap(Bitmap bm, String bmt_name) {
        File sd= Environment.getExternalStorageDirectory();
        String rootpath=sd.getPath();
        path = rootpath +"/"+ bmt_name + ".JPEG";
        boolean result = false;
        Log.e("", "保存图片");
        try {
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 75, out);
            out.flush();
            out.close();
            result = true;
            Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
