package com.example.df.zhiyun.mvp.model.adapterEntity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class StudentsMultipleItem implements MultiItemEntity {
    private Object data;
    private String classId;
    private int subIndex;

    public int getSubIndex() {
        return subIndex;
    }

    public void setSubIndex(int subIndex) {
        this.subIndex = subIndex;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public StudentsMultipleItem(String cId,Object item, int si) {
        this.data = item;
        this.classId = cId;
        this.subIndex = si;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
