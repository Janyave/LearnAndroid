package com.example.hzzhanyawei.learnandroid;

/**
 * Created by hzzhanyawei on 2015/8/20.
 * Email hzzhanyawei@corp.netease.com
 */
public class Msg {

    public static final int TYPE_REVD = 0;
    public static final int TYPE_SEND = 1;

    private int type;
    private String msg;

    public Msg(int type, String msg){
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
