package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

public class AnalysisDict {
    private int isClass;
    private List<Tag> subjectList;
    private List<Tag> classList;

    public int getIsClass() {
        return isClass;
    }

    public void setIsClass(int isClass) {
        this.isClass = isClass;
    }

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
