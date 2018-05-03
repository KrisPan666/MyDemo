package com.jiecheng.zhike.mydemo.draft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.comm.Demo;
import com.jiecheng.zhike.mydemo.draft.viewHolder.ViewHolder;

import java.util.List;

/**
 * Created by 13159 on 2017/8/26.
 */

public class mainAdapter extends BaseAdapter {
    private Context context;
    private int ResourceId;
    private LayoutInflater Inflater;
    private List<Demo> items;
    private TextView demoNameText;
    private TextView createTimeText;

    public mainAdapter(Context context, int resourceId, List<Demo> items) {
        this.context = context;
        ResourceId = resourceId;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHolder vh;
        Demo demo = (Demo) getItem(position);
        if(view==null){
            vh = new ViewHolder();
            view = Inflater.from(context).inflate(ResourceId,viewGroup,false);
            vh.demoNameText = (TextView)view.findViewById(R.id.demo_Name);
            vh.createTimeText = (TextView)view.findViewById(R.id.demo_create_date);
            view.setTag(vh);
        }else{
            vh = (ViewHolder) view.getTag();
        }
            vh.demoNameText.setText(demo.getDemoName());
            vh.createTimeText.setText(demo.getTime());
        return view;
    }
}
