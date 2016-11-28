package com.example.administrator.mytechnologyproject.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter基类
 * Created by Administrator on 2016/9/6 0006.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter{
    protected Context context;
    protected LayoutInflater layoutInflater;
    protected List<T> mylist = new ArrayList<T>();

    public MyBaseAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     *  获得Adapter的所有数据
     * @return
     */
    public List<T> getAdapter() {
        return mylist;
    }

    /**
     * 封装一个添加一条数据的方法
     * @param t
     * 添加的那条数据,也可能是对象
     * @param isClearold
     * 是否要清理旧的数据
     */
    public void appendDataed(T t,boolean isClearold) {
        if(t == null)
            return;
        if(isClearold)
            mylist.clear();
        mylist.add(t);
    }

    /**
     * 封装一个添加条数据的方法
     * @param t
     * 添加的多条数据
     * @param isClearold
     * 是否要清理旧的数据
     */
    public void appendDataed(List<T> t,boolean isClearold) {
        if(t == null)
            return;
        if(isClearold)
            mylist.clear();
        mylist.addAll(t);
    }

    /**
     * 封装一个往数据顶头添加一条数据
     * @param t
     * @param isClearold
     */
    public void appendDataTop(T t,boolean isClearold) {
        if(t == null)
            return;
        if(isClearold)
            mylist.clear();
        mylist.add(0,t);
    }

    /**
     * 封装一个网数据顶头添加多条数据
     * @param t
     * @param isClearold
     */
    public void appendDataTop(List<T> t,boolean isClearold) {
        if(t == null)
            return;
        if(isClearold)
            mylist.clear();
        mylist.addAll(0,t);
    }

    public void isClear() {
        mylist.clear();
    }

    public void updateAdapter() {
        this.notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        if(mylist != null)
            return mylist.size();
        return 0;
    }

    @Override
    public T getItem(int i) {
        if(mylist == null || mylist.size() < 0)
            return null;
        return mylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return getMyview(i,view,viewGroup);
    }

    public abstract View getMyview(int i, View view, ViewGroup viewGroup);
}
