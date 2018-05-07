package com.hzy.mybook.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.hzy.mybook.R;
import com.hzy.mybook.controllor.ActivityCollector;
import com.hzy.mybook.manager.BookManager;
import com.hzy.mybook.manager.TextManager;
import com.hzy.mybook.model.Book;
import com.hzy.mybook.ui.base.BaseActivity;
import com.hzy.mybook.utils.ToastUtils;
import com.hzy.mybook.view.BookPageFactory;
import com.hzy.mybook.view.BookPageView;
import com.hzy.mybook.view.Wheel.WheelView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class ReadBookActivity extends BaseActivity {
	private BookManager bookManager;
	private Book book;
	private BookPageView mBookPageView;
	private Bitmap mCurPageBitmap, mNextPageBitmap;
	private Canvas mCurPageCanvas, mNextPageCanvas;
	private BookPageFactory pagefactory;
	private TextManager textManager;
	private int width ;
	private int height ;
	long fileLenth = 1L;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		// Android获取屏幕宽度和高度：
		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		// 实例化自定义View
		mBookPageView = new BookPageView(this, width, height);
		//panhongyu,为自定义view设置背景颜色；
		mBookPageView.setBackgroundResource(R.drawable.style07);
//		mBookPageView.setBackground(ContextCompat.getColor(context,leftBtnbackground));
		setContentView(mBookPageView);



		// 创建当前的图片
		mCurPageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		// 创建下一页的图片
		mNextPageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		// 转化成画布类
		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);
		// 实例化工具类
		pagefactory = new BookPageFactory(this, width, height);
		try {
			// 实例化Service
			bookManager = new BookManager(this);
			Long bookId = getIntent().getLongExtra("bookId", -1);
			if (bookId == -1) {
				ToastUtils.toast(this, "查询的id不存在");
				startActivity(new Intent(this, MyBookActivity.class));
				return;
			}
			book = bookManager.get(bookId);
			// 读取图书
			fileLenth = pagefactory.openBook(book);
			// 绘制进度百分比
			pagefactory.onDraw(mCurPageCanvas);
		} catch (IOException e1) {
			ToastUtils.toast(this, "查询的电子书不存在");
			Log.e(TAG, e1.getMessage());
		}
		// 设置自定义View的进度条上一页，下一页的图片
		mBookPageView.setBitmaps(mCurPageBitmap, mCurPageBitmap);

		// 设置自定义View的触屏事件
		mBookPageView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent e) {
				boolean ret = false;
				if (v == mBookPageView) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {// 屏幕按下
						// 设置动画效果
						mBookPageView.abortAnimation();
						// 修改点击的坐标，从而判断是上一页还是下一页
						mBookPageView.calcCornerXY(e.getX(), e.getY());

						pagefactory.onDraw(mCurPageCanvas);
						if (mBookPageView.DragToRight()) {
							pagefactory.prePage();// 上一页
							// 如果是首页了，就不用更改进度框的信息
							if (pagefactory.isfirstPage()) {
								ToastUtils.toast(ReadBookActivity.this, "首页");
								return false;
							}
							pagefactory.onDraw(mNextPageCanvas);
						} else {
							pagefactory.nextPage();// 下一页
							// 如果是末页了，就不用更改进度框的信息
							if (pagefactory.islastPage()) {
								ToastUtils.toast(ReadBookActivity.this, "末页");
								return false;
							}
							pagefactory.onDraw(mNextPageCanvas);
						}
						// 把上一页和下一页的图片给自定义View
						mBookPageView.setBitmaps(mCurPageBitmap, mNextPageBitmap);
					}
					// 在activity和view中都有onTouchEvent方法
					// 如果在view中覆盖此方法并return
					// false则一次touch事件此方法只会被触发一次且触发完后会接着把这次的触发事件交给activity中的onTouchEvent方法，
					// 如果return true则所有的touch事件都会触发这个View的 onTouchEvent方法
					ret = mBookPageView.doTouchEvent(e);
					return ret;
				}
				return false;
			}

		});
	}

	// 创建选项菜单：点击手机上的MENU
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.read_book, menu);// 解析定义在menu目录下的菜单布局文件
		return super.onCreateOptionsMenu(menu);
	}

	// 处理选项菜单的点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu1:// 白天模式
			break;
		case R.id.menu2:// 夜晚模式
			break;
		case R.id.menu3:// 图片模式
			// 第一个参数是包含你要加载的位图资源文件的对象
			// 第二个时你需要加载的位图资源的Id
			// 第三个参数应该是对你要加载的位图是否需要完整显示
			pagefactory.setBgBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.bg2));
			
			// 绘制进度百分比
			// pagefactory.onDraw(mCurPageCanvas);
			// // 设置自定义View的进度条上一页，下一页的图片
			// mBookPageView.setBitmaps(mCurPageBitmap, mCurPageBitmap);
			break;
		case R.id.menu4:// 粉红模式
			pagefactory.setM_backColor(0xffff9e85);
			break;
		case R.id.menu5:// 进度控制
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			LinearLayout myLayout = (LinearLayout) layoutInflater.inflate(R.layout.read_book_progress, null);
			prog_int = (EditText) myLayout.findViewById(R.id.prog_int);
			prog_float = (EditText) myLayout.findViewById(R.id.prog_float);
			seekBar = (SeekBar) myLayout.findViewById(R.id.seekBar);
			// 添加事件监听
			seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				// 拖动中

				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					prog_int.setText(progress + "");
					if (progress == 100) {
						prog_float.setText("0");
					} else {
						double putvalue = progress * 0.01;
						DecimalFormat df = new DecimalFormat("#0.00");
						double fp = new Double(df.format(putvalue));
						// 小数点后两位前移，并四舍五入
						int j = (int) Math.round(fp * 100);
						prog_float.setText(j + "");
					}
				}
				// 开始拖动
				public void onStartTrackingTouch(SeekBar seekBar) {
				}
				// 结束拖动
				public void onStopTrackingTouch(SeekBar seekBar) {
				}
			});
			String string = book.getProgress();
			Log.d(TAG, "a" + string + "a");
			String begin = string.substring(0, string.indexOf('.'));
			String end = string.substring(string.indexOf('.') + 1, string.length() - 1);
			Log.d(TAG, begin + end);
			seekBar.setProgress(Integer.parseInt(begin));
			prog_int.setText(begin);
			prog_float.setText(end);
			AlterD = new AlertDialog.Builder(this);
			AlterD.setTitle("进度跳转");
			AlterD.setIcon(R.drawable.mb);
			// AlterD.setMessage("测试View加入进度条");
			AlterD.setView(myLayout);
			AlterD.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
				public void onClick(DialogInterface dialog, int which) {
					DecimalFormat df = new DecimalFormat("#0.00");
					Log.d(TAG, "prog_int:" + prog_int.getText().toString() + ",prog_float:" + prog_float.getText().toString());
					String string = prog_int.getText().toString() + "." + prog_float.getText().toString();
					double d = Double.parseDouble(string);
					double fp = new Double(df.format(d / 100));
					int begin = (int) (fp * fileLenth);
					Log.d(TAG, "begin:" + begin);
					Log.d(TAG, "fileLenth:" + fileLenth);
					// 重新设置进度位置，并清空原来的文字
					pagefactory.setM_mbBufEnd(begin);
					pagefactory.onDraw(mCurPageCanvas);
					// pagefactory.onDraw(mNextPageCanvas);
					// // 设置自定义View的进度条上一页，下一页的图片
					// mBookPageView.setBitmaps(mCurPageBitmap, mNextPageBitmap);
				}
			});
			AlterD.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			AlterD.show();
			break;
		case R.id.menu6:// 调整字体；
			settingTextSize();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 设置字体大小；
	 */
	private void settingTextSize() {
		if(textManager!=null){
//			textManager.setM_fontSize(100);
			wheelUtils();
//			reLoadBook();
		}else {
			textManager = TextManager.instance();
//			textManager.setM_fontSize(100);
			wheelUtils();
//			reLoadBook();
		}
	}

	/**
	 * Wheel工具，滚动选择大小；
	 */
	private void wheelUtils() {
		View outerView = LayoutInflater.from(this).inflate(R.layout.dialog_content_view, null);
		final WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
		wv.setItems(getNumbers(),0);//init selected position is 0 初始选中位置为0
		wv.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(int selectedIndex, String item) {
				Log.d(TAG, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
//				textManager.setM_fontSize(Integer.valueOf(item));
			}
		});

		new AlertDialog.Builder(this)
				.setTitle("请选择字体大小")
				.setView(outerView)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
