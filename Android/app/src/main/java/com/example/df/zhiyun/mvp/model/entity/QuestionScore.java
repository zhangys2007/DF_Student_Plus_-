package com.example.df.zhiyun.mvp.model.entity;

public class QuestionScore {
    private String difficulty;
    private float score;
    private float average;
    private String questionId;
    private String measureTarget;
    private float classScoreRate;
    private String typeName;
    private float gradeErrorRate;
    private String uuid;
    private String standardDeviation;
    private String knowledge;
    private float discriminativePower;
    private float studentQuestionScore;

    public float getStudentQuestionScore() {
        return studentQuestionScore;
    }

    public void setStudentQuestionScore(float studentQuestionScore) {
        this.studentQuestionScore = studentQuestionScore;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getMeasureTarget() {
        return measureTarget;
    }

    public void setMeasureTarget(String measureTarget) {
        this.measureTarget = measureTarget;
    }

    public float getClassScoreRate() {
        return classScoreRate;
    }

    public void setClassScoreRate(float classScoreRate) {
        this.classScoreRate = classScoreRate;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public float getGradeErrorRate() {
        return gradeErrorRate;
    }

    public void setGradeErrorRate(float gradeErrorRate) {
        this.gradeErrorRate = gradeErrorRate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(String standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public float getDiscriminativePower() {
        return discriminativePower;
    }

    public void setDiscriminativePower(float discriminativePower) {
        this.discriminativePower = discriminativePower;
    }
}
