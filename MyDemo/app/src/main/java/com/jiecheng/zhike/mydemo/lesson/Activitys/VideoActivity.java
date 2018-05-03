package com.jiecheng.zhike.mydemo.lesson.Activitys;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.jiecheng.zhike.mydemo.R;

import java.io.File;

import static android.Manifest.permission;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button1;
    private Button button2;
    private Button button3;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_video_layout);
        init();
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(VideoActivity.this, permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(VideoActivity.this,new String[]{
               permission.WRITE_EXTERNAL_STORAGE
            },1);
        }else{
            initVideoPath();
        }
    }
    private void initVideoPath() {
        File file = new File(Environment.getExternalStorageDirectory(),"movie.mp4");
        videoView.setVideoPath(file.getPath());
    }
    private void init(){
        button1 = (Button)findViewById(R.id.play);
        button2 = (Button)findViewById(R.id.pause);
        button3 = (Button)findViewById(R.id.replay);
        videoView = (VideoView)findViewById(R.id.video_view);
    }
//    @Override
//    public void onRequestpermissionsRresult(){
//
//    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play:
                if(!videoView.isPlaying()){
                    videoView.start();//开始播放；
                }
            case R.id.pause:
                if(videoView.isPlaying()){
                    videoView.pause();
                }
            case R.id.replay:
                if(videoView.isPlaying()){
                    videoView.resume();//开始播放；
                }
                break;
        }
    }
    protected  void onDestory(){
        super.onDestroy();
        if(videoView!=null){
            videoView.suspend();
        }
    }

}
