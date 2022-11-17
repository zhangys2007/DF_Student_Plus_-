package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

/***
 * 试卷
 */
public class ExerSet {
    private String count;
    private List<Exer> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Exer> getList() {
        return list;
    }

    public void setList(List<Exer> list) {
        this.list = list;
    }
}
