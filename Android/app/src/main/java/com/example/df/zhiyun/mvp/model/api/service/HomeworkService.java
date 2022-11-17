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
package com.example.df.zhiyun.mvp.model.api.service;

import com.example.df.zhiyun.mvp.model.entity.HomeworkSetWrap;
import com.google.gson.JsonObject;
import com.example.df.zhiyun.mvp.model.entity.BookGrade;

import java.util.List;

import io.reactivex.Observable;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.ErrorDetail;
import com.example.df.zhiyun.mvp.model.entity.ExerSet;
import com.example.df.zhiyun.mvp.model.entity.Homework;
import com.example.df.zhiyun.mvp.model.entity.HomeworkSet;
import com.example.df.zhiyun.mvp.model.entity.PaperAnswerSet;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.model.entity.StoreSet;
import com.example.df.zhiyun.mvp.model.entity.Subject;
import com.example.df.zhiyun.mvp.model.entity.SyncPractice;
import com.example.df.zhiyun.mvp.model.entity.VersionPublisher;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于作业的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface HomeworkService {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/practiceRecords")
    Observable<BaseResponse<ExerSet>> exerSets(@Body RequestBody data);  //练习集

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/newHomework")
    Observable<BaseResponse<List<Homework>>> homeworkNewest(@Body RequestBody data);  //最新作业

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/oldHomework")
    Observable<BaseResponse<List<Homework>>> homeworkOld(@Body RequestBody data);  //历次作业


    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("common/getSubject")
    Observable<BaseResponse<List<Subject>>> getSubject(@Body RequestBody data);  //学科

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("common/getSubjectId")
    Observable<BaseResponse<List<Subject>>> getSubjectId(@Body RequestBody data);  //学科,不带全部

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/wrongQuestion")
    Observable<BaseResponse<List<Question>>> getWrongQuestions(@Body RequestBody data);  //错题集

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/wrongQuestionDetailedExplanation")
    Observable<BaseResponse<List<ErrorDetail>>> getWrongQuestionDetail(@Body RequestBody data);  //错题详情

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/updateCollection")
    Observable<BaseResponse> updateCollection(@Body RequestBody data);  //修改收藏

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/collectionList")
    Observable<BaseResponse<StoreSet>> collectionList(@Body RequestBody data);  //收藏列表

//    @Headers({"Content-Type: application/json","Accept: application/json"})
//    @POST("student/studentAnswerHomework")
//    Observable<BaseResponse<HomeworkSet>> getHomeworkSet(@Body RequestBody data);  //学生作业集

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/v1/teacher/studentDoHomework")
    Observable<BaseResponse<HomeworkSetWrap>> studentDoHomework(@Body RequestBody data);  //学生作业集

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/studentAnswerHomework")
    Observable<BaseResponse<HomeworkSetWrap>> getHomeworkSet(@Body RequestBody data);  //学生作业集

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/submitHomework")
    Observable<BaseResponse> submitHomeworkSet(@Body RequestBody data);  //提交作业集

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/answerCard")
    Observable<BaseResponse<PaperAnswerSet>> answerCard(@Body RequestBody data);  //答题卡

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/v1/teacher/answerCard")
    Observable<BaseResponse<PaperAnswerSet>> answerCardV1(@Body RequestBody data);  //答题卡

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/v1/student/submitQuestion")
    Observable<BaseResponse> submitQuestion(@Body RequestBody data);  //提交问题

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/v1/student/submitHomeWork")
    Observable<BaseResponse> submitHomeWork(@Body RequestBody data);  //提交问题

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/homeSearch")
    Observable<BaseResponse<List<JsonObject>>> search(@Body RequestBody data);  //搜索

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("common/getTextbookSystem")
    Observable<BaseResponse<SyncPractice>> bookSystem(@Body RequestBody data);  //同步练习教材体系列表

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("common/getGrade")
    Observable<BaseResponse<List<BookGrade>>> bookGrade(@Body RequestBody data);  //同步练习年级

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("common/getTextBookVersion")
    Observable<BaseResponse<List<VersionPublisher>>> bookVersion(@Body RequestBody data);  //同步练习教材版本
}
