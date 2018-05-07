package com.hzy.mybook.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hzy.mybook.R;
import com.hzy.mybook.controllor.ActivityCollector;
import com.hzy.mybook.view.CornerListView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 系统设置
 * author：phy
 * date: 2018/02/02
 */
public class SettingActivity extends Activity implements OnItemClickListener {
    // List包含List包含Map
    public List<List<Map<String, String>>> totalListDatas = new ArrayList<List<Map<String, String>>>();
    // 每个分项的页面View
    public LinearLayout cornerContainer = null;
    // 每个item的数据，为字符串的空表示一个分隔
    private String[] mSettingItems = {"我的图书","离线下载", "", "分享该软件给朋友", "评分", "", "检查新版本", "意见反馈", "电话联系我们", "关于", "", "捐赠"};
    // 对应每个item调用的方法名称：通过反射实现方法调用
    private String[] mSettingItemMethods = {"my_book","setting_offline", "", "shareApp", "jmupToMarket", "", "setting_check_new_version",
            "feedBackSuggestion", "callUs", "about", "", "setting_donate"};
    // 显示名称对应方法名称
    private HashMap<String, String> mSettingItemMethodMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.setting);
        loadDatas();
        initView();
    }

    /*
     * 按照每个分隔加载数据
     */
    private void loadDatas() {
        totalListDatas.clear();
        List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
        for (int i = 0; i < mSettingItems.length; i++) {
            if ("".equals(mSettingItems[i])) {
                // 遇到字符串的空，就加入一个空的ArrayList
                totalListDatas.add(listData);
                listData = new ArrayList<Map<String, String>>();
            } else {
                Map<String, String> map = new HashMap<String, String>();
                map.put("text", mSettingItems[i]);
                listData.add(map);
            }
        }
        totalListDatas.add(listData);
    }

    // 加载页面，设置数据
    private void initView() {
        // 获取每个分项的页面View
        cornerContainer = (LinearLayout) findViewById(R.id.setting);
        // 把每个item的名称及其对应的方法名称加入集合
        for (int i = 0; i < mSettingItems.length; i++) {
            mSettingItemMethodMap.put(mSettingItems[i], mSettingItemMethods[i]);
        }
        // 每个分隔List的总数
        int size = totalListDatas.size();
        // 每个分隔List就是一个圆角ListView
        CornerListView cornerListView;
        // 布局属性
        LayoutParams layoutParams;
        SimpleAdapter adapter;
        for (int i = 0; i < size; i++) {
            cornerListView = new CornerListView(this);
            layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            // 设置不同的外边距
            if (i == 0 && i == (size - 1)) {
                layoutParams.setMargins(8, 8, 8, 8);
            } else if (i == 0) {
                layoutParams.setMargins(8, 8, 8, 4);
            } else if (i == (size - 1)) {
                layoutParams.setMargins(8, 4, 8, 8);
            } else {
                layoutParams.setMargins(8, 4, 8, 4);
            }
            cornerListView.setLayoutParams(layoutParams);
            /*
			 * 滚动时，列表里面的view重绘时，用的依旧是系统默认的透明色，颜色值为#FF191919， 要改变这种情况， 只需要调用listView的setCacheColorHint(0)，颜色值设置为0 或者xml文件中listView的属性
			 * Android:cacheColorHint="#00000000"即可，
			 */
            cornerListView.setCacheColorHint(0);
            // 设置分割线
            cornerListView.setDivider(getResources().getDrawable(R.drawable.app_divider_h_gray));
            // 隐藏ListView滚动条
            cornerListView.setScrollbarFadingEnabled(true);
            // 把每个ListView添加至LinearLayout
            cornerContainer.addView(cornerListView);
            // 设置适配器
            adapter = new SimpleAdapter(getApplicationContext(), totalListDatas.get(i), R.layout.setting_item, new String[]{"text"},
                    new int[]{R.id.setting_list_item_text});
            cornerListView.setAdapter(adapter);
            // 加入item点击的监听
            cornerListView.setOnItemClickListener(this);
            // 设置每个间隔ListView的高度=ListDatas的size+1
            int height = totalListDatas.get(i).size() * (int) getResources().getDimension(R.dimen.setting_item_height) + 1;
            cornerListView.getLayoutParams().height = height;
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // 获取文本值
        TextView textView = (TextView) view.findViewById(R.id.setting_list_item_text);
        String text = textView.getText().toString();
        // 反射调用对应方法
        Class<? extends SettingActivity> clazz = this.getClass();
        try {
            Method method = clazz.getMethod(mSettingItemMethodMap.get(text));
            method.invoke(SettingActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void my_book() {
        dialogShow("我的图书!");
    }

    public void setting_offline() {
        dialogShow("离线下载!");
    }

    public void shareApp() {
        dialogShow("分享该软件给朋友!");
    }

    public void jmupToMarket() {
        dialogShow("评分!");
    }

    public void setting_check_new_version() {
        dialogShow("您好，目前没有新版本!");
    }

    public void feedBackSuggestion() {
        dialogShow("意见反馈!");
    }

    public void callUs() {
        dialogShow("联系我们!");
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_DIAL);
//        Uri uri = Uri.parse("tel:" + "13156598796");
//        intent.setData(uri);
//        startActivity(intent);
    }

    public void about() {
        dialogShow("关于!");
    }

    public void setting_donate() {
        dialogShow("捐赠!");
    }

    private void dialogShow(final String message) {
        new AlertDialog.Builder(this).setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(message.equals("联系我们!")){
                    Toast.makeText(getApplication(),"跳转到拨号盘！",Toast.LENGTH_SHORT).show();
                }
                else if(message.equals("捐赠!")){

                }
            }
        }).create().show();
    }

}
