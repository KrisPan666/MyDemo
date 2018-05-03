package com.jiecheng.zhike.mydemo.lesson.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;

/**
 * author：panhongyu
 * @email:hypan3.iflytek.com
 * Created by 13159 on 2017/8/11.
 */

public class NewsContentFragment extends Fragment {
    private  View view;
    TextView newsTitleText;
    TextView newsContentText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lesson_news_content_frag,container,false);
        return view;
    }
    public void refresh(String newsTitle,String newsContent){
        View visibilityLayout = view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        newsTitleText = (TextView)view.findViewById(R.id.news_title);
        newsContentText = (TextView)view.findViewById(R.id.news_content);
        newsTitleText.setText(newsTitle);//刷新新闻标题；
        newsContentText.setText(newsContent);//刷新新闻内容；
    }
}
