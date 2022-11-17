package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

public class CompearItem {
    private List<CompearQuestion> gradeQuestionList;
    private List<CompearScore> classScoreList;
    private List<CompearCla> classList;

    public List<CompearQuestion> getGradeQuestionList() {
        return gradeQuestionList;
    }

    public void setGradeQuestionList(List<CompearQuestion> gradeQuestionList) {
        this.gradeQuestionList = gradeQuestionList;
    }

    public List<CompearScore> getClassScoreList() {
        return classScoreList;
    }

    public void setClassScoreList(List<CompearScore> classScoreList) {
        this.classScoreList = classScoreList;
    }

    public List<CompearCla> getClassList() {
        return classList;
    }

    public void setClassList(List<CompearCla> classList) {
        this.classList = classList;
    }
}
