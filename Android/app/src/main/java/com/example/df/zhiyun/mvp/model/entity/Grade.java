package com.example.df.zhiyun.mvp.model.entity;

import java.util.Date;

public class Grade {
    private String homeworkName;
    private float score;
    private String homeworkId;
    private Date createTime;
    private float scoreRate;
    private float studentScore;
    private String className;
    private String subjectName;
    private float avg;
    private float classScoreRate;

    public String getHomeworkName() {
        return homeworkName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public float getScoreRate() {
        return scoreRate;
    }

    public void setScoreRate(float scoreRate) {
        this.scoreRate = scoreRate;
    }

    public float getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(float studentScore) {
        this.studentScore = studentScore;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public float getClassScoreRate() {
        return classScoreRate;
    }

    public void setClassScoreRate(float classScoreRate) {
        this.classScoreRate = classScoreRate;
    }
}
