package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

public class SignedInfo {
    int signCount;

    int monthCount;

    List<String> list;

    public int getSignCount() {
        return signCount;
    }

    public void setSignCount(int signCount) {
        this.signCount = signCount;
    }

    public int getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(int monthCount) {
        this.monthCount = monthCount;
    }

    public List<String> getlist() {
        return list;
    }

    public void setlist(List<String> list) {
        this.list = list;
    }
}
