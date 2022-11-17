package com.example.df.zhiyun.mvp.model.entity;

import com.contrarywind.interfaces.IPickerViewData;

public class FilterSubject implements IPickerViewData {
    String name;
    String subjectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }
}
