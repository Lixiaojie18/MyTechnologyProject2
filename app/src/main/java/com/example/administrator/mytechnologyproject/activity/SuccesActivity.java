package com.example.administrator.mytechnologyproject.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.Util.CommUtil;
import com.example.administrator.mytechnologyproject.Util.ImageLoader;
import com.example.administrator.mytechnologyproject.Util.SharedUtil;
import com.example.administrator.mytechnologyproject.adapter.LoginAdapter;
import com.example.administrator.mytechnologyproject.base.MyBaseActivity;
import com.example.administrator.mytechnologyproject.model.BaseEntry;
import com.example.administrator.mytechnologyproject.gloable.API;
import com.example.administrator.mytechnologyproject.gloable.Contacts;
import com.example.administrator.mytechnologyproject.model.User;
import com.example.administrator.mytechnologyproject.model.parser.ParserUser;

import org.json.JSONObject;

import java.util.List;

/**
 * 登录成功后的页面
 */
public class SuccesActivity extends MyBaseActivity implements View.OnClickListener {

    private static final String TAG = "SuccesActivity";
    private RequestQueue requestQueue;
    private TextView name, tv_register, comment_count;
    private LoginAdapter adapter;
    private ListView list;
    private ImageView imageView_left, iv_image;
    private Button btn_end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes);
        requestQueue = Volley.newRequestQueue(this);
        name = (TextView) findViewById(R.id.name);
        tv_register = (TextView) findViewById(R.id.tv_register);
        comment_count = (TextView) findViewById(R.id.comment_count);
        list = (ListView) findViewById(R.id.list);
        imageView_left = (ImageView) findViewById(R.id.imageView_left);
        btn_end = (Button) findViewById(R.id.btn_end);
        btn_end.setOnClickListener(this);
        imageView_left.setOnClickListener(this);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        adapter = new LoginAdapter(this);
        list.setAdapter(adapter);

        String token = SharedUtil.getTokey(this, "token");
        String url = API.USER_CENTER_DATA + "ver=" + Contacts.VER + "&imei=" + CommUtil.getIMEI
                (this) + "&token=" + token;
        if(SharedUtil.isEnter(this)) {
            new ImageLoader(this).dispaly(SharedUtil.getUserPortrait(this),iv_image);
            name.setText(SharedUtil.getUserUid(this));
        }else {
            requestUserData(url);
        }
    }

    private void requestUserData(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<User> userBaseEntry = ParserUser.getLoginsuccInfo(jsonObject.
                                toString());
                        int status = userBaseEntry.getStatus();
                        if (status != 0) {
                            showToast("用户数据请求错误");
                        } else {
                            User user = userBaseEntry.getData();
                            SharedUtil.saveUserInfo(getBaseContext(), user);
                            new ImageLoader(getBaseContext()).dispaly(user.getPortrait(), iv_image);
                            name.setText(user.getUid());
                            tv_register.setText("用户积分" + user.getIntegration());
                            comment_count.setText(user.getComnum() + "");
                            adapter.appendDataed(user.getLoginLog(), true);
                            adapter.updateAdapter();
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

    private void dialog() {
        DialogInterface.OnClickListener dialogClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:
                        SharedUtil.clearData(getBaseContext());
                        openActivity(HomeActivity.class);
                        finish();
                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        showToast("取消");
                        break;
                }
            }
        };

        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否确认退出?"); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确认", dialogClick);
        builder.setNegativeButton("取消", dialogClick);
        builder.create().show();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_end:
                dialog();
                break;
            case R.id.imageView_left:
                startActivity(HomeActivity.class);
                break;
        }
    }
}
