package com.example.administrator.mytechnologyproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.Util.ImageLoader;
import com.example.administrator.mytechnologyproject.base.MyBaseAdapter;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class GridViewAdadpter extends MyBaseAdapter<String>{

    public GridViewAdadpter(Context context) {
        super(context);
    }

    @Override
    public View getMyview(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view == null) {
        view = layoutInflater.inflate(R.layout.layout_gridview_item,null);
            vh = new ViewHolder();
            vh.imageView_gridview = (ImageView) view.findViewById(R.id.imageView_gridview);
            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }
        new ImageLoader(context).dispaly(getItem(i),vh.imageView_gridview);
        return view;
    }

    class ViewHolder{
        ImageView imageView_gridview;
    }
}
