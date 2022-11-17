package com.example.df.zhiyun.analy.mvp.model;

import android.app.Application;

import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.analy.mvp.contract.GrowthTraceContract;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.Teacher;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.StudentImprove;
import com.google.gson.Gson;
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
 * Created by MVPArmsTemplate on 10/24/2019 10:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GrowthTraceModel extends BaseModel implements GrowthTraceContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GrowthTraceModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

//    @Override
//    public Observable<BaseResponse<ComSubjCla>> findClassSubjectByStudent() {
//        Map<String,Object> params = new HashMap<>();
//        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
//        return mRepositoryManager
//                .obtainRetrofitService(Teacher.class)
//                .findClassSubjectByStudent(ParamsUtils.fromMap(mApplication,params));
//    }


    @Override
    public Observable<BaseResponse<List<StudentImprove>>> studentImprove(String classId, String subjectId, String studentId) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("classId",classId);
        params.put("subjectId",subjectId);
        params.put("studentId",studentId);
        return mRepositoryManager
                .obtainRetrofitService(Teacher.class)
                .studentImprove(ParamsUtils.fromMap(mApplication,params));
    }
}