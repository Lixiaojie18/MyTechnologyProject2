package com.example.administrator.mytechnologyproject.activity;

/**
 * 引导页面
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.adapter.MyPagerAdapter;
import com.example.administrator.mytechnologyproject.base.BaseActivity;

public class LeadActivity extends BaseActivity {

    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    private ImageView[] imageViews = new ImageView[4];
    private boolean isFirstRunning;

    /**android 的四种枚举:
     * Context.MODE_PRIVATE：为默认操作模式，代表该文件是私有数据，只能被应用本身访问，在该模式下，
     * 写入的内容会覆盖原文件的内容，如果想把新写入的内容追加到原文件中。可以使用Context.MODE_APPEND
     Context.MODE_APPEND：模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件。
     Context.MODE_WORLD_READABLE和Context.MODE_WORLD_WRITEABLE用来控制其他应用是否有权限读写该文件。
     MODE_WORLD_READABLE：表示当前文件可以被其他应用读取；
     MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入。
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SharedPreferences 是储存数据，在这里就是判断是不是第一次登陆
        SharedPreferences preferences = getSharedPreferences("lead_config", Context.MODE_PRIVATE);//私有数据
        isFirstRunning = preferences.getBoolean("isFirst",true);
        if(isFirstRunning) {
            isFirstRunning =false;
            setContentView(R.layout.activity_lead);//显示布局
            initView();//初始化
            init();//监听
            initData();//导入数据
        }else {
            startActivity(LoginActivity.class);
            finish();
        }

    }
    /**
     * 生成用户配置信息
     */
    private void savePreferences() {
        SharedPreferences preferences = getSharedPreferences("lead_config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();//Editor是编辑器
        editor.putBoolean("isFirst",isFirstRunning);
        editor.commit();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imageViews[0] = (ImageView) findViewById(R.id.lead_icon1);
        imageViews[1] = (ImageView) findViewById(R.id.lead_icon2);
        imageViews[2] = (ImageView) findViewById(R.id.lead_icon3);
        imageViews[3] = (ImageView) findViewById(R.id.lead_icon4);

        imageViews[0].setAlpha(1.0f);
    }

    /**
     * 创建适配器，设置监听
     */
    private void init() {
        adapter = new MyPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(listener);
    }

    /**
     * 导入数据
     */
    private void initData() {
        ImageView imageView;
        imageView = (ImageView) getLayoutInflater().inflate(R.layout.viewpage_item, null);
        imageView.setBackgroundResource(R.drawable.wy);
        adapter.addToAdapterView(imageView);

        imageView = (ImageView) getLayoutInflater().inflate(R.layout.viewpage_item, null);
        imageView.setBackgroundResource(R.drawable.welcome);
        adapter.addToAdapterView(imageView);

        imageView = (ImageView) getLayoutInflater().inflate(R.layout.viewpage_item, null);
        imageView.setBackgroundResource(R.drawable.small);
        adapter.addToAdapterView(imageView);

        imageView = (ImageView) getLayoutInflater().inflate(R.layout.viewpage_item, null);
        imageView.setBackgroundResource(R.drawable.bd);
        adapter.addToAdapterView(imageView);

        adapter.notifyDataSetChanged();
    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (position >= 3) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(800);
                            savePreferences();//把false给这个方法
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        startActivity(LoginActivity.class);
                        finish();
                    }
                }.start();
            }
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[i].setAlpha(0.4f);
            }
            imageViews[position].setAlpha(1.0f);//position 是当前引导页的索引
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

}
