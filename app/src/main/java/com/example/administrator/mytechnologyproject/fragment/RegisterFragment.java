package com.example.administrator.mytechnologyproject.fragment;

/**
 * 注册页面
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.example.administrator.mytechnologyproject.activity.SuccesActivity;
import com.example.administrator.mytechnologyproject.gloable.API;
import com.example.administrator.mytechnologyproject.gloable.Contacts;
import com.example.administrator.mytechnologyproject.model.BaseEntry;
import com.example.administrator.mytechnologyproject.model.Register;
import com.example.administrator.mytechnologyproject.model.parser.ParserUser;

import org.json.JSONObject;

public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";
    private EditText ed_ediText_email, ed_ediText_name, ed_ediText_pwd;
    private CheckBox checkbox;
    private Button button_regist;
    private RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ed_ediText_email = (EditText) view.findViewById(R.id.ed_ediText_email);
        ed_ediText_name = (EditText) view.findViewById(R.id.ed_ediText_name);
        ed_ediText_pwd = (EditText) view.findViewById(R.id.ed_ediText_pwd);
        checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        button_regist = (Button) view.findViewById(R.id.button_regist);
        requestQueue = Volley.newRequestQueue(getActivity());
        button_regist.setOnClickListener(onClickListener);
        return view;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name, password, email;
            email = ed_ediText_email.getText().toString();
            name = ed_ediText_name.getText().toString();
            password = ed_ediText_pwd.getText().toString();

            if (!checkbox.isChecked()) {
                Toast.makeText(getActivity(), "请仔细阅读条款，并选择同意", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!CommUtil.verifyEmail(email)) {
                Toast.makeText(getActivity(), "请输入正确的邮箱格式", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getActivity(), "请输入用户名", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!CommUtil.verifyPassword(password)) {
                Toast.makeText(getActivity(), "请输入6到16位由字母、数字和英文组成的字符", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = API.USER_REGISTER + "ver=" + Contacts.VER + "&uid=" + name + "&email="
                    + email + "&pwd=" + password;
            requestRegister(url);
        }
    };

    /**
     * 请求数据
     *
     * @param url JsonObjectRequest 请求
     *            Response 回答，把数据给主线程
     *            ErrorListener 错误执行这个方法
     */
    private void requestRegister(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<Register> baseRegister = ParserUser.getRegisterInfo(jsonObject.toString());
                        Register register = baseRegister.getData();
                        int result = register.getResult();
                        if (result == -2) {
                            Toast.makeText(getActivity(), "用户名重复", Toast.LENGTH_SHORT).show();
                        }
                        if (result == -3) {
                            Toast.makeText(getActivity(), "邮箱重复", Toast.LENGTH_SHORT).show();
                        }
                        if (result == 0) {
                            startActivity(new Intent(getActivity(), SuccesActivity.class));
                            SharedUtil.saveRegisterInfo(baseRegister, getContext());
             //               SharedUtil.saveRegisterInfo(baseRegister,getContext());
            //                ((HomeActivity) getContext()).changeFragmentStatus();
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
