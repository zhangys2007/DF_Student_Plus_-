package com.example.df.zhiyun.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 批改内容
 */
public class CorrectContent implements Serializable {
    private String questionId;
    private String comment;
    private float studentScore;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(float studentScore) {
        this.studentScore = studentScore;
    }
}
