package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

/***
 * 错题详情外面的包装
 */
public class ErrorDetailWrap {
    private String studentHomeWorkName;
    private String time;
    private List<ErrorDetail> list;
    private String studentHomeWorkId;
    public void setStudentHomeWorkName(String studentHomeWorkName) {
        this.studentHomeWorkName = studentHomeWorkName;
    }
    public String getStudentHomeWorkName() {
        return studentHomeWorkName;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }

    public void setList(List<ErrorDetail> list) {
        this.list = list;
    }
    public List<ErrorDetail> getList() {
        return list;
    }

    public void setStudentHomeWorkId(String studentHomeWorkId) {
        this.studentHomeWorkId = studentHomeWorkId;
    }
    public String getStudentHomeWorkId() {
        return studentHomeWorkId;
    }
}
