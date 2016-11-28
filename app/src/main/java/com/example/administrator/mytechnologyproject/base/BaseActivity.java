package com.example.administrator.mytechnologyproject.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class BaseActivity extends AppCompatActivity{

    /**
     * 封装所有Activity的集合
     */
    private static ArrayList<BaseActivity> onLinActivityList = new ArrayList<BaseActivity>();

    /**
     * 一键销毁所有Activity
     */
    public void finishAllActivity() {
        //迭代器
        Iterator<BaseActivity> iterator = onLinActivityList.iterator();
        while (iterator.hasNext()) {
            iterator.next().finish();
        }
    }
    //跳转Activity
    protected void startActivity(Class<?> targetClass) {
        Intent intent = new Intent(this,targetClass);
        startActivity(intent);
    }

    protected void startActivity(Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 页面弹出内容的方法
     * @param content
     */
    protected void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onLinActivityList.add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onLinActivityList.remove(this);
    }

    //通过handleMessage方法接收子线程的消息
    protected Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            myHandlerMessage(msg);
        }
    };

    protected void myHandlerMessage(Message msg) {
    }
}
