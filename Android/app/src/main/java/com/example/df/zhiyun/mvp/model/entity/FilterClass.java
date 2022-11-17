package com.example.df.zhiyun.mvp.model.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class FilterClass  implements IPickerViewData {
    private String classID;
    private String className;
    private List<FilterSubject> subject;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public List<FilterSubject> getSubject() {
        return subject;
    }

    public void setSubject(List<FilterSubject> subject) {
        this.subject = subject;
    }

    @Override
    public String getPickerViewText() {
        return this.className;
    }
}
