package com.hzy.mybook.ui;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.hzy.mybook.R;
import com.hzy.mybook.controllor.ActivityCollector;
import com.hzy.mybook.db.UserDataBaseHelper;
import com.hzy.mybook.manager.BookManager;
import com.hzy.mybook.manager.BookidManager;
import com.hzy.mybook.model.Book;
import com.hzy.mybook.model.BookId;
import com.hzy.mybook.ui.base.BaseActivity;
import com.hzy.mybook.utils.CommUtils;
import com.hzy.mybook.utils.ImageUtil;
import com.hzy.mybook.utils.ToastUtils;
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
import java.util.ArrayList;
import java.util.List;


public class MyBookActivity extends BaseActivity implements View.OnClickListener{
	private BookManager bookManager;
	private BookidManager bookidManager;
	private ArrayList<Book> books;
	private ArrayList<BookId> bookids;
	private GridView bookShelf;
	private ProgressDialog dialog;
	// 显示图书首页的索引
	private int selectedIndex = -1;
	public static int width, height;
//	public static Bitmap itemBackground;
	//create by phy
	public static List<Bitmap> list ;
	//增加下部菜单栏：书架、推荐商城、我的
	private TextView mshelf_tv ;
	private TextView mshop_tv ;
	private TextView myself_tv ;
	//增加Webview
	private WebView mWebView ;
	private static String HTTP = "http://";
	//书架、商城、我的的状态;0表示不在当前页面，1表示在当前页面
    private int shelfState = 0 ;
	private int shopState = 0 ;
	private int myselfState = 0 ;
	public  int  getShelfState() {
		return shelfState;
	}
	public void setShelfState(int shelfState) {
		this.shelfState = shelfState;
	}
	public int  getShopState() {
		return shopState;
	}
	public void setShopState(int shopState) {
		this.shopState = shopState;
	}
	public int  getMyselfState() {
		return myselfState;
	}
	public void setMyselfState(int myselfState) {
		this.myselfState = myselfState;
	}

	private WebViewWrap webWrap;
	private ViewGroup webviewcontainer;
	private AVLoadingIndicatorView loadingView;
	private View loadFail;

	private UserDataBaseHelper uDBH;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//显示标题栏
		setContentView(R.layout.my_book);
		initUI();
		ActivityCollector.addActivity(this);
		mshelf_tv.setOnClickListener(this);
		mshop_tv.setOnClickListener(this);
		myself_tv.setOnClickListener(this);
		uDBH = new UserDataBaseHelper(this);

		Display defaultDisplay = getWindowManager().getDefaultDisplay();
		width = defaultDisplay.getWidth();
		height = defaultDisplay.getHeight();


		list = new ArrayList<Bitmap>();
		int[] fengpi = {R.drawable.bookfengpi5,R.drawable.bookfengpi2,R.drawable.bookfengpi3,R.drawable.bookfengpi4,R.drawable.bookfengpi1,};
		for(int i=0;i<fengpi.length;i++){
			Bitmap itemBackground = ImageUtil.getBitmap(this, fengpi[i], width / 4, height / 4);
			list.add(itemBackground);
		}
//		itemBackground = ImageUtil.getBitmap(this, R.drawable.bookfengpi2, width / 4, height / 4);

		System.out.println("MyBookActivity width:" + MyBookActivity.width);
		System.out.println("MyBookActivity height:" + MyBookActivity.height);

