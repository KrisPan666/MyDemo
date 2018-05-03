package com.jiecheng.zhike.mydemo.draft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.draft.model.SubType;
import com.jiecheng.zhike.mydemo.draft.viewHolder.ViewHolder;

import java.util.List;

/**
 * Created by 13159 on 2017/8/18.
 */

public class SpinnerAdapter extends BaseAdapter {
    private List<SubType> mlist;
    private Context mcontext;

    public SpinnerAdapter(Context pcontext, List<SubType> list) {
        this.mlist = list;
        this.mcontext = pcontext;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if(view==null){
            view = LayoutInflater.from(mcontext).inflate(R.layout.item_sub_type,null);
            viewHolder = new ViewHolder();
            viewHolder.Spinner_textView = (TextView)view.findViewById(R.id.subType_item);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.Spinner_textView.setText(mlist.get(i).getType());
        return view;
    }
}
