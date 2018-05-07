package com.hzy.mybook.document;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.hzy.mybook.R;
import com.hzy.mybook.model.Book;
import com.hzy.mybook.manager.BookManager;
import com.hzy.mybook.ui.base.BaseActivity;
import com.hzy.mybook.utils.FileUtils;
import com.hzy.mybook.utils.ToastUtils;

import java.io.File;

public class ReadBook extends BaseActivity implements OnGestureListener {
	private BookManager bookManager;
	private ViewFlipper flipper;// ViewFlipper实例
	private GestureDetector detector;// 触摸监听实例
	private Long bookId = -1L;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_book);
		detector = new GestureDetector(this);// 初始化触摸探测
		flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper01);// 获得ViewFlipper实例
		// 实例化Service
		bookManager = new BookManager(this);
		bookId = getIntent().getLongExtra("bookId", -1);
		if (bookId != -1) {
			Book book = bookManager.get(bookId);
			String filePath = book.getFilePath();
			try {
				File file = new File(filePath);
				if (!file.exists()) {// 文件不存在
					ToastUtils.toast(ReadBook.this, "文件不存在");
					return;
				}
				FileUtils.readString(file, 100, 1024);
				// 取文件根据字体大小体积，计算
				// InputStream in = new FileInputStream(file);
				// ByteArrayOutputStream out = new ByteArrayOutputStream();
				// byte[] buffer = new byte[1024];
				// int len = -1;
				// while ((len = in.read(buffer)) > 0) {
				// out.write(buffer, 0, len);
				// // // 将View添加到flipper队列中
				// flipper.addView(addTextView(out.toString("GBK")));
				// }
				// in.close();
				// out.close();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
		}
	}
	private View addTextView(String text) {
		TextView tv = new TextView(this);
		tv.setText(text);
		tv.setGravity(1);
		return tv;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.detector.onTouchEvent(event);
	}
	// 主要是做一个translation动画,fromXDelta：动画的开始X位置，toXDelta:动画的结束X位置，duration：持续时间.
	// 然后将onFling方法修改为如下：
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (e1.getX() - e2.getX() > 120) {// 如果是从右向左滑动
			// 注册flipper的进出效果
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
			this.flipper.showNext();
			return true;
		} else if (e1.getX() - e2.getX() < -120) {// 如果是从左向右滑动
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right_out));
			this.flipper.showPrevious();
			return true;
		}
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

}