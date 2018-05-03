package com.jiecheng.zhike.mydemo.httpJson.util;

import com.google.gson.Gson;
import com.jiecheng.zhike.mydemo.httpJson.model.Bank;
import com.jiecheng.zhike.mydemo.httpJson.model.Resp;

import java.util.List;

/**
 * Created by 13159 on 2017/9/1.
 */

public class GsonController {
    private Resp res;
    private List<Bank> bank;
//    List<Bank> list1;
    public List<Bank> parseJSONWithGSON(String responseData){
        Gson gson = new Gson();
        Resp resp = gson.fromJson(responseData,Resp.class);
        return resp.getData();
    }
//    public List<Bank> parseJSONWithGSON2(String responseData2){
//        Gson gson = new Gson();
//        List<Bank> list = gson.fromJson(responseData2,new TypeToken<List<Bank>>(){}.getType());
//        return list;
//    }
}
