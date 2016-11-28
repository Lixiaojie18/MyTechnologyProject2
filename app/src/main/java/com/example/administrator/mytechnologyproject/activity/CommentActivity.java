package com.example.administrator.mytechnologyproject.activity;

/**
 * 显示评论的页面
 */

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.Util.CommUtil;
import com.example.administrator.mytechnologyproject.Util.SharedUtil;
import com.example.administrator.mytechnologyproject.adapter.CommentAdapter;
import com.example.administrator.mytechnologyproject.base.MyBaseActivity;
import com.example.administrator.mytechnologyproject.gloable.API;
import com.example.administrator.mytechnologyproject.gloable.Contacts;
import com.example.administrator.mytechnologyproject.model.BaseEntry;
import com.example.administrator.mytechnologyproject.model.Comment;
import com.example.administrator.mytechnologyproject.model.News;
import com.example.administrator.mytechnologyproject.model.parser.ParserNews;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;

public class CommentActivity extends MyBaseActivity implements View.OnClickListener {
    private EditText et_say;
    private Button btn_send;
    private RequestQueue requestQueue;//在主线程请求数据，子线程运行。
    private News news;
    private CommentAdapter adapter;
    private ListView listView;
    private ImageView iv_image_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        et_say = (EditText) findViewById(R.id.et_say);
        btn_send = (Button) findViewById(R.id.btn_send);
        iv_image_back = (ImageView) findViewById(R.id.iv_image_back);
        iv_image_back.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        adapter = new CommentAdapter(this);
        Bundle bundle = getIntent().getExtras();//发送一个广播
        news = (News) bundle.getSerializable("news");
        listView = (ListView) findViewById(R.id.list_say);
        listView.setAdapter(adapter);
        setComment();
    }

    //解析评论内容
    private void setComment() {
        requestQueue = Volley.newRequestQueue(this);
        String url = API.COMMENT + "ver=" + Contacts.VER + "&nid=" + news.getNid() + "&type=1&stamp="
                + CommUtil.getSystime() + "&cid=20&dir=1&cnt=" + 20;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseEntry<List<Comment>>>() {
                        }.getType();
                        BaseEntry<List<Comment>> entry = gson.fromJson(jsonObject.toString(), type);
                        List<Comment> list = entry.getData();
                        adapter.appendDataed(list, true);
                        adapter.updateAdapter();
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

    //自己发布
    public void setPublish(String content, String token) {
        String ss = null;
        try {
            ss = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        requestQueue = Volley.newRequestQueue(this);
        String url = API.Publish + "ver=" + Contacts.VER + "&nid=" + news.getNid() + "&token=" + token + "&imei=" +
                CommUtil.getIMEI(this) + "&ctx=" + ss;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String res = ParserNews.getPublish(jsonObject.toString());
                        if (res == null) {
                            showToast("发表评论失败");
                        } else {
                            showToast(res);
                            et_say.setText(null);
                            setComment();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String token = SharedUtil.getTokey(this, "token");//用户令牌
                String content = et_say.getText().toString();//发布内容
                if (content == null || content.length() <= 0) {
                    showToast("评论内容不能为空");
                } else if (token == null || content.length() <= 0) {
                    showToast("用户未登录，请登陆后再发表评论");
                } else {
                    setPublish(content, token);
                }
                break;
            case R.id.iv_image_back:
                Bundle bundle = new Bundle();
                bundle.putSerializable("news",news);
                startActivity(NewsShowActivity.class,bundle);
                break;
        }
    }
}
