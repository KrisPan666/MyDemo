package com.jiecheng.zhike.mydemo.filelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.filelist.Utils.fileUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import java.io.File;

/**
 * 自定义的文件适配器；
 * 将获取的文件的图片及文件名称在ListView中展示出来；
 * author by panhongyu(@hypan3.iflytek.com)
 * Created by 13159 on 2017/7/31.
 */
public class FileAdapter  extends BaseAdapter {
    private final Context context;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private int resourceId;
    private List<FileInfo> items;
    private LayoutInflater layoutInflater;

    public FileAdapter(Context context, int resourceId) {
        this.context = context;
        this.resourceId = resourceId;
        layoutInflater = LayoutInflater.from(context);
    }
    public void setItem(List<FileInfo> items) {
        this.items = items;
        notifyDataSetChanged();
    }
    public List<FileInfo> getItems() {
        return items;
    }
    @Override
    public int getCount() {
        if(items!=null){
            return items.size();
        }
         return 0;
    }
    @Override
    public FileInfo getItem(int position) {
        return items.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FileInfo file = getItem(position);//获取当前项的File实例
        convertView = layoutInflater.inflate(resourceId,null);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.File_image);
        textView1 = (TextView)convertView.findViewById(R.id.File_name);
        textView2 = (TextView)convertView.findViewById(R.id.File_name2);
        if (file.isDir()) {
            imageView.setImageResource(R.mipmap.filesystem_icon_folder);
        }
        else {
            imageView.setImageResource(file.getImageId());
        }
        textView1.setText(file.getName());
        Date date = new Date(file.getLastModified());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
//      textView2.setText(file.getSize()+"项   "+dateStr);
        if (file.isDir()) {
            textView2.setText(getFileCount(file.getPath().toString())+ "项   " + dateStr);
        }else{
            textView2.setText(fileUtil.toSize(file.getSize())+"  "+dateStr);
        }
        return convertView;
    }

    //读取所有文件和文件夹
    private static int getFileCount(String dir){
        File f = new File(dir);
        File[] fs = f.listFiles();//系统文件返回null
        int num1=0;//文件
        int num2=0;//目录
        if(fs!=null){
            for(int i=0;i<fs.length;i++){
                File currentFile = fs[i];
                if(currentFile.isFile()){//文件，直接显示
                    num1+=1;
                }else{
                    num2+=1;
                    getFileCount(currentFile.getAbsolutePath());//是目录的时候，继续调用方法，读取文件和文件夹；
                }
            }
        }
        System.out.println("文件数"+num1);
        System.out.println("目录数"+num2);
        return num1+num2;
    }

}
