package com.jiecheng.zhike.mydemo.lesson.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.lesson.controllers.JsonController;
import com.jiecheng.zhike.mydemo.lesson.models.Student;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 13159 on 2017/8/9.
 */

public class StudentAdapter extends BaseAdapter {
    private Context context;
    private int resourceId;
    private ImageView imageView;
    private TextView textView;
    private TextView textView2;
    Student student;
    List<Student> list;
    LayoutInflater layoutInflater;
    JsonController jsonController;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Student getItem(int position) {
        return list.get(position);
    }
    public void setItem(List<Student> items){
        this.list = items;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public StudentAdapter(Context context, int resourceId, List<Student> student){
        this.context = context;
        this.resourceId = resourceId;
        this.list = student;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        student = getItem(position);
        view = layoutInflater.inflate(resourceId, null);
        imageView = view.findViewById(R.id.userPhoto);
        textView = view.findViewById(R.id.userName);
        textView2 = view.findViewById(R.id.banStatus);
//        try {
            String userPhotoUrl = student.getUserPhoto();
//            JsonController jsonController = new JsonController();
//            Bitmap bitmap;
//            bitmap = jsonController.getBitmap(userPhotoUrl);
//            if(bitmap!=null){
//                imageView.setImageBitmap(bitmap);
//            }

//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final Bitmap bitmap = jsonController.getBitmap(userPhotoUrl);
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        imageView.setImageBitmap(bitmap);
//                    }
//                });
//            }
//        }).start();

        Picasso.with(context).load(userPhotoUrl).into(imageView);
        //Picasso.with(context).load(R.mipmap.ic_launcher).into(imageView);
        textView.setText(student.getUserName());
        textView2.setText(student.getBanStatus());
        return view;
    }

}
