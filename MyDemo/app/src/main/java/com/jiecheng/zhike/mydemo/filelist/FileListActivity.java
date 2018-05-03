package com.jiecheng.zhike.mydemo.filelist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.filelist.Utils.fileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @panhongyu(@hypan3.iflytek.com)
 * 文件管理器；
 * 主要实现文件遍历，读取文件，排序，编辑等功能；
 * Created by 13159 on 2017/8/2.
 */

public class FileListActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "extra_text";
    private static final String EXTRA_FILESIZE = "extra_filesize";
    private static final String TAG = "FileListActivity";
    private ImageView imageButton1;
    private ImageView imageButton2;
    private Button titleBack;
    private File currPath;
    private File rootPath;
    private  FileAdapter fileAdapter;
    private Button btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filelist_activity_main);
        //隐藏系统提供的title；
        hide();
        //初始化图片按钮；
        imageButton1 = (ImageView) findViewById(R.id.newfile);
        imageButton2 = (ImageView) findViewById(R.id.sort);
        imageButton1.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Toast.makeText(FileListActivity.this,"终于弹出来了！！！", Toast.LENGTH_SHORT).show();
                // TODO Auto-generated method stub
                Dialog dialog=new AlertDialog.Builder(FileListActivity.this)
                        .setTitle("提示")
                        .setMessage("我是ImageButton1")
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                imageButton1.setImageDrawable(getResources().getDrawable(android.R.drawable.sym_action_call));
                            }
                        }).create();
                dialog.show();
            }
        });
        imageButton2.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Dialog dialog=new AlertDialog.Builder(FileListActivity.this)
                        .setTitle("提示")
                        .setMessage("我是ImageButton2")
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                //相应的处理操作
                            }
                        }).create();
                dialog.show();
            }
        });
        // step1 获取文件列表
        File rootFile = getSdCardDirectory();
        rootPath = rootFile;
        List<FileInfo> rootfileInfoList = getListFile(rootFile);
        // step2 填充
        ListView listView = (ListView) findViewById(R.id.list_view);
        fileAdapter = new FileAdapter(this, R.layout.item_file);
        listView.setAdapter(fileAdapter);
        fileAdapter.setItem(rootfileInfoList);
        //遍历文件夹和读取文件；
        // a/b/c
        // a/b
        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileInfo fileInfo = fileAdapter.getItem(position);
                currPath = fileInfo.getPath();
                if (fileInfo.isDir()) {
                    List<FileInfo> fileInfoList = getListFile(fileInfo.getPath());
                    fileAdapter.setItem(fileInfoList);
                }
                else  {
//                    String text = fileUtil.getString(fileInfo.getPath());
//                    Log.d("返回的Text是：",text);
//                    Intent it = new Intent( FileListActivity.this, TextViewActivity.class);
//                    it.putExtra(EXTRA_TEXT, text);
//                    it.putExtra(EXTRA_FILESIZE, fileInfo.getSize());
//                    FileListActivity.this.startActivity(it);
                    fileUtil.openFile(view.getContext(), fileInfo.getPath());
                }
            }
        });

        //按钮的逐级返回
        btn = (Button)findViewById(R.id.title_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (currPath.getPath().equals(rootPath.getPath())) {
                        finish();
                    }else {
                        List<FileInfo> fileInfoList = getListFile(currPath.getParentFile());
                        fileAdapter.setItem(fileInfoList);
                    }
            }
        });

//        TitleLayout t = new TitleLayout(FileListActivity.this);
//        t.setIOnBackListener(new TitleLayout.IOnBackListener() {
//                    @Override
//                    public void onBack() {
//                        if (currPath.getPath().equals(rootPath.getPath())) {
//                            finish();
//                        }
//                        else {
//                            List<FileInfo> fileInfoList = getListFile(currPath.getParentFile());
//                            fileAdapter.setItem(fileInfoList);
//                        }
//                    }
//                });
        TitleLayout titleLayout = (TitleLayout)findViewById(R.id.titleLayout);
        titleLayout.setIOnBackListener(new TitleLayout.IOnBackListener() {
            @Override
            public void onBack() {
                if (currPath.getPath().equals(rootPath.getPath())) {
                    finish();
                }
                else {
                    List<FileInfo> fileInfoList = getListFile(currPath.getParentFile());
                    fileAdapter.setItem(fileInfoList);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }
    //逐级返回
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        if (currPath.getPath().equals(rootPath.getPath())) {
            super.onBackPressed();
        }
        else {
            List<FileInfo> fileInfoList = getListFile(currPath.getParentFile());
            currPath = currPath.getParentFile();
            fileAdapter.setItem(fileInfoList);
        }
    }

    private List<FileInfo> getListFile(File dir) {
        List<FileInfo> fileInfos = new ArrayList<>();
        File[] list = dir.listFiles();
        for (File f : list) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setPath(f);
            fileInfo.setName(f.getName());
            fileInfo.setLastModified(f.lastModified());
            fileInfo.setSize(f.length());

            String fileExtName= getExtName(f.getName());
            FileType fileType = FileInfo.getFileType(fileExtName);
            fileInfo.setImageId(fileType.code);

            fileInfos.add(fileInfo);
        }
        // 排序
        Collections.sort(fileInfos, new Comparator<FileInfo>() {
            @Override
            public int compare(FileInfo lhs, FileInfo rhs) {
                if (lhs.isDir() && !rhs.isDir()) {
                    return -1;
                }
                if (!lhs.isDir() && rhs.isDir()) {
                    return 1;
                }
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        return fileInfos;
    }
    //截取文件‘.’后面的名称字符串；
    private static String getExtName(String name) {
        int i  = name.indexOf('.');
        return (i > 0 ? name.substring(i, name.length()) : "");
    }
    public static File getSdCardDirectory() {
        return Environment.getExternalStorageDirectory();
    }
    //隐藏原有的标题；
    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

//    private void initViewPager(){
//        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
//
//        View view1 = LayoutInflater.from(this).inflate(R.layout.filelist_layout_1, null);
//        View view2 = LayoutInflater.from(this).inflate(R.layout.filelist_layout_2, null);
//        View view3 = LayoutInflater.from(this).inflate(R.layout.filelist_layout_3, null);
//        View view4 = LayoutInflater.from(this).inflate(R.layout.filelist_activity_main,null);
//
//        ArrayList<View> views = new ArrayList<View>();
//        views.add(view1);
//        views.add(view2);
//        views.add(view3);
//        views.add(view4);
//
//        MyAdapter adapter = new MyAdapter();
//        adapter.setViews(views);
//        viewPager.setAdapter(adapter);
//    }

}
