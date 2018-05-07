package com.hzy.mybook.widget;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.hzy.mybook.utils.StringUtils;
import com.hzy.mybook.utils.ToastUtils;


public class BaseJsIfInterface {

	private static final String TAG = "BaseJsIfInterface";
	protected Activity mActivity;
	
	public BaseJsIfInterface(Activity context) {
		mActivity = context;
	}
	
	@JavascriptInterface
	public void toast(String info) {
		if (!StringUtils.isEmpty(info)) {
			ToastUtils.showCenterShort(mActivity.getApplication(), info);
		}
	}

	@JavascriptInterface
	public void loadUrl(String url) {
		
	}

	/**
	 * 点击某个标签，query选择器字符串
	 * */
	@JavascriptInterface
	public void clickHTML(String url, String query) {
		
	}

	// 获取当前web接口的根路径包含 /
	@JavascriptInterface
	public String getRootUrl() {
		return "";
	}


	@JavascriptInterface
	public void syncScale(String url, String scale, String x, String y,
                          String isZoom) {
	}

	@JavascriptInterface
	public void sendFunction(String funcname, String p1, String p2,
                             String p3, String p4, String p5) {
	}

	/**
	 * 从js中执行某一个android方法
	 * */
	@JavascriptInterface
	public void sendAndroidFunction(final String funcname, final String p1,
                                    final String p2, final String p3, final String p4, final String p5) {
		Log.d(TAG, String.format("sendandroidfun:funcname:%s p1:%s, p2:%s, p3:%s, p4:%s, p5:%s"
										, funcname, p1, p2, p3, p4, p5));
		// run main thread
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sendAndroidFunctionUi(funcname, p1, p2, p3, p4, p5);
			}
		});
	}

	// 获取当前web接口的根路径包含 /
	@JavascriptInterface
	public String getDevModel() {
		return Build.MODEL;
	}

	public void sendAndroidFunctionUi(final String funcname, final String p1,
                                      final String p2, final String p3, final String p4, final String p5) {

	}
}
