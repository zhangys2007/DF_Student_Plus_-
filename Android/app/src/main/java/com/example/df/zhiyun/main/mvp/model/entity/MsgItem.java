package com.example.df.zhiyun.main.mvp.model.entity;

/**
 * 消息
 */
public class MsgItem {
    private String sendContent;
    private String title;
    private long sendTime;
    private String senderId;
    private String senderIdentity;

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderIdentity() {
        return senderIdentity;
    }

    public void setSenderIdentity(String senderIdentity) {
        this.senderIdentity = senderIdentity;
    }
}
