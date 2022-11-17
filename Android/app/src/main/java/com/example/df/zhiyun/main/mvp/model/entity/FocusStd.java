package com.example.df.zhiyun.main.mvp.model.entity;

/**
 * 关注的学生
 */
public class FocusStd {
    private String studentId;
    private String realName;
    private String classId;
    private String name;
    private String className;
    private String id;
    private String pictureBase64;
    private int rise;   // 0下降  1上升 2 不变
    private int rink;

    public int getRise() {
        return rise;
    }

    public void setRise(int rise) {
        this.rise = rise;
    }

    public int getRink() {
        return rink;
    }

    public void setRink(int rink) {
        this.rink = rink;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictureBase64() {
        return pictureBase64;
    }

    public void setPictureBase64(String pictureBase64) {
        this.pictureBase64 = pictureBase64;
    }
}