//						Toast.makeText(ReadBookActivity.this,
//								"selectedIndex: "+ wv.getSelectedPosition() +"  selectedItem: "+ wv.getSelectedItem(),
//								Toast.LENGTH_SHORT).show();
						textManager.setM_fontSize(Integer.valueOf(wv.getSelectedItem()));
						reLoadBook();
					}
				})
				.show();
	}

	private ArrayList getNumbers() {
		ArrayList<String> list = new ArrayList<>();
		for (int i = 1; i < 100; i++) {
			list.add(i + "");
		}
		return  list;
	}

	/**
	 * 重新加载图书
	 */
	private void reLoadBook() {
		pagefactory = new BookPageFactory(this, width, height);
		try {
			// 实例化Service
			bookManager = new BookManager(this);
			Long bookId = getIntent().getLongExtra("bookId", -1);
			if (bookId == -1) {
				ToastUtils.toast(this, "查询的id不存在");
				startActivity(new Intent(this, MyBookActivity.class));
				return;
			}
			book = bookManager.get(bookId);
			// 读取图书
			fileLenth = pagefactory.openBook(book);
			// 绘制进度百分比
			pagefactory.onDraw(mCurPageCanvas);
		} catch (IOException e1) {
			ToastUtils.toast(this, "查询的电子书不存在");
			Log.e(TAG, e1.getMessage());
		}
	}


	private AlertDialog.Builder AlterD;
	SeekBar seekBar;
	EditText prog_int, prog_float;

	// 改变阅读页面的返回事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			startActivity(new Intent(this, MyBookActivity.class));
			ReadBookActivity.this.finish();
			return false;
		}
		return false;
	}
}