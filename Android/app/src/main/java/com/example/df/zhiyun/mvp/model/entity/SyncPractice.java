package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

public class SyncPractice {
    private String learningSection;
    private String semester;
    private String textbookEdition;
    private List<CategoryMain> list;

    public String getLearningSection() {
        return learningSection;
    }

    public void setLearningSection(String learningSection) {
        this.learningSection = learningSection;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTextbookEdition() {
        return textbookEdition;
    }

    public void setTextbookEdition(String textbookEdition) {
        this.textbookEdition = textbookEdition;
    }

    public List<CategoryMain> getList() {
        return list;
    }

    public void setList(List<CategoryMain> list) {
        this.list = list;
    }
}
