package com.example.administrator.mytechnologyproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.Util.dbutil.DBTools;
import com.example.administrator.mytechnologyproject.activity.HomeActivity;
import com.example.administrator.mytechnologyproject.activity.NewsShowActivity;
import com.example.administrator.mytechnologyproject.adapter.GridViewAdadpter;
import com.example.administrator.mytechnologyproject.model.News;

import java.util.ArrayList;
import java.util.List;

public class Image_Fragment extends Fragment {
    private List<News> newslist;
    private List<String> imagelist;
    private GridView gridview;
    private GridViewAdadpter adadpter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_, container, false);
        gridview = (GridView) view.findViewById(R.id.gridview);
        adadpter = new GridViewAdadpter(getContext());
        gridview.setAdapter(adadpter);

        gridview.setOnItemClickListener(onitemClickLister);
        loadData();
        return view;
    }

    private void loadData() {
        DBTools dbTools = new DBTools(getContext());
        newslist = dbTools.getLocalNews();
        imagelist = new ArrayList<>();
        for (int i = 0; i < newslist.size(); i++) {
            imagelist.add(newslist.get(i).getIcon());
        }
        if(imagelist == null || imagelist.size() <= 0) {
            ((HomeActivity)getActivity()).showToast("当前没有图片");
        }else {
            adadpter.appendDataed(imagelist,true);
            adadpter.updateAdapter();
        }
    }


    private AdapterView.OnItemClickListener onitemClickLister = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
            Bundle bundle = new Bundle();
            News news = newslist.get(i);
            bundle.putSerializable("news",news);
            ((HomeActivity)getActivity()).openActivity(NewsShowActivity.class,bundle);
        }
    };


}
