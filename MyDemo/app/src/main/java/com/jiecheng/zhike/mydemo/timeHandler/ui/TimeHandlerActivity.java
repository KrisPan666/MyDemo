package com.jiecheng.zhike.mydemo.timeHandler.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.comm.BaseActivity;

public class TimeHandlerActivity extends BaseActivity implements View.OnClickListener {
    public static final int START_TIME = 1;
    private int tag = 0;//秒个位初始状态
    private int time = 60 * 60 * 60 ;

    private Button timeStart_btn1;
    private TextView sTv_time2;
    private TextView sTv_time1;
    private TextView mTv_time2;
    private TextView mTv_time1;
    private TextView hTv_time2;
    private TextView hTv_time1;
    private TextView mTv_test;
    private Handler hd = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case START_TIME:
//                    while (time > 0) {
//                        try{
//                            Thread.sleep(1000);
//                            time--;
//                            int hh = time / 60 / 60 % 60;
//                            int mm = time / 60 % 60;
//                            int ss = time % 60;
//                            Log.d("倒计时：","还剩" + hh + "小时" + mm + "分钟" + ss + "秒");
//                            hTv_time1.setText(Integer.toString(hh));
//                            mTv_time1.setText(Integer.toString(mm));
//                            sTv_time1.setText(Integer.toString(ss));
////                        mTv_time1.setText("还剩：" + hh + "小时" + mm + "分钟" + ss + "秒");
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                    }

                    int h = 0;//小时
                    int d = 0;//分钟
                    int s = 0;//秒
                    int temp = (tag++)%3600;
                    if(tag<=3600){
                        d = tag/60;
                        mTv_time1.setText(Integer.toString(d/10));
                        mTv_time2.setText(Integer.toString(d%10));
                        if(tag%60!=0){
                            s = tag%60;
                            sTv_time1.setText(Integer.toString(s/10));
                            sTv_time2.setText(Integer.toString(s%10));
                        }
                        if(tag%60==0){
                            sTv_time1.setText(Integer.toString(0));
                            sTv_time2.setText(Integer.toString(0));
                        }
                    }else{
                        h= tag/3600;
                        if(temp!=0){
                            if(temp>60){
                                d = temp/60;
                                mTv_time1.setText(Integer.toString(d/10));
                                mTv_time2.setText(Integer.toString(d%10));
                                if(temp%60!=0){
                                    s = temp%60;
                                    sTv_time1.setText(Integer.toString(s/10));
                                    sTv_time2.setText(Integer.toString(s%10));
                                }
                            }else{
                                s = temp;
                                sTv_time1.setText(Integer.toString(s/10));
                                sTv_time2.setText(Integer.toString(s%10));
                            }
                        }
                    }


//                    hd.sendEmptyMessageDelayed(START_TIME, 1000);


                    //                    long current = System.currentTimeMillis();
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
//                    String time = dateFormat.format(current);
//                    timeStart_tv.setText(time);


