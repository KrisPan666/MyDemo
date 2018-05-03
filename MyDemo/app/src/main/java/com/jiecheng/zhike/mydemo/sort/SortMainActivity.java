package com.jiecheng.zhike.mydemo.sort;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortMainActivity extends AppCompatActivity {
    private TextView mCurrentTv;
    private TextView mAfterTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_main);
        initUI();
        List<Student> stuList = new ArrayList<Student>();
        stuList.add(new Student(23, 100));
        stuList.add(new Student(27, 98));
        stuList.add(new Student(29, 39));
        stuList.add(new Student(29, 58));
        stuList.add(new Student(22, 89));
        mCurrentTv.setText(stuList.get(0).getScore()+","+stuList.get(1).getScore()+","+stuList.get(2).getScore()+","+stuList.get(3).getScore()+","+stuList.get(4).getScore()+",");
        sortList(stuList);
        mAfterTv.setText(stuList.get(0).getScore()+","+stuList.get(1).getScore()+","+stuList.get(2).getScore()+","+stuList.get(3).getScore()+","+stuList.get(4).getScore()+",");
    }

    private void sortList(List<Student> students) {
        Collections.sort(students, new Comparator<Student>() {

            @Override
            public int compare(Student o1, Student o2) {
                //i<0:降序；i>0:升序
                int i = -(o1.getScore() - o2.getScore());
//                if(i == 0){
//                    return o1.getAge() - o2.getAge();
//                }
                return i;
            }
        });
    }

    private void initUI() {
        mCurrentTv = (TextView)findViewById(R.id.sort_current_text);
        mAfterTv = (TextView)findViewById(R.id.sort_after_text);
    }
}
