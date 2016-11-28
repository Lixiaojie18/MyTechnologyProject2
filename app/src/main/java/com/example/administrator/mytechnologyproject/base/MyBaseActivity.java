package com.example.administrator.mytechnologyproject.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mytechnologyproject.R;

public class MyBaseActivity extends FragmentActivity {
    private static final String TAG = "MyBaseActivity";
    private Toast toast;
    //screenW,screenH;代表获取手机的宽、高
    public static int screenW, screenH;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_base);
        screenW = getWindowManager().getDefaultDisplay().getWidth();
        screenH = getWindowManager().getDefaultDisplay().getHeight();
    }

    /**
     * 生命周期调试
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 公共的功能封装
     */

    public void showToast(String msg) {
        if (toast == null)
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    public void openActivity(Class<?> pClass) {
        openActivity(pClass,null,null);
    }

    public void openActivity(Class<?> pClass, Bundle bundle) {
        openActivity(pClass, bundle, null);
    }

    /**
     * Uri代表要操作的数据，Android上可用的每种资源 - 图像、视频片段等都可以用Uri来表示
     * @param pClass
     * @param bundle
     * Bundle，是Android开发中的一个类，用于Activity之间传输数据用。
     * @param uri
     */
    public void openActivity(Class<?> pClass, Bundle bundle, Uri uri) {
        Intent intent = new Intent(this, pClass);
        if (bundle != null)
            intent.putExtras(bundle);
        if (uri != null)
            intent.setData(uri);
        startActivity(intent);
    }

    public void startActivity(Class<?> targetClass) {
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);
    }

    public void startActivity(Class<?> targetClass, Bundle  bundle) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * @param context 上下文
     * @param msg     TextView显示的文本内容
     * @param cancle  控制的是否点击隐藏
     */
    public void showLoadingDialog(Context context, String msg, boolean cancle) {
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);//加载一个自定义的布局
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_loading_img);
        TextView tv = (TextView) view.findViewById(R.id.tv_loading_msg);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        //设置一个动画
        iv.setAnimation(animation);
        if (null != msg) {
            tv.setText(msg);
        }

        dialog = new Dialog(context, R.style.loading_dialog);//设置dialog的样式
        dialog.setContentView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.setCancelable(cancle);
        dialog.show();
    }

    /**
     * 取消消失Dialog
     */
    public void cancalDialog() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

}
