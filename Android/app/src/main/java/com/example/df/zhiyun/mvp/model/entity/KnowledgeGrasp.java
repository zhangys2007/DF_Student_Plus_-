/**
 * Copyright 2019 bejson.com
 */
package com.example.df.zhiyun.mvp.model.entity;

/**
 * Auto-generated: 2019-11-13 14:25:2
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class KnowledgeGrasp {

    private int knowledgeId;
    private float score;
    private String questionId;
    private float scoreRate;
    private String knowledge;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getScoreRate() {
        return scoreRate;
    }

    public void setScoreRate(float scoreRate) {
        this.scoreRate = scoreRate;
    }

    public void setKnowledgeId(int knowledgeId) {
        this.knowledgeId = knowledgeId;
    }
    public int getKnowledgeId() {
        return knowledgeId;
    }


    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }
    public String getKnowledge() {
        return knowledge;
    }

}