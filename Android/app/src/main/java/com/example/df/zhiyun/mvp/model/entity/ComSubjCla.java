package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

public class ComSubjCla {
    List<Tag> subjectList;
    List<Tag> classList;

    public List<Tag> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Tag> subjectList) {
        this.subjectList = subjectList;
    }

    public List<Tag> getClassList() {
        return classList;
    }

    public void setClassList(List<Tag> classList) {
        this.classList = classList;
    }
}
