package com.jiecheng.zhike.mydemo.lesson.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.lesson.models.MenuInfo;

import java.util.List;

/**
 * Created by 13159 on 2017/8/8.
 */

public class MenuLeftAdapter extends BaseAdapter {
    private final Context context;
    private int resourceId;
    LayoutInflater layoutInflater;
    MenuInfo menuInfo;
    private List<MenuInfo> infoList;
    ImageView imageButton;
    TextView textView;

    public MenuLeftAdapter(Context context, int resourceId,List<MenuInfo> Object) {
        this.context = context;
        this.resourceId=resourceId;
        this.infoList = Object;
        layoutInflater=LayoutInflater.from(context);
    }
    public void setItem(List<MenuInfo> items){
        this.infoList=items;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return infoList.size();
    }
    @Override
    public MenuInfo getItem(int i) {
        return infoList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return resourceId;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        menuInfo = (MenuInfo) getItem(position);//获取当前MenuInfo实例；
        view = layoutInflater.inflate(resourceId,null);
        imageButton = (ImageView)view.findViewById(R.id.enum_ImageButton);
        textView = (TextView)view.findViewById(R.id.enum_text);
        imageButton.setImageResource(menuInfo.getId());
        textView.setText(menuInfo.getName());
        return view;
    }
}
