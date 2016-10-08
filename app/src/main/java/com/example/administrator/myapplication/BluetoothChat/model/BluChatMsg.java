package com.example.administrator.myapplication.BluetoothChat.model;


/**
 * Created by Administrator on 2016/10/7.
 */

public class BluChatMsg {
    public static final int SEND = 0;
    public static final int RECEIVE = 1;

    private int from;
    private String deviceName;

    public BluChatMsg(int from, String deviceName) {
        this.from = from;
        this.deviceName = deviceName;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
