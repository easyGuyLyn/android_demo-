package com.example.administrator.myapplication.BluetoothChat.model;

/**
 * Created by Administrator on 2016/10/8.
 */

public class BluChatMsgText extends BluChatMsg {

    private String text;

    public BluChatMsgText(int from, String deviceName, String text) {
        super(from, deviceName);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
