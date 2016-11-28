package com.example.administrator.mytechnologyproject.model.parser;

import android.content.Context;

import com.example.administrator.mytechnologyproject.model.BaseEntry;
import com.example.administrator.mytechnologyproject.model.News;
import com.example.administrator.mytechnologyproject.model.NewsType;
import com.example.administrator.mytechnologyproject.model.Publish;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class ParserNews {

    //获得新闻类型
    public static List<NewsType> getNewsType(String json) {
        Gson gson = new Gson();
        BaseEntry<List<NewsType>> list = gson.fromJson(json,new TypeToken<BaseEntry<List<NewsType>>>() {

        }.getType());
        return list.getData();
    }
    //获得新闻内容
    public static List<News> getNews(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEntry<List<News>>>(){
        }.getType();
        BaseEntry<List<News>> entry = gson.fromJson(json,type);
        return entry.getData();
    }
    //评论后的内容
    public static int getFollow(String json) {
        Gson gson = new Gson();
        BaseEntry<Integer> baseEntry = gson.fromJson(json,new TypeToken<BaseEntry<Integer>>(){

        }.getType());
        int data = baseEntry.getData();
        return data;
    }

    public static String getPublish(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEntry<Publish>>(){}.getType();
        BaseEntry<Publish> entry = gson.fromJson(json,type);
        String res = entry.getData().getExplain();
        return res;
    }
}