package com.example.df.zhiyun.analy.mvp.model;

import android.app.Application;

import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.analy.mvp.contract.HistoryHomeworkAnalyContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.Teacher;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.FilterGrade;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 15:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class HistoryHomeworkAnalyModel extends BaseModel implements HistoryHomeworkAnalyContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HistoryHomeworkAnalyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseResponse<List<JsonObject>>> findHomeworkAnalysis(int pageNo, String subjectId, String classId, String gradeId) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("subjectId",subjectId);
        params.put("classId",classId);
        params.put("pageNo",pageNo);
        params.put("gradeId",gradeId);
        params.put("pageSize", Api.PageSize);

        return mRepositoryManager
                .obtainRetrofitService(Teacher.class)
                .findHomeworkAnalysis(ParamsUtils.fromMap(mApplication,params));
    }

    @Override
    public Observable<BaseResponse<List<FilterGrade>>> findAnalysisDict() {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());

        return mRepositoryManager
                .obtainRetrofitService(Teacher.class)
                .findAnalysisDict(ParamsUtils.fromMap(mApplication,params));
    }
}