package com.jiecheng.zhike.mydemo.lesson.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiecheng.zhike.mydemo.R;


/**
 * Created by 13159 on 2017/8/11.
 */

public class LeftFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lesson_fragment_left,container,false);
        return view;
    }
    //    public View OnCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, NotificationCompat.Builder savedInstanceState){
//           View view = layoutInflater.inflate(R.layout.lesson_fragment_left,viewGroup,flase);
//        return view;
//    }
}
