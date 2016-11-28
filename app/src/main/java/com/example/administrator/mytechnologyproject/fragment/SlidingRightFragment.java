package com.example.administrator.mytechnologyproject.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.mytechnologyproject.R;
import com.example.administrator.mytechnologyproject.Util.ImageLoader;
import com.example.administrator.mytechnologyproject.Util.SharedUtil;
import com.example.administrator.mytechnologyproject.activity.HomeActivity;
import com.example.administrator.mytechnologyproject.activity.SuccesActivity;
import com.example.administrator.mytechnologyproject.gloable.Contacts;
import com.example.administrator.mytechnologyproject.model.BaseEntry;
import com.example.administrator.mytechnologyproject.model.Updata;
import com.example.administrator.mytechnologyproject.model.UpdateManager;
import com.example.administrator.mytechnologyproject.model.parser.PaserUpdate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class SlidingRightFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SlidingRightFragment";
    private ImageView iv_loginImageview,iv_loginedImageview,fun_qq;
    private TextView tv_loginTextView,tv_loginedName,updatenews;
    private RelativeLayout layout_unlogin, layout_unlogined;
    private boolean isLogin;
    private UpdateManager updateManager;
    private Platform qq;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sliding_right, container, false);

        iv_loginImageview = (ImageView) view.findViewById(R.id.iv_loginImageview);
        tv_loginTextView = (TextView) view.findViewById(R.id.tv_loginTextView);
        iv_loginedImageview = (ImageView) view.findViewById(R.id.iv_loginedImageview);
        tv_loginedName = (TextView) view.findViewById(R.id.tv_loginedName);

        layout_unlogin = (RelativeLayout) view.findViewById(R.id.layout_unlogin);
        layout_unlogined = (RelativeLayout) view.findViewById(R.id.layout_unlogined);
        updatenews = (TextView) view.findViewById(R.id.updatenews);
        updatenews.setOnClickListener(this);

        fun_qq = (ImageView) view.findViewById(R.id.fun_qq);
        fun_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qq = ShareSDK.getPlatform(QQ.NAME);
                qq.removeAccount();
                qq.setPlatformActionListener(paListener);
                qq.authorize();//单独授权
                //true不使用SSo授权，false授权
                qq.SSOSetting(true);
                qq.showUser(null);//授权并获取用户信息
                //authorize与showUser单独调用一个即可
            }
        });

        iv_loginImageview.setOnClickListener(this);
        tv_loginTextView.setOnClickListener(this);
        iv_loginedImageview.setOnClickListener(this);
        tv_loginedName.setOnClickListener(this);
        isLogin = SharedUtil.getIsLogined(getActivity(), "isLogin", false);
        changeView();
        return view;
    }
    //第三方登录
    PlatformActionListener paListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            String name = qq.getDb().getUserName();
            String picture = qq.getDb().getUserIcon();
            SharedUtil.saveThreeName(getContext(),name);
            SharedUtil.saveThreePicture(getContext(),picture);
            SharedUtil.saveEnter(getContext(),true);
            SharedUtil.saveLogin(getContext(),true);
            ((HomeActivity)getActivity()).openActivity(SuccesActivity.class);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
        }

        @Override
        public void onCancel(Platform platform, int i) {
        }
    };

    public void changeView() {
        if(isLogin) {
            layout_unlogined.setVisibility(View.VISIBLE);
            String UseUid = SharedUtil.getUserUid(getContext());
            String UserPortrait = SharedUtil.getUserPortrait(getContext());
            new ImageLoader(getContext()).dispaly(UserPortrait,iv_loginedImageview);
            tv_loginedName.setText(UseUid);
            layout_unlogin.setVisibility(View.GONE);

        }else {
            layout_unlogin.setVisibility(View.VISIBLE);
            layout_unlogined.setVisibility(View.GONE);
        }
    }

    private void Updatecheck(String url) {
        //检查版本是否需要更新
        updateManager = new UpdateManager(getContext(),url);
        updateManager.checkUpdateInfo();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                BaseEntry<Updata> entry = null;
                try{
                    entry = PaserUpdate.getUpdate(msg.obj.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(entry.getStatus() == 0) {
                    Updata mupdata = entry.getData();
                    if(Contacts.VER == mupdata.getVersion()) {
                        ((HomeActivity)getActivity()).showToast("版本已经是最新的了");
                    }else {
                        String updateUrl = mupdata.getLink();
                        Updatecheck(updateUrl);
                        ((HomeActivity)getActivity()).showToast("更新成功");
                    }
                }else {
                    ((HomeActivity)getActivity()).showToast("获取更新信息失败，请检查网络连接");
                }
            }
            super.handleMessage(msg);
        }
    };

    private void sendQueue_update() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.3.80:8080/test/update.json");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuffer str = new StringBuffer();
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        str.append(line);
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = str.toString();
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                super.run();
            }
        }.start();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_loginImageview:
            case R.id.tv_loginTextView:
                ((HomeActivity)getActivity()).showLoginFrament();

                break;
            case R.id.iv_loginedImageview:
            case R.id.tv_loginedName:
                ((HomeActivity)getActivity()).openActivity(SuccesActivity.class);
                break;
            case R.id.updatenews:
                Log.i(TAG, "onClick: 版本更新-----------------------------");
                sendQueue_update();
                break;
        }
    }
}
