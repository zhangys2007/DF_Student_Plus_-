package com.example.df.zhiyun.main.mvp.model.entity;

/**
 * 待做的事情
 */
public class TodoItem {
    private String homeworkId;
    private String homeworkName;
    private String classId;
    private String schoolId;
    private String className;
    private int studentCount;
    private long createTime;
    private int paidNumber;
    private int unpaidNumber;
    private int approvedNumber;
    private int unApprovedNumber;

    public int getUnpaidNumber() {
        return unpaidNumber;
    }

    public void setUnpaidNumber(int unpaidNumber) {
        this.unpaidNumber = unpaidNumber;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getHomeworkName() {
        return homeworkName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getPaidNumber() {
        return paidNumber;
    }

    public void setPaidNumber(int paidNumber) {
        this.paidNumber = paidNumber;
    }

    public int getApprovedNumber() {
        return approvedNumber;
    }

    public void setApprovedNumber(int approvedNumber) {
        this.approvedNumber = approvedNumber;
    }

    public int getUnApprovedNumber() {
        return unApprovedNumber;
    }

    public void setUnApprovedNumber(int unApprovedNumber) {
        this.unApprovedNumber = unApprovedNumber;
    }
}
