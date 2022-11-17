/**
 * Copyright 2019 bejson.com
 */
package com.example.df.zhiyun.mvp.model.entity;
import java.util.Date;

/**
 * Auto-generated: 2019-11-12 10:19:3
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class HistoryHWAnalyManager  {
    private int GROUP_BY_DERIVED_0;
    private String gradeName;
    private int GROUP_BY_DERIVED_1;
    private int gradeId;
    private Date ORDER_BY_DERIVED_0;
    private int unFinishCount;
    private int type;
    private int subjectId;
    private String homeworkName;
    private int fzPaperId;
    private int teacherStatus;
    private int finishCount;
    private int schoolId;
    private String time;
    private String subjectName;
    private float score;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setGROUP_BY_DERIVED_0(int GROUP_BY_DERIVED_0) {
        this.GROUP_BY_DERIVED_0 = GROUP_BY_DERIVED_0;
    }
    public int getGROUP_BY_DERIVED_0() {
        return GROUP_BY_DERIVED_0;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
    public String getGradeName() {
        return gradeName;
    }

    public void setGROUP_BY_DERIVED_1(int GROUP_BY_DERIVED_1) {
        this.GROUP_BY_DERIVED_1 = GROUP_BY_DERIVED_1;
    }
    public int getGROUP_BY_DERIVED_1() {
        return GROUP_BY_DERIVED_1;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }
    public int getGradeId() {
        return gradeId;
    }

    public void setORDER_BY_DERIVED_0(Date ORDER_BY_DERIVED_0) {
        this.ORDER_BY_DERIVED_0 = ORDER_BY_DERIVED_0;
    }
    public Date getORDER_BY_DERIVED_0() {
        return ORDER_BY_DERIVED_0;
    }

    public void setUnFinishCount(int unFinishCount) {
        this.unFinishCount = unFinishCount;
    }
    public int getUnFinishCount() {
        return unFinishCount;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
    public int getSubjectId() {
        return subjectId;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }
    public String getHomeworkName() {
        return homeworkName;
    }

    public void setFzPaperId(int fzPaperId) {
        this.fzPaperId = fzPaperId;
    }
    public int getFzPaperId() {
        return fzPaperId;
    }

    public void setTeacherStatus(int teacherStatus) {
        this.teacherStatus = teacherStatus;
    }
    public int getTeacherStatus() {
        return teacherStatus;
    }

    public void setFinishCount(int finishCount) {
        this.finishCount = finishCount;
    }
    public int getFinishCount() {
        return finishCount;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }
    public int getSchoolId() {
        return schoolId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public String getSubjectName() {
        return subjectName;
    }

}