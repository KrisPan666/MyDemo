package com.jiecheng.zhike.mydemo.draft.ui.Draft;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.comm.BaseActivity;
import com.jiecheng.zhike.mydemo.draft.adapter.FirstListAdapter;
import com.jiecheng.zhike.mydemo.draft.db.DraftHelper;
import com.jiecheng.zhike.mydemo.draft.db.SubjectDataBaseHelper;
import com.jiecheng.zhike.mydemo.draft.model.Subject;

import java.util.List;

//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;

public class DrafListActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "DrafListActivity";
    private ImageView imageView1;
    private Button title_edit;
    private Button title_delete;
    private Button addSubjectBtn;
    private ImageView imageView2;
    private TextView title_back;
    private CheckBox cb;
    private SubjectDataBaseHelper sDBH;
    private SQLiteDatabase db;
    private Subject sub;
    private List<Subject> list;
    private FirstListAdapter firstListAdapter;
    private DraftHelper draftHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        sDBH = new SubjectDataBaseHelper(this);
        db = sDBH.getWritableDatabase();
        ContentValues values = new ContentValues();

        list = loadDraftList();
        if(list.size()==0){
            getEmptyPage();
        }else{
            getShowPage();
        }
        firstListAdapter = new FirstListAdapter(this, R.layout.first_list_item, list);
        ListView listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(firstListAdapter);
        imageView1 = (ImageView)findViewById(R.id.add_image);
        title_edit = (Button)findViewById(R.id.title_edit);
        title_back = (TextView)findViewById(R.id.title_back);
        title_delete =(Button)findViewById(R.id.title_delete);
        addSubjectBtn = (Button)findViewById(R.id.empty_button);
        addSubjectBtn.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        title_edit.setOnClickListener(this);
        title_back.setOnClickListener(this);
        title_delete.setOnClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onRestart");
        list = loadDraftList();
        if(list.size()==0){
            getEmptyPage();
            firstListAdapter.setItems(list);
        }else{
            getShowPage();
            firstListAdapter.setItems(list);
        }
    }
    // AsyncTask
    /**
     *遍历草稿箱，返回List<subject>
     * @return
     */
    private List<Subject> loadDraftList() {
        List<Subject> draft =null;
        draftHelper = new DraftHelper(this);
        draft = draftHelper.select();
        return draft;
    }
    @Override
    public void onClick(View view) {
        //cb = (CheckBox)findViewById(R.id.cb);
        // imageView2 = (ImageView)findViewById(R.id.To_Answer);
        switch(view.getId()){
            case R.id.add_image:
                toAddSubjectPage();
                break;
            case R.id.title_edit:
                edit();//点击编辑按钮；
                break;
            case R.id.title_back:
                back();//点击返回按钮；
                break;
            case R.id.title_delete:
                deleteHandle();
                break;
            case R.id.empty_button:
                toAddSubjectPage();
                break;
            default:
                break;
        }
    }
    /**
     * 点击编辑
     */
    private void edit(){
        title_back.setText("取消");
        title_edit.setVisibility(View.GONE);
        title_delete.setVisibility(View.VISIBLE);
        firstListAdapter.setState(FirstListAdapter.EDITE);
    }
    /**
     * 点击返回
     */
    private void back() {
        //此时点击触发的事件是取消；
        if (firstListAdapter.getState() == FirstListAdapter.EDITE) {
            title_delete.setVisibility(View.GONE);
            title_back.setText("返回");
            title_edit.setVisibility(View.VISIBLE);
            title_edit.setTextColor(0xFFFAFA00);
            firstListAdapter.setState(FirstListAdapter.NORMAL);
            firstListAdapter.resetState();
        }
        //返回
        else {
            //结束返回；
            finish();
        }
    }
    /**
     * 删除操作
     */
    private void deleteHandle() {
        List<Subject> listSub = firstListAdapter.getSelectedSubjects();
        draftHelper = new DraftHelper(this);
        if(listSub.size() == 0){
            //toast("请选择后删除");
            toast("请选择后删除");
        }else{
            draftHelper.delete(listSub);
            list = loadDraftList();
            firstListAdapter.setItems(list);
        }
    }
    /**
     * 获取空白页
     */
    private void getEmptyPage(){
        RelativeLayout showPage = (RelativeLayout)findViewById(R.id.draft_show_page);
        LinearLayout emptyPage = (LinearLayout)findViewById(R.id.draft_empty_page);
        showPage.setVisibility(View.GONE);
        emptyPage.setVisibility(View.VISIBLE);
    }
    /**
     * 展示ListView
     */
    private void getShowPage(){
        RelativeLayout showPage = (RelativeLayout)findViewById(R.id.draft_show_page);
        showPage.setVisibility(View.VISIBLE);
    }
    /**
     * 跳转到添加题目页面
     */
    private void toAddSubjectPage(){
        Intent intent = new Intent(DrafListActivity.this, EditSubjectActivity.class);
        startActivity(intent);
    }
}
