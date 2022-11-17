package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

public class CategoryMain {
    String id;
    String name;
    List<CategoryNode> list;
    int subjectId;
    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryNode> getList() {
        return list;
    }

    public void setList(List<CategoryNode> list) {
        this.list = list;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
