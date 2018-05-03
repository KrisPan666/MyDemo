package com.jiecheng.zhike.mydemo.filelist;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.filelist.adapters.MyAdapter;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filelist_main);
        //隐藏标题栏；
        hide();
        //切换页面；
        initViewPager();
    }
    private void initViewPager(){
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        View view1 = LayoutInflater.from(this).inflate(R.layout.filelist_layout_1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.filelist_layout_2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.filelist_layout_3, null);
        View view4 = LayoutInflater.from(this).inflate(R.layout.filelist_activity_main,null);

        ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);

        MyAdapter adapter = new MyAdapter();
        adapter.setViews(views);
        viewPager.setAdapter(adapter);
    }
    //隐藏原有的标题；
    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
