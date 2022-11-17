package com.example.df.zhiyun.mvp.model.entity;

public class CommentItem {
    String id;
    String stuHomeworkName;
    String homeworkId;
    String subjectId;
    String subject;
    long createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getStuHomeworkName() {
        return stuHomeworkName;
    }

    public void setStuHomeworkName(String stuHomeworkName) {
        this.stuHomeworkName = stuHomeworkName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
