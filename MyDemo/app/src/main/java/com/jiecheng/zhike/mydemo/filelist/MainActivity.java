package com.jiecheng.zhike.mydemo.filelist;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.jiecheng.zhike.mydemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = "PxAndDpActivity";
    private List<File> fileList = new ArrayList<File>();
    private File currPath = null;
    private File rootPath = null;


    private static HashMap<String, String> map = new HashMap<>();

    static {
        map.put("", "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filelist_activity_main);
        //1.隐藏原有的标题；
        hide();
//        FileInfo file = new FileInfo("/");
//        getDirectory(file);
//        file.getName();
        String str1 = getSdCardDirectory();
        File root = new File(str1);
        rootPath = root;

        //2.初始化文件；
//        initFile();
//        FileAdapter fileAdapter = new FileAdapter(PxAndDpActivity.this,R.layout.item_file, fileList);
//        ListView listView = (ListView) findViewById(R.id.list_view);
//        listView.setAdapter(fileAdapter);
    }

    public static String getSdCardDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    //隐藏原有的标题；
    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
//    //初始化数据；
//    private void initFile() {
//        for(int i=0;i<2;i++){
//            int a1 = R.mipmap.filesystem_icon_folder;
//            int a2 = R.mipmap.filesystem_icon_movie;
//            int a3 = R.mipmap.filesystem_icon_music;
//            int a4 = R.mipmap.filesystem_icon_pdf;
//            int a5 = R.mipmap.filesystem_icon_photo;
//            int a6 = R.mipmap.filesystem_icon_ppt;
//            int a7 = R.mipmap.filesystem_icon_rar;
//            int a8 = R.mipmap.filesystem_icon_text;
//            int a9 = R.mipmap.filesystem_icon_txt;
//            FileInfo file1 =new FileInfo(a1,"我是一个文件夹");
//            fileList.add(file1);
//            FileInfo file2 =new FileInfo(a2,"我是一个电影");
//            fileList.add(file2);
//            FileInfo file3 =new FileInfo(a3,"我是一个音乐");
//            fileList.add(file3);
//            FileInfo file4 =new FileInfo(a4,"我是一个PDF");
//            fileList.add(file4);
//            FileInfo file5 =new FileInfo(a5,"我是一个photo");
//            fileList.add(file5);
//            FileInfo file6 =new FileInfo(a6,"我是一个ppt");
//            fileList.add(file6);
//            FileInfo file7 =new FileInfo(a7,"我是一个压缩文件");
//            fileList.add(file7);
//        }
//    }
// 递归遍历
//private void getDirectory(File file) {
//    File[]  flist= file.listFiles();
//    FileAdapter fileAdapter = new FileAdapter(PxAndDpActivity.this,R.layout.item_file, flist);
//
//    if (flist == null || flist.length == 0) {
//        Toast.makeText(PxAndDpActivity.this,"没有文件啊",Toast.LENGTH_SHORT).show();
//    }
//    for (File f : flist) {
//        if (f.isDirectory()) {
//            //这里将列出所有的文件夹
//            System.out.println("Dir==>" + f.getAbsolutePath());
//            getDirectory(f);
//        } else {
//            //这里将列出所有的文件
//            System.out.println("file==>" + f.getAbsolutePath());
//        }
//    }
//}
//
//
//}
