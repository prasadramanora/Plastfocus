package com.ramanora.plastfocus.PlastFocus_FirebaseServices;

/**
 * Created by abc on 02/01/2018.
 */

public class NotificationBean {

    String msg, type;
    int count;


    public NotificationBean() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}