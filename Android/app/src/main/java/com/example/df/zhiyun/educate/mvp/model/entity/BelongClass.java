package com.example.df.zhiyun.educate.mvp.model.entity;

public class BelongClass {
    private String classId;
    private String className;
    private int isArrangement;
    private int isEndPut;

    public int getIsEndPut() {
        return isEndPut;
    }

    public void setIsEndPut(int isEndPut) {
        this.isEndPut = isEndPut;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getIsArrangement() {
        return isArrangement;
    }

    public void setIsArrangement(int isArrangement) {
        this.isArrangement = isArrangement;
    }
}
