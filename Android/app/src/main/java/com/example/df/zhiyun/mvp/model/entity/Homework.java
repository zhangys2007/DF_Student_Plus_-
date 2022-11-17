package com.example.df.zhiyun.mvp.model.entity;

/**
 * Auto-generated: 2019-07-20 11:11:44
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Homework {
    private int correctionStatus;
    private String homeworkName;
    private String studentHomeworkId;
    private String subject;
    private String beginTime;
    private String endTime;
    private int type;
    private int isHide;
    private int status;
    private int studentCount;
    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }
    public String getHomeworkName() {
        return homeworkName;
    }

    public void setStudentHomeworkId(String studentHomeworkId) {
        this.studentHomeworkId = studentHomeworkId;
    }
    public String getStudentHomeworkId() {
        return studentHomeworkId;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getSubject() {
        return subject;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    public String getBeginTime() {
        return beginTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getEndTime() {
        return endTime;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public void setIsHide(int isHide) {
        this.isHide = isHide;
    }
    public int getIsHide() {
        return isHide;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public int getCorrectionStatus() {
        return correctionStatus;
    }

    public void setCorrectionStatus(int correctionStatus) {
        this.correctionStatus = correctionStatus;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }
}