package com.example.administrator.mytechnologyproject.Util.dbutil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.mytechnologyproject.model.News;
import com.example.administrator.mytechnologyproject.model.SubType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class DBTools {
    private static final String TAG = "DBTools";
    private Context context;
    private DBManager dbManager;
    private SQLiteDatabase sd;

    public DBTools(Context context) {
        this.context = context;
        dbManager = new DBManager(context);
    }

    /**
     * 收藏
     * @param news
     * @return
     */
    public boolean saveLocalFavorite(News news) {
        sd = dbManager.getReadableDatabase();
        String sql = "select nid from " + DBManager.NEWSFAVORITE_NAME + " where nid = ?";
        Log.i(TAG, "saveLocalFavorite: =================="+sql);
        Cursor c = sd.rawQuery(sql,new String[]{news.getNid() + ""});
        if(c.moveToNext()) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("type",news.getType());
        contentValues.put("nid",news.getNid());
        contentValues.put("summary",news.getSummary());
        contentValues.put("icon",news.getIcon());
        contentValues.put("stamp",news.getStamp());
        contentValues.put("title",news.getTitle());
        contentValues.put("link",news.getLink());
        sd.insert(DBManager.NEWSFAVORITE_NAME,null,contentValues);
        return true;
    }

    public List<News> getLocalFavorite() {
        List<News> listNews = new ArrayList<>();
        sd = dbManager.getReadableDatabase();
        String sql = "select * from " + DBManager.NEWSFAVORITE_NAME;
        Cursor c = sd.rawQuery(sql,null);
        if (c.moveToFirst()) {
            do {
                int type =  c.getInt(c.getColumnIndex("type"));
                int nid =  c.getInt(c.getColumnIndex("nid"));
                String summary = c.getString(c.getColumnIndex("summary"));
                String icon = c.getString(c.getColumnIndex("icon"));
                String stamp = c.getString(c.getColumnIndex("stamp"));
                String title = c.getString(c.getColumnIndex("title"));
                String link = c.getString(c.getColumnIndex("link"));
                News news = new News(summary,icon,stamp,title,nid,link,type);
                listNews.add(news);
            } while (c.moveToNext());
            c.close();
            sd.close();
        }
        return listNews;
    }

    /**
     * 新闻首页
     * @param news
     * @return
     */
    public boolean saveLocalNews(News news) {
        sd = dbManager.getReadableDatabase();
        String sql = "select nid from " + DBManager.NEWS_NAME + " where nid = ?";
        Log.i(TAG, "saveLocalFavorite: =================="+sql);
        Cursor c = sd.rawQuery(sql,new String[]{news.getNid() + ""});
        if(c.moveToNext()) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("type",news.getType());
        contentValues.put("nid",news.getNid());
        contentValues.put("summary",news.getSummary());
        contentValues.put("icon",news.getIcon());
        contentValues.put("stamp",news.getStamp());
        contentValues.put("title",news.getTitle());
        contentValues.put("link",news.getLink());
        sd.insert(DBManager.NEWS_NAME,null,contentValues);
        return true;
    }

    public List<News> getLocalNews() {
        List<News> listNews = new ArrayList<>();
        sd = dbManager.getReadableDatabase();
        String sql = "select * from " + DBManager.NEWS_NAME;
        Cursor c = sd.rawQuery(sql,null);
        if (c.moveToFirst()) {
            do {
                int type =  c.getInt(c.getColumnIndex("type"));
                int nid =  c.getInt(c.getColumnIndex("nid"));
                String summary = c.getString(c.getColumnIndex("summary"));
                String icon = c.getString(c.getColumnIndex("icon"));
                String stamp = c.getString(c.getColumnIndex("stamp"));
                String title = c.getString(c.getColumnIndex("title"));
                String link = c.getString(c.getColumnIndex("link"));
                News news = new News(summary,icon,stamp,title,nid,link,type);
                listNews.add(news);
            } while (c.moveToNext());
            c.close();
            sd.close();
        }
        return listNews;
    }

    /**
     * 判断是否缓存
     */
    public boolean saveLocalType(SubType subType) {
        sd = dbManager.getReadableDatabase();
        String sql = "select subid from " + DBManager.NEWSTYPE_NAME + " where subid = ?";
        Log.i(TAG, "saveLocalFavorite: =================="+sql);
        Cursor c = sd.rawQuery(sql,new String[]{subType.getSubid() + ""});
        if(c.moveToNext()) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("subid",subType.getSubid());
        contentValues.put("subgroup",subType.getSubgroup());
        sd.insert(DBManager.NEWSTYPE_NAME,null,contentValues);
        return true;
    }

    /**
     *查找新闻标题的内容
     * @return
     */
    public List<SubType> getLocalType() {
        List<SubType> listNews = new ArrayList<SubType>();
        sd = dbManager.getReadableDatabase();
        String sql = "select * from " + DBManager.NEWSTYPE_NAME;
        Cursor c = sd.rawQuery(sql,null);
        if (c.moveToFirst()) {
            do {
                int subid =  c.getInt(c.getColumnIndex("subid"));
                String subgroup = c.getString(c.getColumnIndex("subgroup"));
                SubType subType = new SubType(subgroup,subid);
                listNews.add(subType);
            } while (c.moveToNext());
            c.close();
            sd.close();
        }
        return listNews;
    }

}