//                    sTv_time2.setText(Integer.toString((s2++)%10));
//                    sTv_time1.setText(Integer.toString((s2-1)/10));
//                    if(s2>=60){
//                        sTv_time1.setText("0");
//                        if(s2/60==0){
////                        sTv_time1.setText(Integer.toString((s2-1)/10));
//
//                        }
//                        else if(s2>60){
//                            mTv_time2.setText(Integer.toString(s2/60));
//                        }
//                    }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timehandler_show_layout);
        timeStart_btn1 = (Button)findViewById(R.id.start1);
        sTv_time2 = (TextView)findViewById(R.id.s_time2);
        sTv_time1 = (TextView)findViewById(R.id.s_time1);
        mTv_time2 = (TextView)findViewById(R.id.m_time2);
        mTv_time1 = (TextView)findViewById(R.id.m_time1);
        hTv_time2 = (TextView)findViewById(R.id.h_time2);
        hTv_time1 = (TextView)findViewById(R.id.h_time1);
        mTv_test = (TextView)findViewById(R.id.str);
        int hh = time / 60 / 60 % 60;
        int mm = time / 60 % 60;
        int ss = time % 60;
        hTv_time1.setText(Integer.toString(hh));
        mTv_time1.setText(Integer.toString(mm));
        sTv_time1.setText(Integer.toString(ss));
        timeStart_btn1.setOnClickListener(this);
    }
    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.start1:
                String Str ="<img alt=\\\"\\\" src=\\\"http:\\/\\/test.itembank.cycore.cn\\/img\\/question\\/addQuestion\\/8\\/4c9abce9-f7e2-44f4-87ed-f2cb04b352ff.png\\\" style=\\\"vertical-align:middle;FLOAT:right;\\\" \\/>鏌愬悓瀛﹀仛鈥滄帰绌跺脊鍔涗笌寮圭哀浼搁暱閲忕殑鍏崇郴鈥濆疄楠岋紝杩涜\uE511浜嗗\uE6E7涓嬫搷浣滐細\\n<br \\/>锛?锛夊皢寮圭哀鎮\uE101寕鍦ㄩ搧鏋跺彴涓婏紝灏嗗埢搴﹀昂鍥哄畾鍦ㄥ脊绨т竴渚э紝寮圭哀杞寸嚎鍜屽埢搴﹀昂閮藉湪绔栫洿鏂瑰悜锛沑n<br \\/>锛?锛夊脊绨ц嚜鐒舵偓鎸傦紝寰呭脊绨ч潤姝㈡椂娴嬪嚭鍏堕暱搴﹁\uE187涓篖<sub>0<\\/sub>锛庡脊绨т笅绔\uE21B寕涓婄牆鐮佺洏鏃舵祴鍑哄叾闀垮害璁颁负L<sub>x<\\/sub>锛庡湪鐮濈爜鐩樹腑姣忔\uE0BC澧炲姞10g鐮濈爜锛屽垎鍒\uE0A3祴鍑哄脊绨ч暱搴︿緷娆¤\uE187涓篖<sub>1<\\/sub>銆丩<sub>2<\\/sub>銆丩<sub>3<\\/sub>銆丩<sub>4<\\/sub>銆丩<sub>5<\\/sub>銆丩<sub>6<\\/sub>锛屾墍寰楁祴閲忔暟鎹\uE1BC\uE6E7琛\uE7D2細\\n<br \\/>\\n<table class=\\\"edittable\\\" border=\\\"1px solid\\\"> \\n <tbody> \\n  <tr> \\n   <td width=\\\"79\\\">浠ｈ〃绗﹀彿<\\/td> \\n   <td width=\\\"52\\\">L<sub>0<\\/sub><\\/td> \\n   <td width=\\\"54\\\">L<sub>x<\\/sub><\\/td> \\n   <td width=\\\"47\\\">L<sub>1<\\/sub><\\/td> \\n   <td width=\\\"56\\\">L<sub>2<\\/sub><\\/td> \\n   <td width=\\\"56\\\">L<sub>3<\\/sub><\\/td> \\n   <td width=\\\"56\\\">L<sub>4<\\/sub><\\/td> \\n   <td width=\\\"56\\\">L<sub>5<\\/sub><\\/td> \\n   <td width=\\\"56\\\">L<sub>6<\\/sub><\\/td> \\n  <\\/tr> \\n  <tr> \\n   <td>鏁板€锛坈m锛?\\/td> \\n   <td>25.35<\\/td> \\n   <td>27.35<\\/td> \\n   <td>29.35<\\/td> \\n   <td>31.30<\\/td> \\n   <td>33.4<\\/td> \\n   <td>35.35<\\/td> \\n   <td>37.40<\\/td> \\n   <td>39.30<\\/td> \\n  <\\/tr> \\n <\\/tbody>\\n<\\/table>琛ㄤ腑鏈変竴涓\uE045暟鎹\uE1BF\uE187褰曚笉瑙勮寖锛屼唬琛ㄧ\uE0C1鍙蜂负\\n<!--BA-->______\\n<!--EA-->锛庣敱琛ㄥ彲鐭ユ墍鐢ㄥ埢搴﹀昂鐨勬渶灏忓垎搴︿负\\n<!--BA-->______\\n<!--EA-->锛嶾n<br \\/>锛?锛夊\uE6E7鍥炬槸璇ュ悓瀛︽牴鎹\uE1BF〃涓\uE15F暟鎹\uE1BB綔鐨勫浘锛岀旱杞存槸鐮濈爜鐨勮川閲忥紝妯\uE047酱鏄\uE21A脊绨ч暱搴︿笌L<sub>x<\\/sub>鐨勫樊鍊硷紟鐢卞浘鍙\uE21B眰寰楀脊绨х殑鍔插害绯绘暟涓篭n<!--BA-->______\\n<!--EA-->N\\/m锛涘彇閲嶅姏鍔犻€搴\uE6CD=10m\\/s<sup>2<\\/sup>锛岄€杩囧浘鍜岃〃鍙\uE21B眰寰楃牆鐮佺洏鐨勮川閲忎负\\n<!--BA-->______\\n<!--EA-->g锛?,\"gnum\":1,\"id\":0,\"maxQuesOrder\":1,\"optionscount\":4,\"order\":4,\"qid\":\"afa8400b-149c-4fc5-9341-f300698666f1\",\"qtype\":\"blankfilling\",\"questionName\":\"%E5%A1%AB%E7%A9%BA%E9%A2%98\",\"score\":14,\"workid\":\"4a92b2bf-d907-4337-9be5-dc89ba3fbf34\"},{\"analysis\":\"<div>瑙ｏ細灏忕悆鍋氬垵閫熷害涓洪浂鐨勫寑鍔犻€鐩寸嚎杩愬姩锛屾牴鎹畍=at鐭ワ紝閫熷害涔嬫瘮绛変簬杩愬姩鐨勬椂闂翠箣姣旓紝灏忕悆杩愬姩鍒?浣嶇疆銆?浣嶇疆銆?浣嶇疆銆?浣嶇疆鐨勬椂闂翠箣姣斾负1锛?锛?锛?锛屽洜涓簐<sub>2<\\/sub>=0.06m\\/s锛屽垯v<sub>3<\\/sub>=0.12m\\/s锛寁<sub>5<\\/sub>=0.24m\\/s锛?\\n<br\\/>鏁呯瓟妗堜负锛?.12锛?.24\\n<br\\/>鏍规嵁鍖€彉閫熺洿绾胯繍鍔ㄧ殑閫熷害鏃堕棿鍏\uE100紡锛岀粨鍚堟椂闂翠箣姣旀眰鍑洪€搴︿箣姣旓紝浠庤€寰楀嚭v<sub>3<\\/sub>銆乿<sub>5<\\/sub>鐨勫ぇ灏忥紟\\n<br\\/>瑙ｅ喅鏈\uE104\uE57D鐨勫叧閿\uE1BD帉鎻″寑鍙橀€鐩寸嚎杩愬姩鐨勯€搴︽椂闂村叕寮忥紝骞惰兘鐏垫椿杩愮敤锛屽熀纭€\uE57D锛?\\/div>\",\"answer\":\"0.12锛?.24\",\"desc\":\"涓€皬鐞冨湪妗岄潰涓婁粠闈欐\uE11B寮€\uE750鍋氬姞閫熻繍鍔\uE7D2紝鐜扮敤楂橀€鎽勫奖鏈哄湪鍚屼竴搴曠墖涓婂\uE63F娆℃洕鍏夛紝璁板綍涓嬪皬鐞冩瘡娆℃洕鍏夌殑浣嶇疆锛屽苟灏嗗皬鐞冪殑浣嶇疆缂栧彿锛庡\uE6E7鍥炬墍绀猴紝1浣嶇疆鎭颁负灏忕悆鍒氬紑濮嬭繍鍔ㄧ殑鐬\uE104棿锛屼綔涓洪浂鏃跺埢锛庢憚褰辨満杩炵画涓ゆ\uE0BC鏇濆厜鐨勬椂闂撮棿闅斿潎涓?.5s锛屽皬鐞冧粠1浣嶇疆鍒?浣嶇疆鐨勮繍鍔ㄨ繃绋嬩腑缁忚繃鍚勪綅缃\uE1BE殑閫熷害鍒嗗埆涓簐<sub>1<\\/sub>=0锛寁<sub>2<\\/sub>=0.06m\\/s锛寁<sub>3<\\/sub>=\\n<!--BA-->______\\n<!--EA-->m\\/s锛寁<sub>4<\\/sub>=0.18m\\/s锛寁<sub>5<\\/sub>=\\n<!--BA-->______\\n<!--EA-->m\\/s锛嶾n<br\\/><imgalt=\\\"\\\" src=\\\"http:\\/\\/test.itembank.cycore.cn\\/img\\/question\\/addQuestion\\/910\\/83b8909a-b0bd-4dc0-b159-8df8f6b15a96.png\\\" style=\\\"vertical-align:middle\\\" \\/>";
                Str = Str.replace("\\n","").replace("\\/", "/").replace("\\\"", "\"");
                //.replace("\\n","").replace("\\/", "/").replace("\\\"", "");
                //Str = Str.replace("h", "=");
//                Log.d("Str:",Str);
//                mTv_test.setText(Str);

//                hd.sendEmptyMessageDelayed(START_TIME, 1000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        while(true){
                            Message message = new Message();
                            message.what=START_TIME;
                            hd.sendMessage(message);
//                            hd.sendEmptyMessageDelayed(START_TIME, 1000);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                Looper.loop();
                break;
            default:
                break;
        }
    }
}
