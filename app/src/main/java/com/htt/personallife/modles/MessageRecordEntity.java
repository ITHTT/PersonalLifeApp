package com.htt.personallife.modles;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MessageRecordEntity {
    private int id;
    private String messageUrl;
    private String messageTitle;
    private String messageBrief;
    private long messageTime;
    private int messageState;
    private int messageUnReadCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageBrief() {
        return messageBrief;
    }

    public void setMessageBrief(String messageBrief) {
        this.messageBrief = messageBrief;
    }

    public int getMessageState() {
        return messageState;
    }

    public void setMessageState(int messageState) {
        this.messageState = messageState;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public int getMessageUnReadCount() {
        return messageUnReadCount;
    }

    public void setMessageUnReadCount(int messageUnReadCount) {
        this.messageUnReadCount = messageUnReadCount;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }
}
