package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

public class AnalyReport {
    private String homeworkId;
    private String homeworkName;
    private int homeworkStatus;
    private float studentHomeworkScore;
    private String subjectId;
//    private int fzPaperId;
    private int type;
    private String realName;
    private float score;
    private String gradeName;
    private String name;
//    private int highestScore;
//    private int lowestScore;
//    private int assignCount;
    private int count;
//    private int unAssignCount;
//    private int submitCount;
//    private int unSubmitCount;
//    private int correctCount;
//    private int unCorrectCount;
    private int timeLenth;
    private String className;
    private List<QuestionScore> questionScoreList;

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

    public int getHomeworkStatus() {
        return homeworkStatus;
    }

    public void setHomeworkStatus(int homeworkStatus) {
        this.homeworkStatus = homeworkStatus;
    }

    public float getStudentHomeworkScore() {
        return studentHomeworkScore;
    }

    public void setStudentHomeworkScore(float studentHomeworkScore) {
        this.studentHomeworkScore = studentHomeworkScore;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTimeLenth() {
        return timeLenth;
    }

    public void setTimeLenth(int timeLenth) {
        this.timeLenth = timeLenth;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<QuestionScore> getQuestionScoreList() {
        return questionScoreList;
    }

    public void setQuestionScoreList(List<QuestionScore> questionScoreList) {
        this.questionScoreList = questionScoreList;
    }
}
