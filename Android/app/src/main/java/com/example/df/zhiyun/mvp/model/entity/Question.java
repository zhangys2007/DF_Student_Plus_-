package com.example.df.zhiyun.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.df.zhiyun.mvp.model.api.Api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Auto-generated: 2019-07-26 12:26:32
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Question implements Parcelable {
    private String userAnswer;
    private String questionStem;
    private String questionId;
    private String hearingUrl;
    private List<QuestionOption> optionList;
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
    private String answer;
    private String studentAnswer;
    private int count;
    private int contentType;
    private int questionCommentCount;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
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

    private List<Question> subQuestion;

    public List<Question> getSubQuestion() {
        return subQuestion;
    }

    public void setSubQuestion(List<Question> subQuestion) {
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

    public List<QuestionOption> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<QuestionOption> optionList) {
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

    public Question() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.questionStem);
        dest.writeString(this.questionId);
        dest.writeString(this.hearingUrl);
        dest.writeTypedList(this.optionList);
        dest.writeString(this.details);
        dest.writeString(this.time);
        dest.writeString(this.source);
        dest.writeString(this.title);
        dest.writeString(this.questionType);
        dest.writeString(this.content);
        dest.writeInt(this.is_write);
        dest.writeString(this.subjectId);
        dest.writeString(this.questionNum);
        dest.writeString(this.analysis);
        dest.writeString(this.answer);
        dest.writeString(this.studentAnswer);
        dest.writeInt(this.count);
        dest.writeFloat(this.questionSum);
        dest.writeInt(this.studentAnswerStatus);
        dest.writeFloat(this.studentScore);
        dest.writeString(this.comment);
        dest.writeString(this.studentHomeworkAnswerId);
        dest.writeString(this.studentId);
        dest.writeString(this.knowledgeSystem);
        dest.writeFloat(this.avg);
        dest.writeString(this.measureTarget);
        dest.writeInt(this.correctCount);
        dest.writeInt(this.errorCount);
        dest.writeString(this.scoring);
        dest.writeByte(this.scorreChanged ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.subQuestion);
    }

    protected Question(Parcel in) {
        this.questionStem = in.readString();
        this.questionId = in.readString();
        this.hearingUrl = in.readString();
        this.optionList = in.createTypedArrayList(QuestionOption.CREATOR);
        this.details = in.readString();
        this.time = in.readString();
        this.source = in.readString();
        this.title = in.readString();
        this.questionType = in.readString();
        this.content = in.readString();
        this.is_write = in.readInt();
        this.subjectId = in.readString();
        this.questionNum = in.readString();
        this.analysis = in.readString();
        this.answer = in.readString();
        this.studentAnswer = in.readString();
        this.count = in.readInt();
        this.questionSum = in.readFloat();
        this.studentAnswerStatus = in.readInt();
        this.studentScore = in.readFloat();
        this.comment = in.readString();
        this.studentHomeworkAnswerId = in.readString();
        this.studentId = in.readString();
        this.knowledgeSystem = in.readString();
        this.avg = in.readFloat();
        this.measureTarget = in.readString();
        this.correctCount = in.readInt();
        this.errorCount = in.readInt();
        this.scoring = in.readString();
        this.scorreChanged = in.readByte() != 0;
        this.subQuestion = in.createTypedArrayList(Question.CREATOR);
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };


    /**
     * 获取批改的数据,已批阅的不用提交,未批改的不用提交
     * @return
     */
    public Map<String,Object> getCorrectParam(){

        if(getStudentAnswerStatus() == Api.STUDENT_ANSWER_STATUS_READED ){  //已批阅的不需要提交
            return null;
        }

        Map<String,Object> data = new HashMap<>();
        data.put("comment", getComment());
        data.put("studentScore", getStudentScore());
        data.put("questionId", getQuestionId());

        if(getSubQuestion() != null && getSubQuestion().size() > 0){  //有子问题
            List<Map<String,Object>> subCorrect = new ArrayList<>();
            data.put("subCorrect",subCorrect);
            for(Question subq:getSubQuestion()){
                Map<String,Object> temp = subq.getCorrectParam();
                if(temp != null){
                    subCorrect.add(temp);
                }
            }
            if(subCorrect.size() == 0){    //没有子问题的父问题也不需要提交
                return null;
            }else{
                return data;
            }
        }else{    //没有子问题的
            if(isScorreChanged() == false){  //没被评分
                return null;
            }else{
                List<Map<String,Object>> subCorrect = new ArrayList<>();
                data.put("subCorrect",subCorrect);
                return data;
            }
        }
    }
}