package com.hzy.mybook.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzy.mybook.R;
import com.hzy.mybook.controllor.ActivityCollector;

public class StatementActivity extends AppCompatActivity {
    private ImageView zhifubao;
    private TextView mt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        zhifubao = (ImageView)findViewById(R.id.zhifubao_image);
        mt = (TextView) findViewById(R.id.myText);
        ActivityCollector.addActivity(this);
    }
}
