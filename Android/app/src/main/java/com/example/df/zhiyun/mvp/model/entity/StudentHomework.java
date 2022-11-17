package com.example.df.zhiyun.mvp.model.entity;

public class StudentHomework {
    private String studentId;
    private String studentName;
    private String studentHomeworkId;
    private long time;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentHomeworkId() {
        return studentHomeworkId;
    }

    public void setStudentHomeworkId(String studentHomeworkId) {
        this.studentHomeworkId = studentHomeworkId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
