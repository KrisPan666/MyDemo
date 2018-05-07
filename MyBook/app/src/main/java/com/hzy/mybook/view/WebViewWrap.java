package com.hzy.mybook.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.hzy.mybook.R;
import com.wang.avi.AVLoadingIndicatorView;

public class WebViewWrap {
	private static final String TAG = "WebViewWrap";
	private WebView mWebView;
	private boolean mIsLoading = true;
	private AVLoadingIndicatorView loadingView;
	private View loadFail;
	private boolean isFinish = false;// 标记界面是否已经销毁
	/**
	 * webview 
	 * @param webview
	 * @param context
	 * @param Jsinterface js接口
	 * @param interfaceName js接口名称
	 */
	public WebViewWrap(WebView webview
			, Activity context, Object Jsinterface, String interfaceName, AVLoadingIndicatorView loadingView, View loadFail) {
		mWebView = webview;
		this.loadFail = loadFail;
		this.loadingView = loadingView;
		initWebViewSetting(Jsinterface, interfaceName);
	}
	
	// 初始化webview配置
	@SuppressWarnings("deprecation")
	private void initWebViewSetting(Object Jsinterface, String interfaceName) {
		String DESKTOP_USERAGENT = "Mozilla/5.0 "
				+ "(iPad; U; CPU OS 3_2 like Mac OS X;en-us) "
				+ "AppleWebKit/531.21.10 (KHTML, like Gecko) "
				+ "Version/4.0.4 Mobile/7B334bSafari/531.21.10 " + "mcvwebview";// 保证可以识别出当前浏览器是移动设备
		WebSettings settings = mWebView.getSettings();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WebView.setWebContentsDebuggingEnabled(true);
		}
		// settings.setPluginsEnabled(true);
		settings.setJavaScriptEnabled(true);
		settings.setPluginState(PluginState.ON_DEMAND);
		settings.setAllowFileAccess(true);
		settings.setAllowContentAccess(true);
		settings.setDatabaseEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		settings.setUserAgentString(DESKTOP_USERAGENT);
		settings.setCacheMode(WebSettings.LOAD_DEFAULT);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			settings.setAllowFileAccessFromFileURLs(true);// 为了能够访问外部sd卡的文件
		}
		settings.setDomStorageEnabled(true);   
		mWebView.setSaveEnabled(false);// http://bLg.csdn.net/fei0724/article/details/18352991
		mWebView.setWebChromeClient(new MyWebChromeClient());
		mWebView.setWebViewClient(new MyWebClient());
		mWebView.addJavascriptInterface(Jsinterface, interfaceName/*"JsIf"*/);
		// 点击刷新重试
		loadFail.findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String loadFailUrl = String.valueOf(loadFail.getTag());
				if (!TextUtils.isEmpty(loadFailUrl)) {
					Log.d(TAG, "click reload : loadFailUrl:" + loadFailUrl);
					showloading();
					mWebView.loadUrl(loadFailUrl);
				}
			}
		});

	}

	private class MyWebChromeClient extends WebChromeClient {
		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			super.onShowCustomView(view, callback);
			if (view instanceof FrameLayout) {
				FrameLayout frame = (FrameLayout) view;
				if (frame.getFocusedChild() instanceof VideoView) {
					VideoView video = (VideoView) frame.getFocusedChild();
					frame.removeView(video);
					video.start();
				}
			}
		}

		@Override
		public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
			log(consoleMessage.message(), consoleMessage.lineNumber(), consoleMessage.sourceId());
			return super.onConsoleMessage(consoleMessage);
		}

		@Override
		@Deprecated
		public void onConsoleMessage(String message, int lineNumber,
                                     String sourceID) {
			super.onConsoleMessage(message, lineNumber, sourceID);
			log(message, lineNumber, sourceID);
		}

		public void log(String message, int lineNumber, String sourceID) {
			Log.d(TAG, String.format("line:%d, sourceId=%s : %s", lineNumber, sourceID, message));
		}

	}

	public class MyWebClient extends WebViewClient {
		private long startload;
		private long endload;
		private boolean isloadfail = false;
		private int tryLoadCount = 0;

		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return false;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			mIsLoading = true;
			isloadfail = false;
			showloading();
			startload = System.currentTimeMillis();
			Log.d(TAG, "load html start " + url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
                                    String description, final String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			Log.d(TAG, "load fail: " + failingUrl);
			Log.d(TAG, "onReceivedError : " + errorCode + "," + description);
			isloadfail = true;
			tryLoadCount++;
			if (tryLoadCount > 4) {
				// 加载失败,显示加载失败页面
				Log.d(TAG, "show load fail : " + description);
				showLoadFail(failingUrl, errorCode);
				tryLoadCount = 0;
			}
			else {
				// 尝试重新加载,尝试时间间隔依次加长：1s, 2s, 3s, 4s
				Log.d(TAG, "load fail , try reload, tryLoadCount: " + tryLoadCount);
				if (!TextUtils.isEmpty(failingUrl)) {
					mWebView.postDelayed(new Runnable() {
						@Override
						public void run() {
							if (!isFinish) {
								showloading();
								mWebView.loadUrl(failingUrl);
							}
							else {
								Log.d(TAG, "activity is finish");
							}
						}
					}, tryLoadCount * 1000);
				}
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			mIsLoading = false;
			if (!isloadfail) {
				showWebview();
			}
			endload  = System.currentTimeMillis();
			Log.d(TAG, "load html finish : total time->" + (endload - startload) + "," + url);
		}

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed();// https证书问题,导致空白页问题
			Log.d(TAG, "onReceivedError : " + error);
		}
	}

	private void showLoadFail(String failingUrl, int errorCode) {
		//loadingView.setVisibility(View.GONE);
		loadingView.hide();
		loadFail.setVisibility(View.VISIBLE);
		loadFail.setTag(failingUrl);
		if (mWebView != null) {
			mWebView.setVisibility(View.GONE);
		}
	}

	private void showWebview() {
		//loadingView.setVisibility(View.GONE);
		loadingView.hide();
		loadFail.setVisibility(View.GONE);
		if (mWebView != null) {
			mWebView.setVisibility(View.VISIBLE);
		}
	}

	private void showloading() {
		//loadingView.setVisibility(View.VISIBLE);
		loadingView.show();
		loadFail.setVisibility(View.GONE);
		if (mWebView != null) {
			mWebView.setVisibility(View.GONE);
		}
	}

	// 加载url
	public void loadUrl(String url) {
		if (mWebView != null) {
			mWebView.loadUrl(url);
		}
	}

	public void goForward() {
		mWebView.goForward();
	}

	public void goBack() {
		mWebView.goBack();
	}

	public View getWebView() {
		return mWebView;
	}

	public boolean isLoading() {
		return mIsLoading;
	}

	public void destroy() {
		if (mWebView != null) {
			mWebView.destroy();
		}
	}

	public void reload() {
		if (mWebView != null) {
			mWebView.reload();
		}
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}
}
