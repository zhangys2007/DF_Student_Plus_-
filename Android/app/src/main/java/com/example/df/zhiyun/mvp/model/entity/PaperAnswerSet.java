package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

public class PaperAnswerSet {
    private String title;
    private List<AnswerSet> list;
    private String studentHomeWorkId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AnswerSet> getList() {
        return list;
    }

    public void setList(List<AnswerSet> list) {
        this.list = list;
    }

    public String getStudentHomeWorkId() {
        return studentHomeWorkId;
    }

    public void setStudentHomeWorkId(String studentHomeWorkId) {
        this.studentHomeWorkId = studentHomeWorkId;
    }
}
