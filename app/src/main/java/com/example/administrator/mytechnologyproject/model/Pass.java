package com.example.administrator.mytechnologyproject.model;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class Pass {

    private int result;
    private String explain;

    public Pass(int result, String explain) {
        this.result = result;
        this.explain = explain;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    @Override
    public String toString() {
        return "Pass{" +
                "result=" + result +
                ", explain='" + explain + '\'' +
                '}';
    }
}
