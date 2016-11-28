package com.example.administrator.mytechnologyproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class User implements Serializable{
    private String uid;
    private String portrait;
    private int integration;
    private int comnum;
    private List<LoginLog> loginlog;


    public User(String uid, String portrait, int integration, int comnum, List<LoginLog> loginLog) {
        this.uid = uid;
        this.portrait = portrait;
        this.integration = integration;
        this.comnum = comnum;
        this.loginlog = loginLog;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getIntegration() {
        return integration;
    }

    public void setIntegration(int integration) {
        this.integration = integration;
    }

    public int getComnum() {
        return comnum;
    }

    public void setComnum(int comnum) {
        this.comnum = comnum;
    }

    public List<LoginLog> getLoginLog() {
        return loginlog;
    }

    public void setLoginLog(List<LoginLog> loginLog) {
        this.loginlog = loginLog;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", portrait='" + portrait + '\'' +
                ", integration=" + integration +
                ", comnum=" + comnum +
                ", loginLog=" + loginlog +
                '}';
    }
}
