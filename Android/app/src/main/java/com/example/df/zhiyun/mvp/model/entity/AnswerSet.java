package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

public class AnswerSet {
    private String name;
    private List<Answer> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Answer> getList() {
        return list;
    }

    public void setList(List<Answer> list) {
        this.list = list;
    }
}
