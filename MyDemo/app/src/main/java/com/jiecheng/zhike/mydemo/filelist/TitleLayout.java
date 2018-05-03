package com.jiecheng.zhike.mydemo.filelist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiecheng.zhike.mydemo.R;

/**
 * 自定义控件；对标题栏中的返回按钮和编辑按钮进行操作；
 * 解决了重复编写代码，可以再次利用
 * author by panhongyu(@hypan3.iflytek.com)
 * Created by 13159 on 2017/8/2.
 */
public class TitleLayout extends LinearLayout {
   private Button titleBack;
   private Button titleEdit;

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //调用inflate（）方法就可以动态的加载一个布局文件；
        // 两个参数，第一个是布局的id，第二个是给加载好的布局再添加一个父布局，这里指Titlelayout
        LayoutInflater.from(context).inflate(R.layout.filelist_title,this);
        titleBack = (Button)findViewById(R.id.title_back);
        titleEdit = (Button)findViewById(R.id.title_edit);
        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onBack();
                }
//                new Thread() {
//                    public void run() {
//                        try {
//                            Instrumentation inst = new Instrumentation();
//                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//                        } catch (Exception e) {
//                          Log.e("异常：",e.toString());
//                        }
//                    }
//                }.start();
            }
        });
        titleEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"我猜中了你会点，功能还未实现，敬请等待功能完善", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface IOnBackListener {
        void onBack();
    }
    private IOnBackListener listener;
    public void setIOnBackListener(IOnBackListener listener) {
        this.listener= listener;
    }

}
