package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

//布置作业详情
public class PutHWDetail {
    String homeworkName;
    List<String> strings;
    int types;
    long beginTime;
    long endTime;
    long createTime;
    int isHide;
    int isDisplayAnswer;


    public String getHomeworkName() {
        return homeworkName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getIsHide() {
        return isHide;
    }

    public void setIsHide(int isHide) {
        this.isHide = isHide;
    }

    public int getIsDisplayAnswer() {
        return isDisplayAnswer;
    }

    public void setIsDisplayAnswer(int isDisplayAnswer) {
        this.isDisplayAnswer = isDisplayAnswer;
    }
}
