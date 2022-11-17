package com.example.df.zhiyun.mvp.model.entity;

/**
 * Auto-generated: 2019-08-06 11:14:49
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class HomeworkArrange {

    private int unApprovedNumber;
    private int unpaidNumber;
    private int approvedNumber;
    private String homeworkId;
    private String createTime;
    private int homeworkStatus;
    private String homeworkName;
    public void setUnApprovedNumber(int unApprovedNumber) {
        this.unApprovedNumber = unApprovedNumber;
    }
    public int getUnApprovedNumber() {
        return unApprovedNumber;
    }

    public void setUnpaidNumber(int unpaidNumber) {
        this.unpaidNumber = unpaidNumber;
    }
    public int getUnpaidNumber() {
        return unpaidNumber;
    }

    public void setApprovedNumber(int approvedNumber) {
        this.approvedNumber = approvedNumber;
    }
    public int getApprovedNumber() {
        return approvedNumber;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getCreateTime() {
        return createTime;
    }

    public int getHomeworkStatus() {
        return homeworkStatus;
    }

    public void setHomeworkStatus(int homeworkStatus) {
        this.homeworkStatus = homeworkStatus;
    }

    public String getHomeworkName() {
        return homeworkName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }
}