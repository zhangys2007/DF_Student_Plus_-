package com.example.df.zhiyun.paper.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.HomeworkService;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.paper.mvp.contract.CardContract;
import com.example.df.zhiyun.mvp.model.entity.PaperAnswerSet;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2019 12:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CardModel extends BaseModel implements CardContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CardModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> submitHomeWork(String studentHomeWorkId,String homeworkId,
                                                   String uuid,int type,int subjectId,String schoolId) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("studentHomeWorkId", studentHomeWorkId);
        params.put("homeworkId", homeworkId);
        params.put("uuid", uuid);
        params.put("type", type);
        params.put("subjectId", subjectId);
        params.put("schoolId", schoolId);

        return mRepositoryManager
                .obtainRetrofitService(HomeworkService.class)
                .submitHomeWork(ParamsUtils.fromMap(mApplication,params));
    }

    @Override
    public Observable<BaseResponse<PaperAnswerSet>> answerSet(String studentHomeWorkId,int subjectId,
                                                              String schoolId,int jobType) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("studentHomeWorkId", studentHomeWorkId);
        params.put("subjectId", subjectId);
        params.put("schoolId", schoolId);
        params.put("jobType", jobType);


        return mRepositoryManager
                .obtainRetrofitService(HomeworkService.class)
                .answerCardV1(ParamsUtils.fromMap(mApplication,params));
    }
}