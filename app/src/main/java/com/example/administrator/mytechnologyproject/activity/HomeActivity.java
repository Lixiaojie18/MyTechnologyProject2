package com.example.administrator.mytechnologyproject.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.base.MyBaseActivity;
import com.example.administrator.mytechnologyproject.fragment.FavoriteFragment;
import com.example.administrator.mytechnologyproject.fragment.HomeFragment;
import com.example.administrator.mytechnologyproject.fragment.Image_Fragment;
import com.example.administrator.mytechnologyproject.fragment.LoginFragment;
import com.example.administrator.mytechnologyproject.fragment.RegisterFragment;
import com.example.administrator.mytechnologyproject.fragment.SlidingMenuFragment;
import com.example.administrator.mytechnologyproject.fragment.SlidingRightFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class HomeActivity extends MyBaseActivity implements View.OnClickListener {
    private static final String TAG = "HomeActivity";
    private SlidingMenu slidingmenu;
    private Fragment homeFragment, slidingLeftFragment, slidingRightFragmnet, longFragment, favoriteFragment,
            registeFragment, imageFragment;
    private TextView tv_home_title;
    private ImageView iv_home_left, iv_home_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv_home_title = (TextView) findViewById(R.id.tv_home_title);
        iv_home_left = (ImageView) findViewById(R.id.iv_home_left);
        iv_home_right = (ImageView) findViewById(R.id.iv_home_right);
        iv_home_left.setOnClickListener(this);
        iv_home_right.setOnClickListener(this);
        initSlidingMenu();
        loadHomeFragment();
    }


    //初始化
    private void initSlidingMenu() {
        slidingRightFragmnet = new SlidingRightFragment();
        slidingLeftFragment = new SlidingMenuFragment();
        slidingmenu = new SlidingMenu(this);

        slidingmenu.setMode(SlidingMenu.LEFT_RIGHT);//设置左右皆可滑动
        slidingmenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置滑动范围为全屏皆可滑动
        slidingmenu.setBehindWidth(550);// 设置SlidingMenu菜单的宽度
//        slidingmenu.setFadeDegree(0.55f);//设置渐变效果.但实际没看出啥效果啊
        slidingmenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);// 必须调用,使SlidingMenu附加在Activity上
        slidingmenu.setBehindOffsetRes(R.dimen.sliding_left);//设置主页面的宽度

        slidingmenu.setMenu(R.layout.layout_left_sliding);// 设置左侧滑的布局文件
        slidingmenu.setSecondaryMenu(R.layout.layout_right_sliding);//设置右侧滑的布局文件
        //让fragment在Activity上显示,与之相关联
        getSupportFragmentManager().beginTransaction().add(R.id.layout_left_container,
                slidingLeftFragment).commit();

        getSupportFragmentManager().beginTransaction().add(R.id.layout_right_container,
                slidingRightFragmnet).commit();
    }

    public void showFavoriteFragment() {
        setTitle("我的收藏");
        if (favoriteFragment == null) {
            favoriteFragment = new FavoriteFragment();
        }
        slidingmenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                favoriteFragment).commit();
    }

    /**
     * 显示主页面的Fragment
     */
    public void showHomeFragment() {
        setTitle("资讯");
        slidingmenu.showContent();
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, homeFragment).commit();
    }

    /**
     * 注册页面
     */
    public void showRegisteFragment() {
        setTitle("用户注册");
        if (registeFragment == null)
            registeFragment = new RegisterFragment();
        slidingmenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                registeFragment).commit();
    }

    /**
     * 登录界面
     */
    public void showLoginFrament() {
        setTitle("用户登录");
        if (longFragment == null)
            longFragment = new LoginFragment();
        slidingmenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                longFragment).commit();
    }

    public void showImageFragment() {
        setTitle("新闻图片");
        if (imageFragment == null)
            imageFragment = new Image_Fragment();
        slidingmenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                imageFragment).commit();
    }

    /**
     * 设置标题栏显示的内容
     *
     * @param name
     */
    public void setTitle(String name) {
        tv_home_title.setText(name);
    }

    private void loadHomeFragment() {
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, homeFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_left:
                if (slidingmenu != null && slidingmenu.isMenuShowing()) {
                    slidingmenu.showContent();
                } else {
                    slidingmenu.showMenu();
                }
                break;
            case R.id.iv_home_right:
                if (slidingmenu != null && slidingmenu.isMenuShowing()) {
                    slidingmenu.showContent();
                } else {
                    slidingmenu.showSecondaryMenu();
                }
                break;
        }
    }
}
