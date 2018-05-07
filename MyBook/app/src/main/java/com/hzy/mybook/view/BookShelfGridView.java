package com.hzy.mybook.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.GridView;

import com.hzy.mybook.R;
import com.hzy.mybook.ui.MyBookActivity;
import com.hzy.mybook.utils.ImageUtil;

public class BookShelfGridView extends GridView {
	private Bitmap background;
	public BookShelfGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	protected void dispatchDraw(Canvas canvas) {
		int count = getChildCount();// 一个屏幕看到多少个item
//		int height = MyBookActivity.itemBackground.getHeight();
		//create by phy
		int height = MyBookActivity.list.get(0).getHeight();
		background = ImageUtil.getBitmap(getContext(), R.drawable.bookshelf_layer_center, MyBookActivity.width / 3, height + height / 4);
		int top = count > 0 ? getChildAt(0).getTop() : 0;
		int backgroundWidth = background.getWidth();
		int backgroundHeight = background.getHeight();

		for (int y = top; y < MyBookActivity.height; y += backgroundHeight) {
			for (int x = 0; x < MyBookActivity.width; x += backgroundWidth) {
				// System.out.println(x + "--------------------" + y);
				canvas.drawBitmap(background, x, y, null);
			}
		}
		super.dispatchDraw(canvas);
	}
}
