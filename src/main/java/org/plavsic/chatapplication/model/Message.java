package org.plavsic.chatapplication.model;

public class Message {
    private String msg;
    private boolean isCurrentClient;

    public Message(String msg,boolean isCurrentClient) {
        this.msg = msg;
        this.isCurrentClient = isCurrentClient;
    }


    public boolean isCurrentClient() {
        return isCurrentClient;
    }

    public String getMsg() {
        return msg;
    }
}
