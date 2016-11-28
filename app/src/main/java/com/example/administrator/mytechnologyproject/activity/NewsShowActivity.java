package com.example.administrator.mytechnologyproject.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.Util.CommUtil;
import com.example.administrator.mytechnologyproject.Util.dbutil.DBTools;
import com.example.administrator.mytechnologyproject.base.MyBaseActivity;
import com.example.administrator.mytechnologyproject.gloable.API;
import com.example.administrator.mytechnologyproject.gloable.Contacts;
import com.example.administrator.mytechnologyproject.model.News;
import com.example.administrator.mytechnologyproject.model.parser.ParserNews;

import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import static android.webkit.WebSettings.LayoutAlgorithm.NARROW_COLUMNS;
import static android.webkit.WebSettings.LayoutAlgorithm.SINGLE_COLUMN;

/**
 * 加载新闻页面后的布局
 */
public class NewsShowActivity extends MyBaseActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private PopupWindow popupWindow;
    private ImageView imageView;
    private ImageView iv_image_back;
    private DBTools dbTools;
    private News news;
    private TextView tv_follow;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CommUtil.isNetworkAvailable(this)) {
            setContentView(R.layout.no_net_work);
        } else {
            setContentView(R.layout.activity_news_show);
            webView = (WebView) findViewById(R.id.webView);

            imageView = (ImageView) findViewById(R.id.iv_show_menu);
            iv_image_back = (ImageView) findViewById(R.id.iv_image_back);
            iv_image_back.setOnClickListener(onClickListener);
            imageView.setOnClickListener(onClickListener);
            progressBar = (ProgressBar) findViewById(R.id.pb_progress);

            tv_follow = (TextView) findViewById(R.id.tv_follow);
            tv_follow.setOnClickListener(onClickListener);

            requestQueue = Volley.newRequestQueue(this);

            dbTools = new DBTools(this);
            Bundle bundle = getIntent().getExtras();
            news = (News) bundle.getSerializable("news");
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            // User settings
            webSettings.setJavaScriptEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setUseWideViewPort(true);//关键点

            webSettings.setLayoutAlgorithm(
                    SINGLE_COLUMN);
            webSettings.setDisplayZoomControls(false);
            webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
            webSettings.setAllowFileAccess(true); // 允许访问文件
            webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
            webSettings.setSupportZoom(true); // 支持缩放

            webSettings.setLoadWithOverviewMode(true);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int mDensity = metrics.densityDpi;
            Log.d("maomao", "densityDpi = " + mDensity);
            if (mDensity == 240) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else if (mDensity == 160) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            } else if (mDensity == 120) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
            } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else if (mDensity == DisplayMetrics.DENSITY_TV) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            }


            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            WebChromeClient client = new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    progressBar.setProgress(newProgress);
                    if (newProgress >= 100) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            };

/**
 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
 */
            webView.setWebChromeClient(client);
            webSettings.setLayoutAlgorithm(NARROW_COLUMNS);
            webView.getSettings().setLayoutAlgorithm(SINGLE_COLUMN);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.loadUrl(news.getLink());
            setRequestData();
            showPopupWindow();
        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    private void showPopupWindow() {
        View view = getLayoutInflater().inflate(R.layout.layout_favorite, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_favorite);
        TextView tv2= (TextView) view.findViewById(R.id.tv_shared);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                boolean isSaved = dbTools.saveLocalFavorite(news);
                if (isSaved) {
                    showToast("收藏成功");
                } else {
                    showToast("已收藏");
                }
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showShare();
            }
        });
    }

    private void setRequestData() {
        String url = API.FOLLOW_UP + "ver=" + Contacts.VER + "&nid=" + news.getNid();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        int data = ParserNews.getFollow(jsonObject.toString());

                        if(data > 0) {
                            tv_follow.setText("跟帖：" + data);
                        }else {
                            tv_follow.setVisibility(View.GONE);
                        }

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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_image_back:
                    openActivity(HomeActivity.class);
                    finish();
                    break;
                case R.id.iv_show_menu:
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else if (popupWindow != null) {
                        popupWindow.showAsDropDown(imageView, 0, 5);
                    }

                    break;
                case R.id.tv_follow:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news",news);
                    openActivity(CommentActivity.class,bundle);
                    break;
            }
        }
    };

}
