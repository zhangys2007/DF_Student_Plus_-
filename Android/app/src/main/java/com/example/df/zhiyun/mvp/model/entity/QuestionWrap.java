package com.example.df.zhiyun.mvp.model.entity;

import android.text.TextUtils;

import com.example.df.zhiyun.mvp.model.api.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2019-07-26 12:26:32
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class QuestionWrap {
    private String userAnswer;
    private String questionStem;
    private String questionId;
    private String hearingUrl;
    private List<QuestionOptionWrap> optionList;
    private String details;
    private String time;
    private String source;
    private String title;
    private String questionType;
    private String content;
    private int is_write;
    private String subjectId;
    private String questionNum="";
    private String analysis;
    private List<String> answer;
    private String studentAnswer;
    private int count;
    private int contentType;
    private int questionCommentCount;
    private int smallCount;

    private float questionSum;
    private int studentAnswerStatus;
    private float studentScore;
    private String comment;
    private String studentHomeworkAnswerId;
    private String studentId;
    private String knowledgeSystem;
    private float avg;
    private String measureTarget;
    private int correctCount;
    private int errorCount;
    private String scoring;
    private boolean scorreChanged;
    private boolean expand;
    private String hearing;
    private List<QuestionWrap> subQuestion;

    public static Question conver2Question(QuestionWrap source){
        if(source == null){
            return null;
        }
        Question target = new Question();
        target.setUserAnswer(source.getUserAnswer());
        target.setQuestionStem(source.getContent());
        target.setContent(source.getQuestionStem());
        target.setQuestionId(source.getQuestionId());
        target.setHearingUrl(source.getHearingUrl());


        //填空题和简答题要根据smallcount的数量转换对应的optionlist
        if(TextUtils.equals(source.getQuestionType(),Integer.toString(Api.QUESTION_INPUT))   ||
                TextUtils.equals(source.getQuestionType(),Integer.toString(Api.QUESTION_SIMPLE))){
            List<QuestionOption> opList = new ArrayList<>();
            QuestionOption op;
            for(int j=0;j<source.getSmallCount();j++){
                op = new QuestionOption();
                opList.add(op);
                op.setOption("");
                op.setOptionContent("");
            }
            target.setOptionList(opList);
        }else{
            if(source.getOptionList() != null){
                List<QuestionOption> opList = new ArrayList<>();
                for(QuestionOptionWrap optionWrap:source.getOptionList()){
                    opList.add(QuestionOptionWrap.conver2QuestionOption(optionWrap));
                }
                target.setOptionList(opList);
            }
        }

        target.setDetails(source.getDetails());
        target.setTime(source.getTime());
        target.setSource(source.getSource());
        target.setTitle(source.getTitle());
        target.setQuestionType(source.getQuestionType());
        target.setIs_write(source.getIs_write());
        target.setSubjectId(source.getSubjectId());
        target.setQuestionNum(source.getQuestionNum());
        target.setAnalysis(source.getAnalysis());
        if(source.getAnswer() != null && source.getAnswer().size() >0){
            StringBuilder builder = new StringBuilder();
            builder.append(source.getAnswer().get(0));
            for(int i=1;i<source.getAnswer().size();i++){
                builder.append(",");
                builder.append(source.getAnswer().get(i));
            }
            target.setAnswer(builder.toString());
        }else{
            target.setAnswer("");
        }

        target.setStudentAnswer(source.getStudentAnswer());
        target.setCount(source.getCount());
        target.setContentType(source.getContentType());
        target.setQuestionCommentCount(source.getQuestionCommentCount());
        target.setQuestionSum(source.getQuestionSum());
        target.setStudentAnswerStatus(source.getStudentAnswerStatus());
        target.setStudentScore(source.getStudentScore());
        target.setComment(source.getComment());
        target.setStudentHomeworkAnswerId(source.getStudentHomeworkAnswerId());
        target.setStudentId(source.getStudentId());
        target.setKnowledgeSystem(source.getKnowledgeSystem());
        target.setAvg(source.getAvg());
        target.setMeasureTarget(source.getMeasureTarget());
        target.setCorrectCount(source.getCorrectCount());
        target.setErrorCount(source.getErrorCount());
        target.setScoring(source.getScoring());
        target.setScorreChanged(source.isScorreChanged());
        target.setExpand(source.isExpand());
        target.setHearing(source.getHearing());
        List<QuestionWrap> sourceList = source.getSubQuestion();
        if(sourceList != null && sourceList.size() >0){
            List<Question> subList = new ArrayList<>();
            for(int i=0;i<sourceList.size();i++){
                subList.add(QuestionWrap.conver2Question(sourceList.get(i)));
            }
            target.setSubQuestion(subList);
        }else{
            target.setSubQuestion(new ArrayList<>());
        }

        return target;
    }

    public int getSmallCount() {
        return smallCount;
    }

    public void setSmallCount(int smallCount) {
        this.smallCount = smallCount;
    }

    public int getQuestionCommentCount() {
        return questionCommentCount;
    }

    public void setQuestionCommentCount(int questionCommentCount) {
        this.questionCommentCount = questionCommentCount;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getHearing() {
        return hearing;
    }

    public void setHearing(String hearing) {
        this.hearing = hearing;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isScorreChanged() {
        return scorreChanged;
    }

    public void setScorreChanged(boolean scorreChanged) {
        this.scorreChanged = scorreChanged;
    }

    public String getKnowledgeSystem() {
        return knowledgeSystem;
    }

    public void setKnowledgeSystem(String knowledgeSystem) {
        this.knowledgeSystem = knowledgeSystem;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public String getMeasureTarget() {
        return measureTarget;
    }

    public void setMeasureTarget(String measureTarget) {
        this.measureTarget = measureTarget;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public String getScoring() {
        return scoring;
    }

    public void setScoring(String scoring) {
        this.scoring = scoring;
    }

    public String getStudentHomeworkAnswerId() {
        return studentHomeworkAnswerId;
    }

    public void setStudentHomeworkAnswerId(String studentHomeworkAnswerId) {
        this.studentHomeworkAnswerId = studentHomeworkAnswerId;
    }



    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public float getQuestionSum() {
        return questionSum;
    }

    public void setQuestionSum(float questionSum) {
        this.questionSum = questionSum;
    }

    public int getStudentAnswerStatus() {
        return studentAnswerStatus;
    }

    public void setStudentAnswerStatus(int studentAnswerStatus) {
        this.studentAnswerStatus = studentAnswerStatus;
    }

    public float getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(float studentScore) {
        this.studentScore = studentScore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
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


    public List<QuestionWrap> getSubQuestion() {
        return subQuestion;
    }

    public void setSubQuestion(List<QuestionWrap> subQuestion) {
        this.subQuestion = subQuestion;
    }

    public String getQuestionStem() {
        return questionStem;
    }

    public void setQuestionStem(String questionStem) {
        this.questionStem = questionStem;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    public String getQuestionId() {
        return questionId;
    }

    public List<QuestionOptionWrap> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<QuestionOptionWrap> optionList) {
        this.optionList = optionList;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    public String getDetails() {
        return details;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public String getSource() {
        return source;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
    public String getQuestionType() {
        return questionType;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public int getIs_write() {
        return is_write;
    }

    public void setIs_write(int is_write) {
        this.is_write = is_write;
    }

}