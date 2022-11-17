package com.example.df.zhiyun.mvp.model.entity;

public class HomeworkState {
    private String fzHomeworkId;
    private String classId;
    private String homeworkName;
    private String className;
    private String userName;
    private String userRealName;
    private String subjectName;
    private int submitCount;
    private int unSubmitCount;
    private int correctCount;
    private int unCorrectCount;
    private int assignCount;
    private float submitPercentage;
    private float correctPercentage;
    private String beginTime;

    public String getFzHomeworkId() {
        return fzHomeworkId;
    }

    public void setFzHomeworkId(String fzHomeworkId) {
        this.fzHomeworkId = fzHomeworkId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getHomeworkName() {
        return homeworkName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(int submitCount) {
        this.submitCount = submitCount;
    }

    public int getUnSubmitCount() {
        return unSubmitCount;
    }

    public void setUnSubmitCount(int unSubmitCount) {
        this.unSubmitCount = unSubmitCount;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public int getUnCorrectCount() {
        return unCorrectCount;
    }

    public void setUnCorrectCount(int unCorrectCount) {
        this.unCorrectCount = unCorrectCount;
    }

    public int getAssignCount() {
        return assignCount;
    }

    public void setAssignCount(int assignCount) {
        this.assignCount = assignCount;
    }

    public float getSubmitPercentage() {
        return submitPercentage;
    }

    public void setSubmitPercentage(float submitPercentage) {
        this.submitPercentage = submitPercentage;
    }

    public float getCorrectPercentage() {
        return correctPercentage;
    }

    public void setCorrectPercentage(float correctPercentage) {
        this.correctPercentage = correctPercentage;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
}
