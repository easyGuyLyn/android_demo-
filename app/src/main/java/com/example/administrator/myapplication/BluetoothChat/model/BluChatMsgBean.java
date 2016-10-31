package com.example.administrator.myapplication.BluetoothChat.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/18.
 */

public class BluChatMsgBean implements Serializable {

    //基本字段
    private String time;
    private String sender;
    private String content;
    private String receiver;
    private String contentType; // 0 包头 1 文字  2 图片 3 语音 4 其他

    //分类字段
    private String voiceLength;//语音长度
    private String filePath;//文件地址

    public BluChatMsgBean(String contentType, String receiver, String content, String time, String sender) {
        this.contentType = contentType;
        this.receiver = receiver;
        this.content = content;
        this.time = time;
        this.sender = sender;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVoiceLength() {
        return voiceLength;
    }

    public void setVoiceLength(String voiceLength) {
        this.voiceLength = voiceLength;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
