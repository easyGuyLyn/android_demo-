package com.example.administrator.myapplication.BluetoothChat.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/18.
 */

public class BluChatMsgRp implements Serializable {
    private BluChatMsgBean model;

    public BluChatMsgBean getModel() {
        return model;
    }

    public void setModel(BluChatMsgBean model) {
        this.model = model;
    }
}