		bookManager = new BookManager(this);
		bookidManager = new BookidManager(this);
		// 加载图书列表
		loadData();
		// 点击
		bookShelf.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectedIndex = position;
				readBook();
			}
		});
		// 长按点击
		bookShelf.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				selectedIndex = position;
				return false;
			}
		});
		// 注册上下文菜单
		registerForContextMenu(bookShelf);
	}

	private void initUI() {
		bookShelf = (GridView) findViewById(R.id.bookShelf);
		mshelf_tv = (TextView)findViewById(R.id.myshelf);
		mshop_tv = (TextView)findViewById(R.id.bookshop);
		myself_tv = (TextView)findViewById(R.id.myself);
//		mWebView = (WebView)findViewById(R.id.webview);
//		mWebView = new WebView(getApplication());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.myshelf:
				showShelf();
				break;
			case R.id.bookshop:
				gotoBookShop();
				break;
			case R.id.myself:
				gotoMyCenter();
				break;
			default:
				break;
		}
	}

	//进入个人中心；
	private void gotoMyCenter() {
		setShelfState(0);
		setShopState(0);
		setMyselfState(1);
		Intent intent = new Intent(this,MyCenterActivity.class);
		startActivity(intent);
	}

	//进入推荐的小说网站
	public void gotoBookShop() {
		setShelfState(0);
		setShopState(1);
		setMyselfState(0);
		bookShelf.setVisibility(View.GONE);
		Intent intent = new Intent(this,WebViewActivity.class);
		startActivity(intent);
	}

	/**
	 * js接口
	 * */
	public static class MyJsIf extends BaseJsIfInterface {
		private static final String TAG = "MyJsIf";
		public static final String TOAST = "toast";
		private MyBookActivity mAluate;

		public MyJsIf(MyBookActivity activity) {
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


	//webview下载监听；
	private class MyWebViewDownLoadListener implements DownloadListener {
		@Override
		public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
			try {
				String fileName = URLDecoder.decode(URLUtil.guessFileName(url, contentDisposition, mimetype), "UTF-8");
				String destPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + fileName;
				Toast.makeText(getApplicationContext(),"任务正在后台下载，下载完成会提示！",Toast.LENGTH_SHORT).show();
				new DownloadTask().execute(url, destPath);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
	}


	//展示书架；
	private void showShelf() {
		setShelfState(1);
		setShopState(0);
		setMyselfState(0);
		bookShelf.setVisibility(View.VISIBLE);
	}
	// 阅读
	private void readBook() {
		dialog = ProgressDialog.show(MyBookActivity.this, "温馨提示", "正在加载文件", true);
		Book book = books.get(selectedIndex);
		File file = new File(book.getFilePath());
		if (!file.exists()) {// 文件不存在
			ToastUtils.toast(this, "文件不存在");
			dialog.dismiss();
			return;
		}
		Long id = book.getId();
		Intent intent = new Intent(this, ReadBookActivity.class);
		intent.putExtra("bookId", id);
		dialog.dismiss();
		startActivity(intent);
	}



	// 创建选项菜单：点击手机上的MENU
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.my_book, menu);// 解析定义在menu目录下的菜单布局文件
		return super.onCreateOptionsMenu(menu);
	}

	// 处理选项菜单的点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_importbook:// menu_importbook 图书导入
			Intent intent = new Intent(this, ImportBookActivity.class);
			startActivityForResult(intent, REQUEST_CODE_IMPORT_BOOK);
			break;
		case R.id.menu_help:
			//跳转到viewpager页面
//			Intent intent1 = new Intent(MyBookActivity.this, ViewPagerActivity.class);
//			startActivity(intent1);
			break;
		case R.id.menu_setting:
			//跳转到设置页面
			Intent intent2 = new Intent(MyBookActivity.this, SettingActivity.class);
			startActivity(intent2);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 直接点击文件浏览器列表返回
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_IMPORT_BOOK) {
			loadData();
			ToastUtils.toast(this, "添加图书成功");
		}
	}


	// 加载数据到ListView
	private void loadData() {
		books = bookManager.getAll();
		Log.d(TAG, "books.size:" + books.size());
		ShlefAdapter adapter = new ShlefAdapter();
		bookShelf.setAdapter(adapter);
	}

	class ShlefAdapter extends BaseAdapter {
		public int getCount() {
			return books.size();
		}
		public Book getItem(int position) {
			return books.get(position);
		}
		public long getItemId(int position) {
			return getItem(position).getId();
		}
		public View getView(int position, View contentView, ViewGroup arg2) {
			Book book = getItem(position);
			contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.my_book_item, null);
			TextView view = (TextView) contentView.findViewById(R.id.bookName);
			view.setText(book.getName());
			if(position%2 == 0){
				BitmapDrawable bitmapDrawable = new BitmapDrawable(list.get(1));
				view.setBackgroundDrawable(bitmapDrawable);
			}
			else if(position%3 == 0) {
				BitmapDrawable bitmapDrawable = new BitmapDrawable(list.get(2));
				view.setBackgroundDrawable(bitmapDrawable);
			}
			else if(position%4 == 0) {
				BitmapDrawable bitmapDrawable = new BitmapDrawable(list.get(3));
				view.setBackgroundDrawable(bitmapDrawable);
			}
			else if(position%5 == 0) {
				BitmapDrawable bitmapDrawable = new BitmapDrawable(list.get(4));
				view.setBackgroundDrawable(bitmapDrawable);
			}
			else if(position%6 == 0) {
				BitmapDrawable bitmapDrawable = new BitmapDrawable(list.get(5));
				view.setBackgroundDrawable(bitmapDrawable);
			}
			else{
				BitmapDrawable bitmapDrawable = new BitmapDrawable(list.get(0));
				view.setBackgroundDrawable(bitmapDrawable);
			}
			return contentView;
		}
	}

	// 创建上下文菜单
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("上下文菜单");
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.menu_book, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	// 处理上下文菜单的点击事件
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.read:
			readBook();
			break;
		case R.id.update:
			// emp.do?id=200
			Book book = books.get(selectedIndex);
			Long id = book.getId( );
			BookId bookId = new BookId(String.valueOf(id));
			if(bookidManager.find(bookId.getBid()) == null){
				bookidManager.insertbookid(bookId);
				ToastUtils.showLong(getApplication(),"《"+book.getName()+"》收藏成功！");
			}else{
				ToastUtils.showLong(getApplication(),"该图书已被收藏！");
			}

			// Intent intent = new Intent(this, BookFormActivity.class);
			// intent.putExtra("bookId", id);
			// startActivityForResult(intent, BOOK_UPDATE);
			break;
		case R.id.delete:
			Long id1 = books.get(selectedIndex).getId();
			bookManager.delete(id1);
			ToastUtils.toast(this, R.string.msg_delete_success);
			loadData();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	/**
	 * 	改变主页面的返回事件
	 如果不做任何处理，浏览网页，点击系统“Back”键，整个Browser会调用finish()而结束自身，
	 如果希望浏览的网页回退而不是推出浏览器，需要在当前Activity中处理并消费掉该Back事件。
	 * @param keyCode
	 * @param event
	 * @return
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if((getShopState() == 1) && (keyCode==KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
//			mWebView.goBack();
			return true;
		}else if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			dialog();
			return false;
		}
		return false;
	}

	private void dialog() {
		Builder builder = new Builder(this);
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