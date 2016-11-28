package com.example.administrator.mytechnologyproject.model.parser;

import android.content.Context;

import com.example.administrator.mytechnologyproject.model.BaseEntry;
import com.example.administrator.mytechnologyproject.model.Register;
import com.example.administrator.mytechnologyproject.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class ParserUser {

    /**
     * 解析用户注册信息
     * @param json
     * @return
     */
    public static BaseEntry<Register> getRegisterInfo(String json) {
        Gson gson = new Gson();
        BaseEntry<Register> registerBaseEntry = gson.fromJson(json,new
                TypeToken<BaseEntry<Register>>() {

        }.getType());
        return registerBaseEntry;
    }


    /**
     * 解析登录成功的用户数据
     * @param json
     * @return
     */
    public static BaseEntry<User> getLoginsuccInfo(String json) {
        Gson gson = new Gson();
       BaseEntry<User> userBaseEntry = gson.fromJson(json,new TypeToken<BaseEntry<User>>(){

       }.getType());
        return userBaseEntry;
    }

}
