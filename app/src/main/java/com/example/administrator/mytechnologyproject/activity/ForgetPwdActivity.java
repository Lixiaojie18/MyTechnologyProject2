package com.example.administrator.mytechnologyproject.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.base.MyBaseActivity;
import com.example.administrator.mytechnologyproject.gloable.API;
import com.example.administrator.mytechnologyproject.gloable.Contacts;
import com.example.administrator.mytechnologyproject.model.BaseEntry;
import com.example.administrator.mytechnologyproject.model.Pass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class ForgetPwdActivity extends MyBaseActivity implements View.OnClickListener {
    private EditText editext;
    private Button btn_forget;
    private RequestQueue request;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        btn_forget = (Button) findViewById(R.id.btn_forget);
        image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(this);
        btn_forget.setOnClickListener(this);
        editext = (EditText) findViewById(R.id.editext);
    }

    private void FindPass(String email) {
        request = Volley.newRequestQueue(this);
        String url = API.FORGET_PASS + "ver=" + Contacts.VER + "&email=" + email;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseEntry<Pass>>() {
                        }.getType();
                        BaseEntry<Pass> entry = gson.fromJson(jsonObject.toString(), type);
                        Pass pwd = entry.getData();
                        int in = pwd.getResult();
                        String explain = pwd.getExplain();
                        if (in == 0) {
                            showToast(explain);
                            startActivity(HomeActivity.class);
                        } else {
                            showToast(explain);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                }
        );
        request.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forget:
                String email = editext.getText().toString();
                if (email.length() <= 0 || email == null) {
                    showToast("邮箱不能为空");
                }else {
                    FindPass(email);
                }
                break;
            case R.id.image:
                startActivity(HomeActivity.class);
                break;
        }
    }
}
