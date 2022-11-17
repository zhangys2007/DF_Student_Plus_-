package com.example.df.zhiyun.mvp.model.entity;

import java.util.ArrayList;
import java.util.List;

//用于新版和旧版的转换
public class HomeworkSetWrap {
    private String teachId;
    private String hearingUrl;
    private String studentHomeWorkId;
    private long time;
    private List<QuestionWrap> list;
    private String subjectId;
    private String columnName;
    private int isPay;

    public static HomeworkSet conver2HomeworkSet(HomeworkSetWrap source){
        if(source == null){
            return null;
        }
        HomeworkSet target = new HomeworkSet();
        target.setTeachId(source.getTeachId());
        target.setHearingUrl(source.getHearingUrl());
        target.setStudentHomeWorkId(source.getStudentHomeWorkId());
        target.setTime(source.getTime());
        target.setSubjectId(source.getSubjectId());
        target.setColumnName(source.getColumnName());
        target.setIsPay(source.getIsPay());
        if(source.getList() != null && source.getList().size() > 0){
            List<Question> targetList = new ArrayList<>();
            for(QuestionWrap item:source.getList()){
                targetList.add(QuestionWrap.conver2Question(item));
            }

            target.setList(targetList);
        }

        return target;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public String getTeachId() {
        return teachId;
    }

    public void setTeachId(String teachId) {
        this.teachId = teachId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getHearingUrl() {
        return hearingUrl;
    }

    public void setHearingUrl(String hearingUrl) {
        this.hearingUrl = hearingUrl;
    }

    public String getStudentHomeWorkId() {
        return studentHomeWorkId;
    }

    public void setStudentHomeWorkId(String studentHomeWorkId) {
        this.studentHomeWorkId = studentHomeWorkId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<QuestionWrap> getList() {
        return list;
    }

    public void setList(List<QuestionWrap> list) {
        this.list = list;
    }


}
