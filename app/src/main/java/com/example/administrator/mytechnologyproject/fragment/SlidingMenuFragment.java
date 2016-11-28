package com.example.administrator.mytechnologyproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.activity.HomeActivity;
import com.example.administrator.mytechnologyproject.activity.LocationActivity;

/**
 * 左侧滑文件
 */
public class SlidingMenuFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout iv_sliding_news, iv_sliding_read, iv_sliding_ties,
            iv_sliding_local, iv_sliding_favorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sliding_menu, container, false);
        iv_sliding_news = (RelativeLayout) view.findViewById(R.id.iv_sliding_news);
        iv_sliding_read = (RelativeLayout) view.findViewById(R.id.iv_sliding_read);
        iv_sliding_ties = (RelativeLayout) view.findViewById(R.id.iv_sliding_ties);
        iv_sliding_local = (RelativeLayout) view.findViewById(R.id.iv_sliding_local);
        iv_sliding_favorite = (RelativeLayout) view.findViewById(R.id.iv_sliding_favorite);

        iv_sliding_news.setOnClickListener(this);
        iv_sliding_read.setOnClickListener(this);
        iv_sliding_ties.setOnClickListener(this);
        iv_sliding_local.setOnClickListener(this);
        iv_sliding_favorite.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_sliding_news:
                ((HomeActivity) getActivity()).showHomeFragment();
                iv_sliding_news.setBackgroundColor(0x33c85555);
                iv_sliding_favorite.setBackgroundColor(0xc3f3fd);
                break;
            case R.id.iv_sliding_favorite:
                ((HomeActivity) getActivity()).showFavoriteFragment();
                iv_sliding_favorite.setBackgroundColor(0x33c85555);
                iv_sliding_news.setBackgroundColor(0xc3f3fd);
                break;
            case R.id.iv_sliding_ties:

                break;
            case R.id.iv_sliding_local:
                ((HomeActivity)getActivity()).openActivity(LocationActivity.class);
                break;
            case R.id.iv_sliding_read:
                ((HomeActivity)getActivity()).showImageFragment();
                break;
        }
    }
}
