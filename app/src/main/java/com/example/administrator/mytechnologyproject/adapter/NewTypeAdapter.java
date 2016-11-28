package com.example.administrator.mytechnologyproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.base.MyBaseAdapter;
import com.example.administrator.mytechnologyproject.model.SubType;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class NewTypeAdapter extends MyBaseAdapter<SubType> {
    public NewTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyview(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;//优化器
        if(view == null) {
            view = layoutInflater.inflate(R.layout.layout_newtype_item, null);
            vh = new ViewHolder();
            vh.tv_newtype_item = (TextView) view.findViewById(R.id.tv_newtype_item);
            view.setTag(vh);
        }else {
           vh = (ViewHolder) view.getTag();
        }

       String subgroup = mylist.get(i).getSubgroup();
        vh.tv_newtype_item.setText(subgroup);
        return view;
    }

    class ViewHolder {
        TextView tv_newtype_item;
    }
}
