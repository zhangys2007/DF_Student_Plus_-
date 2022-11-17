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

import java.util.List;

import io.reactivex.Observable;

import com.example.df.zhiyun.main.mvp.model.entity.PushData;
import com.example.df.zhiyun.mvp.model.entity.AnalyItem;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.Edition;
import com.example.df.zhiyun.mvp.model.entity.Message;
import com.example.df.zhiyun.mvp.model.entity.Plan;
import com.example.df.zhiyun.mvp.model.entity.Post;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * ================================================
 * 存放通用的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface CommonService {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/v1/teacher/pushList")
    Observable<BaseResponse<List<PushData>>> getPosts(@Body RequestBody data);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("common/getMessageList")
    Observable<BaseResponse<List<Message>>> getMessages(@Body RequestBody data);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("common/updateMessage")
    Observable<BaseResponse> updateMessages(@Body RequestBody data);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("common/feedback")
    Observable<BaseResponse> feedback(@Body RequestBody data);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("common/dataAnalysis")
    Observable<BaseResponse<List<AnalyItem>>> dataAnalysis(@Body RequestBody data);  //数据分析接口

    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);  //下载数据

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("common/edition")
    Observable<BaseResponse<Edition>> getEdition(@Body RequestBody data);
}
