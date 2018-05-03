package com.jiecheng.zhike.mydemo.httpJson.util;

import com.jiecheng.zhike.mydemo.httpJson.model.Bank;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13159 on 2017/8/9.
 */

public class JsonController {
    int code;
    Bank bank;
    //json解析；
    public List<Bank> getjson(String json){
        List<Bank> list = new ArrayList<Bank>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            if(jsonObject!=null){
                code = jsonObject.optInt("code");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    bank = new Bank();
                    bank.setBankid(jsonObject2.optString("bankid"));
                    bank.setBankname(jsonObject2.optString("bankname"));
                    bank.setClassname(jsonObject2.optString("classname"));
                    bank.setCyuserid(jsonObject2.optString("cyuserid"));
                    bank.setDatecreated(jsonObject2.optString("datecreated"));
                    bank.setUserid(jsonObject2.optString("userid"));
                    bank.setUsername(jsonObject2.optString("username"));
                    bank.setVersion(jsonObject2.optString("version"));
                    bank.setWorkid(jsonObject2.optString("workid"));
                    bank.setWorkname(jsonObject2.optString("workname"));
                    bank.setWtype(jsonObject2.optString("wtype"));
                    bank.setQtype(jsonObject2.optString("qtype"));
                    bank.setSchoolid(jsonObject2.optString("schoolid"));
                    bank.setClassid(jsonObject2.optString("classid"));
                    bank.setClasstype(jsonObject2.optString("classtype"));
                    bank.setCyclassname(jsonObject2.optString("cyclassname"));
                    bank.setPic_url(jsonObject2.optString("pic_url"));
                    bank.setService_mac(jsonObject2.optString("service_mac"));
                    list.add(bank);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
