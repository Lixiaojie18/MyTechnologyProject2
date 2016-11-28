package com.example.administrator.mytechnologyproject.model;

import android.app.Application;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class CrashApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
