package com.example.df.zhiyun.mvp.model.entity;

public class CompearScore {
    private float score;
    private String homeworkId;
    private String questionId;
    private float scoreRate;
    private String knowledgeId;
    private String knowledge;

    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public float getScoreRate() {
        return scoreRate;
    }

    public void setScoreRate(float scoreRate) {
        this.scoreRate = scoreRate;
    }
}
