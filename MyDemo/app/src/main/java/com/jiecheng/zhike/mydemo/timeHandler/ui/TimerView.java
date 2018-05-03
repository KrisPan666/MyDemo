package com.jiecheng.zhike.mydemo.timeHandler.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.jiecheng.zhike.mydemo.R;

/**
 * Created by 13159 on 2017/8/30.
 */

public class TimerView extends LinearLayout {

    public TimerView(Context context) {
        super(context);
        initUI();
    }

    public TimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public TimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    private void initUI() {
        inflate(getContext(), R.layout.timehandler_view_timer, this);

    }
}
