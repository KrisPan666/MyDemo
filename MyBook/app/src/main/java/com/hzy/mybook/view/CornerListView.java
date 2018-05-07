package com.hzy.mybook.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hzy.mybook.R;

/**
 * 自定义圆角ListView
 */
public class CornerListView extends ListView {
	public CornerListView(Context context) {
		this(context, null);
	}
	public CornerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 整个listview的圆角背景
		this.setBackgroundResource(R.drawable.corner_list_bg);
	}
	/**
	 * 关于Android中事件传递
	 */
	/*
	 * onInterceptTouchEvent:
	 * 
	 * onInterceptTouchEvent是在ViewGroup里面定义的。Android中的layout布局类一般都是继承此类的。 onInterceptTouchEvent是用于拦截手势事件的，每个手势事件都会先调用onInterceptTouchEvent。
	 * 
	 * onTouchEvent: * onTouchEvent同样也是在view中定义的一个方法。处理传递到view 的手势事件。 手势事件类型包括ACTION_DOWN,ACTION_MOVE,ACTION_UP,ACTION_CANCEL等事件。
	 */

	/*
	 * 其中Layout里的onInterceptTouchEvent默认返回值是false,这样touch事件会传递到View控件，Layout里的onTouch默认返回值是false,
	 * View里的onTouch默认返回值是true,当我们手指点击屏幕时候，先调用ACTION_DOWN事件
	 * ,当onTouch里返回值是true的时候，onTouch回继续调用ACTION_UP事件，如果onTouch里返回值是false，那么onTouch只会调用ACTION_DOWN而不调用ACTION_UP.
	 */

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) event.getX();
			int y = (int) event.getY();
			// 通过坐标获取索引
			int itemnum = pointToPosition(x, y);
			if (itemnum == AdapterView.INVALID_POSITION) {
				// 代表无效的位置。有效值的范围是 0 到当前适配器项目数减 1 。
				break;
			}
			// 设置不同的选择器:不同的原角效果
			if (itemnum == 0) {
				if (itemnum == (getAdapter().getCount() - 1)) { // 只有一项
					setSelector(R.drawable.corner_list_single_item);
				} else {// 第一项
					setSelector(R.drawable.corner_list_first_item);
				}
			} else if (itemnum == (getAdapter().getCount() - 1)) { // 最后一项
				setSelector(R.drawable.corner_list_last_item);
			} else {// 中间项
				setSelector(R.drawable.corner_list_item);
			}
			break;
		}
		return super.onInterceptTouchEvent(event);
	}
}
