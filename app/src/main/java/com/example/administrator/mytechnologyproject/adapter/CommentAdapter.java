package com.example.administrator.mytechnologyproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.Util.ImageLoader;
import com.example.administrator.mytechnologyproject.base.MyBaseAdapter;
import com.example.administrator.mytechnologyproject.model.Comment;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class CommentAdapter extends MyBaseAdapter<Comment> {


    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyview(int i, View view, ViewGroup viewGroup) {
        viewHolder vh;//优化
        if (view == null) {
            view = layoutInflater.inflate(R.layout.layout_content_comment, null);
            vh = new viewHolder();
            vh.imageView_head = (ImageView) view.findViewById(R.id.imageView_head);
            vh.text_title = (TextView) view.findViewById(R.id.text_title);
            vh.text_time = (TextView) view.findViewById(R.id.text_time);
            vh.text_content = (TextView) view.findViewById(R.id.text_content);
            view.setTag(vh);
        } else {
            vh = (viewHolder) view.getTag();
        }

        vh.text_content.setText(getItem(i).getContent());
        vh.text_time.setText(getItem(i).getStamp());
        vh.text_title.setText(getItem(i).getUid());
        new ImageLoader(context).dispaly(getItem(i).getPortrait(), vh.imageView_head);
        return view;
    }

    class viewHolder {
        ImageView imageView_head;
        TextView text_title, text_content, text_time;
    }
}
