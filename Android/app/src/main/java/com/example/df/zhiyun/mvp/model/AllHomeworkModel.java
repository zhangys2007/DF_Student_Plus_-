package com.example.df.zhiyun.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.mvp.contract.AllHomeworkContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.HomeworkService;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.Homework;
import com.example.df.zhiyun.mvp.model.entity.Subject;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 11:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AllHomeworkModel extends BaseModel implements AllHomeworkContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AllHomeworkModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<Homework>>> getHomework(int page) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("page",page);
        params.put("size", Api.PageSize);
        return mRepositoryManager
                .obtainRetrofitService(HomeworkService.class)
                .homeworkNewest(ParamsUtils.fromMap(mApplication,params));
    }

    @Override
    public Observable<BaseResponse<List<Subject>>> getSubject() {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        return mRepositoryManager
                .obtainRetrofitService(HomeworkService.class)
                .getSubject(ParamsUtils.fromMap(mApplication,params));
    }
}