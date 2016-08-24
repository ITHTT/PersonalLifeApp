package com.htt.imlibrary.modles;

public class EmoticonEntity {

    private long mEventType;
    private String mIconUri;
    private String mContent;
    private int iconResource;

    public long getEventType() {
        return mEventType;
    }

    public void setEventType(long eventType) {
        this.mEventType = eventType;
    }

    public String getIconUri() {
        return mIconUri;
    }

    public void setIconUri(String iconUri) {
        this.mIconUri = iconUri;
    }

    public void setIconUri(int iconUri) {
        this.mIconUri = "" + iconUri;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public EmoticonEntity(long eventType, String iconUri, String content) {
        this.mEventType = eventType;
        this.mIconUri = iconUri;
        this.mContent = content;
    }

    public EmoticonEntity(String iconUri, String content) {
        this.mIconUri = iconUri;
        this.mContent = content;
    }

    public EmoticonEntity(String content) {
        this.mContent = content;
    }

    public EmoticonEntity() { }
}
