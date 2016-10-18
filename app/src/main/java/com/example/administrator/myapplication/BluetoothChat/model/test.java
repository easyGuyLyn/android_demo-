package com.example.administrator.myapplication.BluetoothChat.model;

/**
 * Created by Administrator on 2016/10/18.
 */

public class test {

    /**
     * content : 解决了
     * contentType : 1
     * receiver : 小米平板
     * sender : easyGuy
     * time : 1476781965071
     */

    private ModelBean model;

    public ModelBean getModel() {
        return model;
    }

    public void setModel(ModelBean model) {
        this.model = model;
    }

    public static class ModelBean {
        private String content;
        private String contentType;
        private String receiver;
        private String sender;
        private String time;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
    }
}
