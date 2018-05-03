package com.jiecheng.zhike.mydemo.draft.ui.Draft;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.comm.BaseActivity;
import com.jiecheng.zhike.mydemo.draft.adapter.SpinnerAdapter;
import com.jiecheng.zhike.mydemo.draft.adapter.viewPageAdapter;
import com.jiecheng.zhike.mydemo.draft.db.SubjectDataBaseHelper;
import com.jiecheng.zhike.mydemo.draft.model.SubType;
import com.jiecheng.zhike.mydemo.draft.model.Subject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditSubjectActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "EditSubjectActivity";
    private SubjectDataBaseHelper sDBH;
    private static SQLiteDatabase db;
    private Button btn_save_draft;
    private Button btn_add_continue;
    private EditText et1;
    private EditText et2;
    private Spinner s_elements;
    private int s_position;//记录选择的位置
    private String element;
    private TextView tv;
    private String strTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //初始化ViewPager；
        initViewPager();
        //初始化Spinner；
        initSpinner();
        sDBH = new SubjectDataBaseHelper(this);
        db = sDBH.getWritableDatabase();

        btn_save_draft = (Button)findViewById(R.id.save_draft);
        btn_add_continue = (Button)findViewById(R.id.add_continue);

        btn_save_draft.setOnClickListener(this);
        btn_add_continue.setOnClickListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(this,DrafListActivity.class);
        finish();
    }
    @Override
    public void onClick(View view) {
        ContentValues values = new ContentValues();
        switch (view.getId()){
            case R.id.save_draft:
                insert();//插入数据；
                break;
            case R.id.sent:
                break;
            case R.id.delete:
                break;
            case R.id.add_continue:
                AddContinue();
                break;
            default:
                break;
        }
    }

    /**
     * 题面继续添加题目图片；
     */
    private void AddContinue() {
        //打开相册或者拍照上传；
//        openPhoto();
//        takePhoto();
    }

    //向数据库插入数据；
    private void insert(){
        ContentValues values = new ContentValues();
                //创建数据库；
//                sDBH.getReadableDatabase();
//                Toast.makeText(this,"数据库创建成功！！！",Toast.LENGTH_SHORT).show();
                //从页面获取到数据，并set到subject模型中；然后再取；
                et1 = (EditText)findViewById(R.id.subName_value);
                //获得Spinner
                SubType sele = (SubType) s_elements.getSelectedItem();
                String subType = sele.getType();
                et2 = (EditText)findViewById(R.id.subSum_value);
                String sunName = et1.getText().toString();
//                String subType = (String)s_elements.getSelectedItem();

                String sumSub;
                Pattern pattern1 = Pattern.compile("[0-9]*");
                Matcher isNum1 = pattern1.matcher(et2.getText().toString());
           if(isNum1 != null){
                if(!isNum1.matches()){
                    toast("题目数量请输入数字！！");
                    et2.setTextColor(0xffFF3300);
                }else{
                    et2.setTextColor(0xff000000);
                    sumSub = et2.getText().toString();
                    long createTime1 = System.currentTimeMillis();
                    String createTime = String.valueOf(System.currentTimeMillis());
                    //格式化時間；
                    Date currentTime = new Date();
                    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString1 = formatter1.format(currentTime);
                    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmss");
                    String dateString2 = formatter2.format(currentTime);
                    Subject sub = new Subject(sunName,subType,sumSub,dateString1);

                    //保存题目信息数据；
                    values.put("subName",sub.getSubName()+dateString2);
                    values.put("subType",sub.getSubType());
                    values.put("subNum",sub.getSubNum());
                    values.put("createTime",sub.getTime());
                    db.insert("subject",null,values);
                    toast("题目保存成功！");
                }
           }else{
               toast("请填写题目数量！");
           }
    }
    // 下拉框选择事件
    private class OnItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            element = parent.getItemAtPosition(position).toString();// 得到spanner的值
            s_position = position;
            Toast.makeText(EditSubjectActivity.this, "选择的元素是：" +element,Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
    //增加Spanner显示题目类型；
    private void initSpinner() {
        //Spinner spinner = (Spinner)findViewById(R.id.subType_Spanner);
        s_elements = (Spinner) findViewById(R.id.subType_Spanner);
        List<SubType> list = new ArrayList<SubType>();
        list.add(new SubType("填空题"));
        list.add(new SubType("选择题"));
        list.add(new SubType("判断题"));
        list.add(new SubType("主观题"));
        SpinnerAdapter spinAdapter = new SpinnerAdapter(EditSubjectActivity.this,list);
        s_elements.setAdapter(spinAdapter);
    }
    //使用viewPage；
    private void initViewPager(){
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        View view1 = LayoutInflater.from(this).inflate(R.layout.layout1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.layout2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.layout3, null);
        View view4 = LayoutInflater.from(this).inflate(R.layout.layout4, null);
        ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        viewPageAdapter adapter = new viewPageAdapter();
        adapter.setViews(views);
        viewPager.setAdapter(adapter);
    }
}
