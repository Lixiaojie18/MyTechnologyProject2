package com.example.administrator.mytechnologyproject.gloable;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class API {
    public static final String ServerIP = "http://118.244.212.82:9092/newsClient/";

    /**
     * 首页列表
     */
    public static final String NEWS_LIST = ServerIP + "news_list?";


    /**
     * 标题列表
     */
    public static final String NEWS_SORT = ServerIP + "news_sort?";


    /**
     * 用户登录接口
     */
    public static final String USER_LOGIN = ServerIP + "user_login?";



    /**
     * 用户注册的接口
     */
    public static final String USER_REGISTER = ServerIP + "user_register?";


    /**
     * 用户中心数据
     */
    public static final String USER_CENTER_DATA = ServerIP + "user_home?";

    /**
     * 跟帖
     */

    public static final String FOLLOW_UP = ServerIP + "cmt_num?";

    /**
     * 评论内容
     */
    public static final String COMMENT = ServerIP + "cmt_list?";


    /**
     * 发布评论
     */

    public static final String Publish = ServerIP + "cmt_commit?";

    /**
     * APP版本更新
     */
    public static final String APP_UPDATA = ServerIP + "";

    /**
     * 找回密码
     */
    public static final String FORGET_PASS =  ServerIP + "user_forgetpass?";

}
