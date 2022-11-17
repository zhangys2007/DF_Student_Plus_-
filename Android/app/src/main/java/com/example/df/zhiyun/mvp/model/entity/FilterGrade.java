package com.example.df.zhiyun.mvp.model.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class FilterGrade implements IPickerViewData {
    private String gradeID;
    private String grade;
    private List<FilterClass> classList;

    public String getGradeID() {
        return gradeID;
    }

    public void setGradeID(String gradeID) {
        this.gradeID = gradeID;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<FilterClass> getClassList() {
        return classList;
    }

    public void setClassList(List<FilterClass> classList) {
        this.classList = classList;
    }

    @Override
    public String getPickerViewText() {
        return this.grade;
    }
}
