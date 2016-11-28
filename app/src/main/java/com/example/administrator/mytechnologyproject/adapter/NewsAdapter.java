package com.example.administrator.mytechnologyproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.Util.ImageLoader;
import com.example.administrator.mytechnologyproject.base.MyBaseAdapter;
import com.example.administrator.mytechnologyproject.model.News;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class NewsAdapter extends MyBaseAdapter<News> {
    public NewsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyview(int i, View view, ViewGroup viewGroup) {
        ViewHolde vh;
        if(view == null) {
            vh = new ViewHolde();
            view = layoutInflater.inflate(R.layout.layout_news_item,null);
            vh.iv_image_news = (ImageView) view.findViewById(R.id.iv_image_news);
            vh.tv_news_title = (TextView) view.findViewById(R.id.tv_news_title);
            vh.tv_news_content = (TextView) view.findViewById(R.id.tv_news_content);
            vh.tv_news_time = (TextView) view.findViewById(R.id.tv_news_time);
            view.setTag(vh);
        }else {
            vh = (ViewHolde) view.getTag();
        }
        vh.tv_news_title.setText(getItem(i).getTitle());
        vh.tv_news_content.setText(getItem(i).getSummary());
        vh.tv_news_time.setText(getItem(i).getStamp());
        new ImageLoader(context).dispaly(getItem(i).getIcon(),vh.iv_image_news);

        return view;
    }

    class ViewHolde {
        ImageView iv_image_news;
        TextView tv_news_title,tv_news_content,tv_news_time;
    }
}
