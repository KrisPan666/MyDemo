package com.hzy.mybook.viewPage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.hzy.mybook.R;
import com.hzy.mybook.controllor.ActivityCollector;
import com.hzy.mybook.ui.LoginActivity;
import com.hzy.mybook.ui.MyBookActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager 引导
 * create at 2018-1-16 下午4:24:29
 * 暂时不用2、3两个引导页
 */
public class ViewPagerActivity extends AppCompatActivity {
	private ViewPager mVPActivity;
	private Fragment1 mFragment1;
//	private Fragment2 mFragment2;
//	private Fragment3 mFragment3;
	private Fragment4 mFragment4;
	private List<Fragment> mListFragment = new ArrayList<Fragment>();
	private PagerAdapter mPgAdapter;

//	UserManager userManager ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		String str = readFile();//读sd卡文件
//		if(userManager == null){
//			userManager = UserManager.instance() ;
//		}
		//判断是否登录过，yes表示已经安装登陆过，no表示未登录。
		if(str != null && "no".equals(str)){
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
		}else if(str != null && "yes".equals(str)){
            Intent intent = new Intent(getApplicationContext(), MyBookActivity.class);
            startActivity(intent);
        }else{
			setContentView(R.layout.activity_viewpager);
			initView();
		}
	}

	//读取文件，用于判断是否显示引导页；若读取的文件为yes，则不用显示引导页；
    private String readFile() {
		String strFile = null ;
		String rootDir = null ;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			rootDir = Environment.getExternalStorageDirectory() + "/isLogin.txt";
		} else {
			rootDir = "/mnt/sdcard/isLogin.txt";
		}// mnt/sdcard
		File file = new File(rootDir);

		//判断文件是否存在，不存在就创建；
		if (!file.exists()){
		try {
			//创建文件
			file.createNewFile();
			//给一个吐司提示，显示创建成功
//            Toast.makeText(getApplicationContext(), "文件创建成功", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
        // read
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        try {
            strFile = br.readLine();
            fr.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return strFile ;
    }

    private void initView() {
		mVPActivity = (ViewPager) findViewById(R.id.vp_activity);
		mFragment1 = new Fragment1();
//		mFragment2 = new Fragment2();
//		mFragment3 = new Fragment3();
		mFragment4 = new Fragment4();
		mListFragment.add(mFragment1);
//		mListFragment.add(mFragment2);
//		mListFragment.add(mFragment3);
		mListFragment.add(mFragment4);
		mPgAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
				mListFragment);
		mVPActivity.setAdapter(mPgAdapter);
	}
}
