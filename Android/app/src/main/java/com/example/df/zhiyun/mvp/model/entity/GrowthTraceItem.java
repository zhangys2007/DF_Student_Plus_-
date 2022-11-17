package com.example.df.zhiyun.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class GrowthTraceItem implements Parcelable {
    private String studentId;
    private String realName;
    private String homeworkName;
    private String classId;
    private String studentName;
    private float studentScore;
    private String className;
    private String subjectName;
    private String gradeName;
    private String picture;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getRealName() {
        return realName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }
    public String getHomeworkName() {
        return homeworkName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getStudentName() {
        return studentName;
    }

    public void setStudentScore(float studentScore) {
        this.studentScore = studentScore;
    }
    public float getStudentScore() {
        return studentScore;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    public String getClassName() {
        return className;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public String getSubjectName() {
        return subjectName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.studentId);
        dest.writeString(this.realName);
        dest.writeString(this.homeworkName);
        dest.writeString(this.classId);
        dest.writeString(this.studentName);
        dest.writeFloat(this.studentScore);
        dest.writeString(this.className);
        dest.writeString(this.subjectName);
        dest.writeString(this.gradeName);
        dest.writeString(this.picture);
    }

    public GrowthTraceItem() {
    }

    protected GrowthTraceItem(Parcel in) {
        this.studentId = in.readString();
        this.realName = in.readString();
        this.homeworkName = in.readString();
        this.classId = in.readString();
        this.studentName = in.readString();
        this.studentScore = in.readFloat();
        this.className = in.readString();
        this.subjectName = in.readString();
        this.gradeName = in.readString();
        this.picture = in.readString();
    }

    public static final Parcelable.Creator<GrowthTraceItem> CREATOR = new Parcelable.Creator<GrowthTraceItem>() {
        @Override
        public GrowthTraceItem createFromParcel(Parcel source) {
            return new GrowthTraceItem(source);
        }

        @Override
        public GrowthTraceItem[] newArray(int size) {
            return new GrowthTraceItem[size];
        }
    };
}
