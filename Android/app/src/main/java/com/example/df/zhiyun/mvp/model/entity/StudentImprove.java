package com.example.df.zhiyun.mvp.model.entity;

public class StudentImprove {
    String homework_name;
    String class_id;
    String homework_id;
    String student_id;
    float score_rate;
    float studentScore;
    long openTime;
    float score;
    float avg;

    public String getHomework_name() {
        return homework_name;
    }

    public void setHomework_name(String homework_name) {
        this.homework_name = homework_name;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getHomework_id() {
        return homework_id;
    }

    public void setHomework_id(String homework_id) {
        this.homework_id = homework_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public float getScore_rate() {
        return score_rate;
    }

    public void setScore_rate(float score_rate) {
        this.score_rate = score_rate;
    }

    public float getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(float studentScore) {
        this.studentScore = studentScore;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }
}
