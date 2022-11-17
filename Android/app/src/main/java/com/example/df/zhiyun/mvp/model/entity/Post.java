package com.example.df.zhiyun.mvp.model.entity;

/**
 * Auto-generated: 2019-07-24 13:11:39
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Post {
    public static final int TYPE_READ = 1;
    public static final int TYPE_UNREAD = 0;
    private String noticeContent;
    private String moticeTime;
    private String noticeId;
    private int status;
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
    public String getNoticeContent() {
        return noticeContent;
    }

    public void setMoticeTime(String moticeTime) {
        this.moticeTime = moticeTime;
    }
    public String getMoticeTime() {
        return moticeTime;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }
    public String getNoticeId() {
        return noticeId;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

}
