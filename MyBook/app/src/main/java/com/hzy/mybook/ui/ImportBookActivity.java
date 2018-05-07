package com.hzy.mybook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import com.hzy.mybook.R;
import com.hzy.mybook.controllor.ActivityCollector;
import com.hzy.mybook.manager.BookManager;
import com.hzy.mybook.model.Book;
import com.hzy.mybook.ui.base.BaseActivity;
import com.hzy.mybook.utils.DateUtils;
import com.hzy.mybook.utils.FileUtils;
import com.hzy.mybook.utils.ToastUtils;
import com.hzy.mybook.view.XListView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ImportBookActivity extends BaseActivity implements XListView.IXListViewListener {
	private BookManager bookManager;
	private File rootDir;// 初始文件路径
	private EditText filePath;
	private XListView mListView;
	private Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.import_book);
		//初始化页面
		initView();
		ActivityCollector.addActivity(this);

	}
	private void initView() {
		 mHandler = new Handler();
		bookManager = new BookManager(this);
		mListView = (XListView) findViewById(R.id.listView);
		mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(getTime());
		filePath = (EditText) findViewById(R.id.filePath);
		// SDCard作为根目录
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			rootDir = Environment.getExternalStorageDirectory();
		} else {
			rootDir = Environment.getRootDirectory();
		}// mnt/sdcard
		loadData(rootDir);// 加载数据
		// 文件浏览器列表的点击事件
		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				HashMap map = (HashMap) parent.getItemAtPosition(position);
				File selectFile = (File) map.get("file");
				if (selectFile.isDirectory()) {// 是文件夹
					loadData(selectFile);
				} else if (selectFile.exists()) {// 文件存在
					if (selectFile.length() < 1) {
						ToastUtils.toast(ImportBookActivity.this, "此文件是无效文件");
						return;
					}
					Book book = new Book(selectFile.getName(), selectFile.getAbsolutePath(), new Date(), 0, "0.00%");
					bookManager.save(book);
					Intent intent = new Intent();
					ImportBookActivity.this.setResult(RESULT_OK, intent);
					ImportBookActivity.this.finish();
				} else {// 文件不存在
					ToastUtils.toast(ImportBookActivity.this, "文件不存在");
				}
			}
		});
	}
	// 加载数据
	private void loadData(File file) {
		File[] files = FileUtils.read(file);
		Log.d(TAG, "files.length:" + files.length);
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		// 加入上级目录
		if (!rootDir.getAbsolutePath().equals(file.getAbsolutePath())) {// 子目录
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("image", R.drawable.file);
			map.put("file", file.getParentFile());
			map.put("name", "返回上一级...");
			data.add(map);
		}
		for (int i = 0; i < files.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			String fileName = files[i].getName();
			 Log.d(TAG, "file info:" + files[i].getAbsolutePath());
			if (files[i].isDirectory()) {// 文件
				map.put("image", R.drawable.file);
			} else if (files[i].isFile()) {
				if (fileName.endsWith(".txt")) {
					map.put("image", R.drawable.txt);
				} else if (fileName.endsWith(".zip")) {
					map.put("image", R.drawable.zip);
				} else if (fileName.endsWith(".xml")) {
					map.put("image", R.drawable.xml);
				}
			}
			map.put("file", files[i]);
			map.put("name", fileName);
			if (files[i].listFiles() != null) {// 文件夹非空
				map.put("size", "(" + files[i].listFiles().length + ")");
			} else if (files[i].isFile()) {// 是文件就不显示
			} else {// 文件夹为空
				map.put("size", "(0)");
			}
			map.put("time", DateUtils.format(files[i].lastModified()));
			data.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.import_book_item, new String[] { "image", "name", "size", "time" }, new int[] { R.id.image, R.id.name,
				R.id.size, R.id.time });
		mListView.setAdapter(adapter);
		filePath.setText("路径：" + file.getAbsolutePath());
		Log.d(TAG, "data.size():" + data.size());
	}
	/**
	 * 获取当前时间
	 * @return
	 */
	private String getTime() {
		return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
	}
	
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			public void run() {
				 initView();
				 onLoad();
			}
		}, 2500);
		
	}

	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			public void run() {
				 initView();
				 onLoad();
			}
		}, 2500);
		
	}
	
	private void onLoad() {
		 mListView.stopRefresh();
	     mListView.stopLoadMore();
	     mListView.setRefreshTime(getTime());
	}
}
