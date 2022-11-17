package com.example.df.zhiyun.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/***
 * 错题详情
 */
public class ErrorDetail implements Parcelable {
    private int isCollection;
    private String questionId;
    private String answer;
    private List<QuestionOption> optionList;
    private String studentAnswer;
    private String analysis;
    private String questionType;
    private String content;
    private String examinationPoint;

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    public String getQuestionId() {
        return questionId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getAnswer() {
        return answer;
    }

    public void setOptionList(List<QuestionOption> optionList) {
        this.optionList = optionList;
    }
    public List<QuestionOption> getOptionList() {
        return optionList;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }
    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
    public String getAnalysis() {
        return analysis;
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

    public void setExaminationPoint(String examinationPoint) {
        this.examinationPoint = examinationPoint;
    }
    public String getExaminationPoint() {
        return examinationPoint;
    }

    public ErrorDetail() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.isCollection);
        dest.writeString(this.questionId);
        dest.writeString(this.answer);
        dest.writeTypedList(this.optionList);
        dest.writeString(this.studentAnswer);
        dest.writeString(this.analysis);
        dest.writeString(this.questionType);
        dest.writeString(this.content);
        dest.writeString(this.examinationPoint);
    }

    protected ErrorDetail(Parcel in) {
        this.isCollection = in.readInt();
        this.questionId = in.readString();
        this.answer = in.readString();
        this.optionList = in.createTypedArrayList(QuestionOption.CREATOR);
        this.studentAnswer = in.readString();
        this.analysis = in.readString();
        this.questionType = in.readString();
        this.content = in.readString();
        this.examinationPoint = in.readString();
    }

    public static final Creator<ErrorDetail> CREATOR = new Creator<ErrorDetail>() {
        @Override
        public ErrorDetail createFromParcel(Parcel source) {
            return new ErrorDetail(source);
        }

        @Override
        public ErrorDetail[] newArray(int size) {
            return new ErrorDetail[size];
        }
    };
}
