package com.example.df.zhiyun.mvp.model;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.mvp.contract.HomeworkOldContract;
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
 * Created by MVPArmsTemplate on 07/22/2019 10:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomeworkOldModel extends BaseModel implements HomeworkOldContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HomeworkOldModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseResponse<List<Homework>>> getHomework(int page,int subjectId,
                    String beginTime,String endTime) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("page",page);
        params.put("size", Api.PageSize);

        params.put("subjectId",subjectId == 0 ?"":subjectId);
        params.put("beginTime", TextUtils.isEmpty(beginTime)?"":beginTime);
        params.put("endTime",TextUtils.isEmpty(endTime)?"":endTime);

        return mRepositoryManager
                .obtainRetrofitService(HomeworkService.class)
                .homeworkOld(ParamsUtils.fromMap(mApplication,params));
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