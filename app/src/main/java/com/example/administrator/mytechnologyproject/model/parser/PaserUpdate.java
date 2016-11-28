package com.example.administrator.mytechnologyproject.model.parser;

import com.example.administrator.mytechnologyproject.model.BaseEntry;
import com.example.administrator.mytechnologyproject.model.Updata;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class PaserUpdate {

   public static BaseEntry<Updata> getUpdate (String json) {
       Gson gson = new Gson();
       Type type = new TypeToken<BaseEntry<Updata>>(){
       }.getType();
       BaseEntry<Updata> updataentry = gson.fromJson(json,type);
       return updataentry;
   }
}
