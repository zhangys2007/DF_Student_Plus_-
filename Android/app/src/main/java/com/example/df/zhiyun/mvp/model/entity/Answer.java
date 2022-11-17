package com.example.df.zhiyun.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Answer implements Parcelable , Serializable {
        private static final long serialVersionUID= -34975938475934934L;
        private String questionId;
        private List<String> answer;
        private List<Integer> types;
        private String questionNum;
        private String isWrite;
        private String isRight;
        private List<com.example.df.zhiyun.mvp.model.entity.Answer> subAnswer;

        public String getIsRight() {
            return isRight;
        }

        public void setIsRight(String isRight) {
            this.isRight = isRight;
        }

        public List<Integer> getTypes() {
            return types;
        }

        public void setTypes(List<Integer> types) {
            this.types = types;
        }

        public List<com.example.df.zhiyun.mvp.model.entity.Answer> getSubAnswer() {
            return subAnswer;
        }

        public void setSubAnswer(List<com.example.df.zhiyun.mvp.model.entity.Answer> subAnswer) {
            this.subAnswer = subAnswer;
        }

        public String getIsWrite() {
            return isWrite;
        }

        public void setIsWrite(String isWrite) {
            this.isWrite = isWrite;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
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

        public Answer() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.questionId);
            dest.writeStringList(this.answer);
            dest.writeList(this.types);
            dest.writeString(this.questionNum);
            dest.writeString(this.isWrite);
            dest.writeTypedList(this.subAnswer);
        }

        protected Answer(Parcel in) {
            this.questionId = in.readString();
            this.answer = in.createStringArrayList();
            this.types = new ArrayList<Integer>();
            in.readList(this.types, Integer.class.getClassLoader());
            this.questionNum = in.readString();
            this.isWrite = in.readString();
            this.subAnswer = in.createTypedArrayList(com.example.df.zhiyun.mvp.model.entity.Answer.CREATOR);
        }

        public static final Creator<com.example.df.zhiyun.mvp.model.entity.Answer> CREATOR = new Creator<com.example.df.zhiyun.mvp.model.entity.Answer>() {
            @Override
            public com.example.df.zhiyun.mvp.model.entity.Answer createFromParcel(Parcel source) {
                return new com.example.df.zhiyun.mvp.model.entity.Answer(source);
            }

            @Override
            public com.example.df.zhiyun.mvp.model.entity.Answer[] newArray(int size) {
                return new com.example.df.zhiyun.mvp.model.entity.Answer[size];
            }
        };
}
