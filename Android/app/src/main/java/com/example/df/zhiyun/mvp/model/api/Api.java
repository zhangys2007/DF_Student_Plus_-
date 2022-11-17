/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.df.zhiyun.mvp.model.api;


import com.example.df.zhiyun.BuildConfig;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by JessYan on 08/05/2016 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Api {
    String APP_DOMAIN = BuildConfig.BASE_URL;

    int PageSize = 10;
    int PageStart = 1;

    //后台返回代表成功与否
    int success = 1;
    int faile = 0;
    int logout = 101;    //异地登陆

    //人员身份
     int TYPE_STUDENT = 1;
     int TYPE_TEACHER = 2;
     int TYPE_PARENT = 3;

     //消息的已读未读
    int STATUS_UNREAD = 0;
    int STATUS_READ = 1;

    //性别
    int SEX_MAN = 1;
    int SEX_WOMEN = 2;

    //科目类型
    int SUBJECT_ALL = 0;
    int SUBJECT_CHINESE = 23;
    int SUBJECT_MATH = 24;
    int SUBJECT_ENGLISH = 25;

    //题目类型
    int QUESTION_HEAD = 0;  //
    int QUESTION_SELECT = 1;  //选择题
    int QUESTION_INPUT = 2; //填空
    int QUESTION_BIG = 3; //大题
    int QUESTION_SIMPLE = 4; //简答题
    int QUESTION_CHINESE_BIG = 5; //语文大题，分屏
    int QUESTION_COMPOSITION = 6; //作文
    int QUESTION_LISTEN = 7; //听力
    int QUESTION_MULTIPLE_SELECT = 10; //多选

    //是否已经收藏
    int STORE_NOT =0;
    int STORE_YES =1;

    //用于student_answer_homework接口的区分
    int STUDUNTEN_ANSWER_TYPE_HOMEWORK = 0;
    int STUDUNTEN_ANSWER_TYPE_SYNC = 1; //学生端做同步练习，教师端教材习题预览
    int STUDUNTEN_ANSWER_TYPE_SEL = 2;  //教师端精选试卷预览，云作业
    int STUDUNTEN_ANSWER_TYPE_FORMED = 3;  //教师端已组试卷预览
    int STUDUNTEN_ANSWER_TYPE_HISTORY = 4;  //历次作业

    int FINISH = 1;
    int UNFINISH = 0;

    //搜索类型
    int SEARCH_PAPER = 0; //试卷
    int SEARCH_HOMEWORK = 1; //作业

    int DEFAULT_ANSWER_TIME = 60*60*2;

    String PROVIDER_AUTHOR = BuildConfig.APPLICATION_ID;

    //布置作业的类型
    int PUT_TYPE_CLOUD = 0;   //云作业
    int PUT_TYPE_FORMED = 1;    //已组作业
    int PUT_TYPE_SELECTED = 0;  //精选试题

    int PLAN_TYPE_PLAN = 0; //教案
    int PLAN_TYPE_DESINED = 1; //教学设计

    int STUDENT_ANSWER_STATUS_UNREAD = 0;
    int STUDENT_ANSWER_STATUS_READED = 1;

    int COUNT_TYPE_DATE = 1;
    int COUNT_TYPE_WEEK = 2;
    int COUNT_TYPE_MONTH = 3;

    int USAGE_COUNT_TYPE_DATE = 0;
    int USAGE_COUNT_TYPE_WEEK = 1;
    int USAGE_COUNT_TYPE_MONTH = 2;
}
