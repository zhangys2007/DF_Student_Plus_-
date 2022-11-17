package com.example.df.zhiyun.mvp.model.entity;

import java.util.Date;

public class ClassImprove {
    private Date assign_date;
    private float score;
    private String homework_name;
    private float avg;
    private String class_id;
    private String schoolId;
    private String homework_id;
    private float score_rate;
    private int type;
    private long openTime;
    private String paperId;

    public Date getAssign_date() {
        return assign_date;
    }

    public void setAssign_date(Date assign_date) {
        this.assign_date = assign_date;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getHomework_name() {
        return homework_name;
    }

    public void setHomework_name(String homework_name) {
        this.homework_name = homework_name;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getHomework_id() {
        return homework_id;
    }

    public void setHomework_id(String homework_id) {
        this.homework_id = homework_id;
    }

    public float getScore_rate() {
        return score_rate;
    }

    public void setScore_rate(float score_rate) {
        this.score_rate = score_rate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }
}
