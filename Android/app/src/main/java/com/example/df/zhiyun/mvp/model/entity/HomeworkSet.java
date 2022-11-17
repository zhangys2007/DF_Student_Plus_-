package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

public class HomeworkSet {
    private int count;
    private String teachId;
    private String hearingUrl;
    private String studentHomeWorkId;
    private String homeWorkId;
    private long time;
    private List<Question> list;
    private String subjectId;
    private String columnName;
    private int isPay;
    private String paperName;

    public String getHomeWorkId() {
        return homeWorkId;
    }

    public void setHomeWorkId(String homeWorkId) {
        this.homeWorkId = homeWorkId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public String getTeachId() {
        return teachId;
    }

    public void setTeachId(String teachId) {
        this.teachId = teachId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getHearingUrl() {
        return hearingUrl;
    }

    public void setHearingUrl(String hearingUrl) {
        this.hearingUrl = hearingUrl;
    }

    public String getStudentHomeWorkId() {
        return studentHomeWorkId;
    }

    public void setStudentHomeWorkId(String studentHomeWorkId) {
        this.studentHomeWorkId = studentHomeWorkId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<Question> getList() {
        return list;
    }

    public void setList(List<Question> list) {
        this.list = list;
    }
}
