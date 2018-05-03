package com.jiecheng.zhike.mydemo.Utils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.comm.BaseActivity;

public class PxAndDpActivity extends BaseActivity implements View.OnClickListener {
    private EditText pxEdittext;
    private EditText dpEdittext;
    private EditText spEditText;
    private Button pxTodp_btn;
    private Button dpTopx_btn;
    private Button pxTosp_btn;
    private Button spTopx_btn;
    private TextView result_tv;
    private float pxValue;
    private float dpValue;
    private float spValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_px_todp);
        init();
    }

    /**
     * 界面初始化；
     */
    private void init() {
        pxEdittext = (EditText)findViewById(R.id.px_text);
        dpEdittext = (EditText)findViewById(R.id.dp_text);
        spEditText = (EditText)findViewById(R.id.sp_text);

        pxTodp_btn = (Button)findViewById(R.id.px_to_dp) ;
        dpTopx_btn = (Button)findViewById(R.id.dp_to_px);
        pxTosp_btn = (Button)findViewById(R.id.px_to_sp);
        spTopx_btn = (Button)findViewById(R.id.sp_to_px);

        result_tv = (TextView)findViewById(R.id.result);

        pxTodp_btn.setOnClickListener(this);
        dpTopx_btn.setOnClickListener(this);
        pxTosp_btn.setOnClickListener(this);
        spTopx_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //px_to_dp
        if(R.id.px_to_dp == view.getId()){
            Toast.makeText(this,"px_to_dp", Toast.LENGTH_SHORT).show();
            Log.d("px_to_dp:","走这里了吗？");
            PxAndDpChangeUtil  pxAndDpChangeUtil = new PxAndDpChangeUtil();
            if(!pxEdittext.getText().toString().equals("")){
                pxValue = Float.parseFloat(pxEdittext.getText().toString());
                //根据手机的分辨率从 px(像素) 的单位 转成为 dp
                int dp = pxAndDpChangeUtil.px2dip(this,pxValue);
                result_tv.setText("px:"+pxValue+"转换成dp:"+dp);
            }else{
                return;
            }
        }
        //dp_to_px
        else if(R.id.dp_to_px == view.getId()){
            if(!dpEdittext.getText().toString().equals("")){
                PxAndDpChangeUtil pxAndDpChangeUtil = new PxAndDpChangeUtil();
                dpValue = Float.parseFloat(dpEdittext.getText().toString());
                Toast.makeText(this,"dp_to_px",Toast.LENGTH_SHORT).show();
                Log.d("dp_to_px:","走这里了吗？");
                int px = pxAndDpChangeUtil.dip2px(this,dpValue);
                result_tv.setText("dp—>px:"+px);
            }else{
                return;
            }
        }
        //px_to_sp
        else if(R.id.px_to_sp == view.getId()){
            if(!pxEdittext.getText().toString().equals("")){
                PxAndDpChangeUtil pxAndDpChangeUtil = new PxAndDpChangeUtil();
                pxValue = Float.parseFloat(pxEdittext.getText().toString());
                Toast.makeText(this,"dp_to_px",Toast.LENGTH_SHORT).show();
                Log.d("dp_to_px:","走这里了吗？");
                int sp = pxAndDpChangeUtil.px2sp(this,pxValue);
                result_tv.setText("px—>sp:"+sp);
            }else{
                return;
            }
        }
        //sp_to_px
        else if(R.id.sp_to_px == view.getId()){
            if(!spEditText.getText().toString().equals("")){
                PxAndDpChangeUtil pxAndDpChangeUtil = new PxAndDpChangeUtil();
                spValue = Float.parseFloat(spEditText.getText().toString());
                Toast.makeText(this,"dp_to_px",Toast.LENGTH_SHORT).show();
                Log.d("dp_to_px:","走这里了吗？");
                int px = pxAndDpChangeUtil.sp2px(this,spValue);
                result_tv.setText("sp—>px:"+px);
            }else{
                return;
            }
        }

    }
}
