package com.example.administrator.mytechnologyproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.Util.CommUtil;
import com.example.administrator.mytechnologyproject.Util.dbutil.DBTools;
import com.example.administrator.mytechnologyproject.activity.HomeActivity;
import com.example.administrator.mytechnologyproject.activity.NewsShowActivity;
import com.example.administrator.mytechnologyproject.adapter.NewTypeAdapter;
import com.example.administrator.mytechnologyproject.adapter.NewsAdapter;
import com.example.administrator.mytechnologyproject.gloable.API;
import com.example.administrator.mytechnologyproject.gloable.Contacts;
import com.example.administrator.mytechnologyproject.model.News;
import com.example.administrator.mytechnologyproject.model.NewsType;
import com.example.administrator.mytechnologyproject.model.SubType;
import com.example.administrator.mytechnologyproject.model.parser.ParserNews;
import com.example.administrator.mytechnologyproject.view.HorizontalListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Volley框架
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private HorizontalListView horizontalListView;
    private RequestQueue requestQueue;
    private NewTypeAdapter newTypeAdapter;
    private ListView Newslistview;
    private List<News> listnews;
    private List<SubType> listSubtype;
    private List<NewsType> listNewsType;
    private NewsAdapter newsadapter;
    private DBTools dbTools;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        horizontalListView = (HorizontalListView) view.findViewById(R.id.horizontalview);
        newTypeAdapter = new NewTypeAdapter(getContext());
        horizontalListView.setAdapter(newTypeAdapter);
        requestQueue = Volley.newRequestQueue(getContext());//实例化一个RequestQueue对象
        dbTools = new DBTools(getContext());

        Newslistview = (ListView) view.findViewById(R.id.listview);
        newsadapter = new NewsAdapter(getContext());
        Newslistview.setAdapter(newsadapter);
        Newslistview.setOnItemClickListener(onitemClickLister);


        requestQueue = Volley.newRequestQueue(getContext());

        loadTitleTypeData();
        loadHomeList();

        sendRequestData();
        return view;
    }


    private void loadTitleTypeData() {
        if (CommUtil.isNetworkAvailable(getActivity())) {
            sendRequestData();
        } else {
            listSubtype = new ArrayList<>();
            if (listSubtype != null && listSubtype.size() > 0) {
                newTypeAdapter.appendDataed(listSubtype, true);
                newTypeAdapter.updateAdapter();
            } else {
                ((HomeActivity) getActivity()).showToast("请检查当前网络状态");
            }
        }
    }

    /**
     * 加载首页数据
     */

    private void loadHomeList() {
        if (CommUtil.isNetworkAvailable(getActivity())) {
            sendRequestNews();
        } else {
            listnews = dbTools.getLocalNews();
            if (listnews != null && listnews.size() > 0) {
                newsadapter.appendDataed(listnews, true);
                newsadapter.updateAdapter();
            } else {
                ((HomeActivity) getActivity()).showToast("请检查当前网络状态");
            }
        }
    }

    /**
     * 传达请求数据
     */
    private void sendRequestData() {
        String url = API.NEWS_SORT + "ver=" + Contacts.VER + "&imei=" + CommUtil.getIMEI(getContext());
        JsonObjectRequest jsonobjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        listNewsType = ParserNews.getNewsType(jsonObject.toString());
                        listSubtype = new ArrayList<>();
                        for (int i = 0; i < listNewsType.size(); i++) {
                            listSubtype.addAll(listNewsType.get(i).getSubgrp());
                        }
                        for(int i=0;i<listSubtype.size();i++) {
                            dbTools.saveLocalType(listSubtype.get(i));
                        }


                        newTypeAdapter.appendDataed(listSubtype, true);
                        newTypeAdapter.updateAdapter();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );
        requestQueue.add(jsonobjectRequest);
    }


    public void sendRequestNews() {
        String url = API.NEWS_LIST + "ver=" + Contacts.VER + "dsf&subid=1" + "&dir=1" +
                "&nid=2" + "&stamp=" + CommUtil.getSystime() + "&cnt=2";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        listnews = ParserNews.getNews(jsonObject.toString());
                        for(int i = 0; i < listnews.size(); i++) {
                            dbTools.saveLocalNews(listnews.get(i));
                        }
                        newsadapter.appendDataed(listnews, true);
                        newsadapter.updateAdapter();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


    private AdapterView.OnItemClickListener onitemClickLister = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            News news = newsadapter.getItem(position);
            bundle.putSerializable("news", news);
            ((HomeActivity) getActivity()).openActivity(NewsShowActivity.class, bundle);
        }
    };
}
