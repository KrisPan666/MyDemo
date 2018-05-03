package com.jiecheng.zhike.mydemo.filelist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;

/**
 * Created by 13159 on 2017/8/3.
 */

public class TextViewActivity extends Activity {

    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filelist_openfile);
        txtView = (TextView)findViewById(R.id.open_file);
        if(txtView!=null){
            Intent intent = getIntent();
            String data = intent.getStringExtra("extra_text");
//            Log.d("",data);
            txtView.setText(data);
        }

    }
}
