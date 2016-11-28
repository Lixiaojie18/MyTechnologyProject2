package com.example.administrator.mytechnologyproject.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.mytechnologyproject.base.MyBaseActivity;
import com.example.administrator.mytechnologyproject.model.BaseEntry;
import com.example.administrator.mytechnologyproject.model.Register;
import com.example.administrator.mytechnologyproject.model.User;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class SharedUtil extends MyBaseActivity {
    private static final String SHARED_PATH = "app_share";
    private static final String SHARED_PATH_REGISTER = "register";


    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
    }


    public static SharedPreferences getDefaultSharedPreferences_register(Context context) {
        return context.getSharedPreferences(SHARED_PATH_REGISTER, Context.MODE_PRIVATE);
    }
    /**
     * 保存用户基本信息
     */
    public static void saveUserInfo(Context context,User user) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid",user.getUid());//用户名字
        editor.putString("portrait",user.getPortrait());//用户头像
        editor.commit();
    }
    /**
     * 得到已登录的用户名
     */
    public static String getUserUid(Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getString("uid",null);
    }
    /**
     * 得到已登录的用户头像地址
     */
    public static String getUserPortrait(Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getString("portrait",null);
    }


    /**
     * 保存用户的注册信息
     * @param baseRegister
     * @param context
     */
    public static void saveRegisterInfo(BaseEntry<Register> baseRegister, Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", true);
        Register register = baseRegister.getData();
        editor.putInt("result", register.getResult());
        editor.putString("explain", register.getExplain());
        editor.putString("token", register.getToken());
        editor.commit();
    }

    //存储登录状态
    public static void saveLogin(Context context,boolean boo) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", boo);
        editor.commit();
    }

    /**
     * 判断用户是否登录
     *
     * @param context
     * @param key
     * @param devalue
     */
    public static boolean getIsLogined(Context context, String key, boolean devalue) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        return sharedPreferences.getBoolean(key, devalue);
    }

    /**
     * 获得用户注册后的信息
     * @param context
     * @param key
     * @return
     */
    public static String getTokey(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        return sharedPreferences.getString(key,null);
    }
    //储存名字
    public static void saveThreeName(Context context,String name) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid",name);
        editor.commit();
    }

    //储存图片
    public static void saveThreePicture(Context context,String picture) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("portrait",picture);
        editor.commit();
    }
    //储存状态
    public static void saveEnter(Context context,boolean enter) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("enter",enter);
        editor.commit();
    }
    //判断登录状态
    public static boolean isEnter(Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
       return sharedPreferences.getBoolean("enter",false);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);
    }


    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, null);
    }

    public static void putboolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getboolean(Context context, String key, boolean devalue) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, devalue);
    }

    /**
     * 清除用户的信息
     */

    public static void clearData(Context context) {
    SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
