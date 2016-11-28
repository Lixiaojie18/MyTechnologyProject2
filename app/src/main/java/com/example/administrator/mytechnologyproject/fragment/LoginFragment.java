package com.example.administrator.mytechnologyproject.fragment;

/**
 * 登录界面
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.Util.CommUtil;
import com.example.administrator.mytechnologyproject.Util.SharedUtil;
import com.example.administrator.mytechnologyproject.activity.ForgetPwdActivity;
import com.example.administrator.mytechnologyproject.activity.HomeActivity;
import com.example.administrator.mytechnologyproject.activity.SuccesActivity;
import com.example.administrator.mytechnologyproject.gloable.API;
import com.example.administrator.mytechnologyproject.gloable.Contacts;
import com.example.administrator.mytechnologyproject.model.BaseEntry;
import com.example.administrator.mytechnologyproject.model.Register;
import com.example.administrator.mytechnologyproject.model.parser.ParserUser;

import org.json.JSONObject;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private Button btn_login_regist, btn_login_login;
    private EditText et_loginName, et_loginPwd;
    private RequestQueue requestQueue;
    private PopupWindow popupWindow;
    private View view;
    private TextView tv_forget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        btn_login_regist = (Button) view.findViewById(R.id.btn_login_regist);
        btn_login_login = (Button) view.findViewById(R.id.btn_login_login);
        et_loginName = (EditText) view.findViewById(R.id.et_loginName);
        et_loginPwd = (EditText) view.findViewById(R.id.et_loginName);
        btn_login_regist.setOnClickListener(this);
        btn_login_login.setOnClickListener(this);
        tv_forget = (TextView) view.findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(getActivity());
        PopupWindow();
        return view;
    }

    private void PopupWindow() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_popupwindow, null);
        Button tv_sure = (Button) view.findViewById(R.id.tv_sure);
        Button tv_end = (Button) view.findViewById(R.id.tv_end);
        tv_end.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        /**
         * view类是android中众多UI组件的父类；viewgroup也继承自view类，它是UI组件的容器；
         * 举例讲，view类的子类有TextView，EditView这种文本框；viewGroup类的子类有Linearlayout，Slidingmenu等布局。，
         * 而LayoutParams是他们共有的子视图
         */
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_regist:
                ((HomeActivity) getActivity()).showRegisteFragment();
                break;
            case R.id.btn_login_login:
                String name = et_loginName.getText().toString();
                String pwd = et_loginPwd.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!CommUtil.verifyPassword(pwd)) {
                    Toast.makeText(getActivity(), "请输入6到16位由字母、数字和英文组成的字符", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = API.USER_LOGIN + "ver=" + Contacts.VER + "&uid=" + name + "&pwd="
                        + pwd + "&device=" + 0;
                requestLogin(url);
                break;
            case R.id.tv_forget:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else if (popupWindow != null) {
                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 5);
                }
                break;
            case R.id.tv_sure:
                ((HomeActivity) getActivity()).openActivity(ForgetPwdActivity.class);
                break;
            case R.id.tv_end:
                popupWindow.dismiss();
                break;
        }
    }

    private void requestLogin(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<Register> baseLogin = ParserUser.getRegisterInfo(jsonObject.toString());
                        int result = baseLogin.getStatus();
                        if (result == 0) {
                            int loginID = baseLogin.getStatus();
                            if (loginID == 0) {
                                startActivity(new Intent(getActivity(), SuccesActivity.class));
                                SharedUtil.saveRegisterInfo(baseLogin, getContext());
                                //           SharedUtil.saveRegisterInfo(baseLogin, getContext());
                            }
                        }
                        if (result == -1) {
                            Toast.makeText(getActivity(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
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
}
