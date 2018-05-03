package com.jiecheng.zhike.mydemo.lesson.Activitys;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.lesson.adapters.StudentAdapter;
import com.jiecheng.zhike.mydemo.lesson.controllers.JsonController;
import com.jiecheng.zhike.mydemo.lesson.models.Student;

import java.util.List;

public class StudentActivity extends AppCompatActivity {
    StudentAdapter studentAdapter;
    ListView listView;
    JsonController jsonController = new JsonController();
    List<Student> studentList;
    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_activity_student);
        hide();
        bindFindStudent();
    }
    //隐藏原有的标题；
    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
    private  void bindEvent(){
        studentList = jsonController.getjson();
        studentList.get(0).toString();
        Log.d("我的第一个json数据：",studentList.get(1).toString());
        studentAdapter = new StudentAdapter(this,R.layout.lesson_student_item,studentList);
        int studentCount = studentAdapter.getCount();
        textView = (TextView)findViewById(R.id.StudentCount);
        textView.setText("0/"+studentCount+"");
        listView = (ListView)findViewById(R.id.student);
        listView.setAdapter(studentAdapter);
    }
    private void bindFindStudent(){
        imageView = (ImageView) findViewById(R.id.findStudent);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindEvent();
            }
        });
    }

}
