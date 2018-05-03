package com.jiecheng.zhike.mydemo.httpJson.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.httpJson.model.Bank;
import com.jiecheng.zhike.mydemo.httpJson.util.JsonController;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 13159 on 2017/8/31.
 * author:panhongyu
 */

public class BankAdapter extends BaseAdapter {
    private Context context;
    private int resourceId;
    private TextView questionAndClassType_tv;
    private TextView createDate_tv;
    private Bank bank;
    List<Bank> list;
    LayoutInflater layoutInflater;
    JsonController jsonController;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setItem(List<Bank> items){
        this.list = items;
        notifyDataSetChanged();
    }
    public BankAdapter(Context context, int resourceId, List<Bank> list) {
        this.context = context;
        this.resourceId = resourceId;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        bank = (Bank)getItem(i);
        view = layoutInflater.inflate(resourceId, null);
        questionAndClassType_tv = view.findViewById(R.id.question_Class_type);
        createDate_tv = view.findViewById(R.id.question_create_date);
        questionAndClassType_tv.setText(bank.getWorkname()+"   "+bank.getClassname());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long longTime = Long.parseLong(bank.getDatecreated());
        String createTime = format.format(longTime);
        createDate_tv.setText("创建时间："+createTime);
        return view;
    }
}
