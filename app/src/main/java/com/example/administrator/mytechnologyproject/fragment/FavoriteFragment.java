package com.example.administrator.mytechnologyproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.Util.dbutil.DBTools;
import com.example.administrator.mytechnologyproject.activity.HomeActivity;
import com.example.administrator.mytechnologyproject.activity.NewsShowActivity;
import com.example.administrator.mytechnologyproject.adapter.NewsAdapter;
import com.example.administrator.mytechnologyproject.model.News;

import java.util.List;


public class FavoriteFragment extends Fragment {
    private ListView listView;
    private NewsAdapter adapter;
    private DBTools dbTools;
    private TextView tv_nofavorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        tv_nofavorite = (TextView) view.findViewById(R.id.tv_nofavorite);
        adapter = new NewsAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemclick);
        dbTools = new DBTools(getContext());
        getData();
        return view;
    }


    public void getData() {
        List<News> list = dbTools.getLocalFavorite();
        if(list.size() != 0) {
            adapter.appendDataed(list,true);
            adapter.updateAdapter();
        }else {
            listView.setVisibility(View.GONE);
            tv_nofavorite.setVisibility(View.VISIBLE);
        }

    }

    private AdapterView.OnItemClickListener onItemclick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle(position);
            News news = adapter.getItem(position);
            bundle.putSerializable("news",news);
            ((HomeActivity) getActivity()).openActivity(NewsShowActivity.class,bundle);
        }
    };
}
