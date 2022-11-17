package com.example.df.zhiyun.mvp.contract;

import com.example.df.zhiyun.mvp.model.entity.Answer;

//做作业集
public interface DoHomeworkContract {
    void initeWhiteAnswer(Answer answer);
    Answer getAnswer();
}
