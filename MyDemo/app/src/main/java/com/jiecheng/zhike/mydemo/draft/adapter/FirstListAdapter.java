package com.jiecheng.zhike.mydemo.draft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.draft.model.Subject;
import com.jiecheng.zhike.mydemo.draft.viewHolder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13159 on 2017/8/21.
 */

public class FirstListAdapter extends BaseAdapter {
    private final Context context;
    private int resourceId;
    private List<Subject> items;
    private LayoutInflater Inflater;
    /**
     * 0 正常
     * 1 编辑
     */
    public final static int NORMAL = 0;
    public final static int EDITE = 1;
    private int state = NORMAL;

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
        notifyDataSetChanged();
    }
    /**
     * 重置item的状态
     */
    public void resetState() {
        for (Subject sub: items) {
            sub.setChecked(false);
        }
    }
    public FirstListAdapter(Context context, int resourceId, List<Subject> items) {
        this.context = context;
        this.resourceId = resourceId;
        this.items = items;  }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Subject getItem(int i) {
        return items.get(i);
    }

    public void setItems(List<Subject> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    /**
     * 加载布局，返回一个view
     * @param position
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        Subject subject = getItem(position);//获取当前项的subject实例;
        final ViewHolder viewHolder;
        if(view==null){
            view = Inflater.from(context).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = (TextView)view.findViewById(R.id.sub_name);
            viewHolder.textView2 = (TextView)view.findViewById(R.id.sub_create_date);
            viewHolder.imageView1 = (ImageView)view.findViewById(R.id.subject_image);
            viewHolder.imageView2 = (ImageView)view.findViewById(R.id.To_Answer);
            viewHolder.imgCheck = (CheckBox)view.findViewById(R.id.cb);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
            viewHolder.imgCheck.getTag(position);
        }
        viewHolder.imgCheck.setTag(position);

        viewHolder.textView1.setText(subject.getSubType()+":"+subject.getSubName());
        viewHolder.textView2.setText("创建时间："+subject.getTime());
        viewHolder.imageView1.setImageResource(R.mipmap.subject);
        viewHolder.imageView2.setImageResource(R.mipmap.interactive_ic_next_nor);

        if (state == NORMAL) {
            viewHolder.imgCheck.setVisibility(View.GONE);
            viewHolder.imageView2.setVisibility(View.VISIBLE);
        }
        else if(state==EDITE){
            viewHolder.imgCheck.setVisibility(View.VISIBLE);
            viewHolder.imageView2.setVisibility(View.GONE);
        }

        viewHolder.imgCheck.setChecked(items.get(position).isChecked());
        viewHolder.imgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ViewHolder.imgCheck.setChecked(!ViewHolder.imgCheck.isChecked());
                items.get(position).setChecked(viewHolder.imgCheck.isChecked());
            }
        });
        return view;
    }
    /**
     * 获得被选择的item，并返回一个list
     * @return
     */
    public List<Subject> getSelectedSubjects() {
        List<Subject> list = new ArrayList<Subject>();
        for (Subject sub: items) {
            if (sub.isChecked()) {
                list.add(sub);
            }
        }
        return list;
    }

}
