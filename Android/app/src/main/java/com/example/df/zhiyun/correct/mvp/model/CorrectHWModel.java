package com.example.df.zhiyun.correct.mvp.model;

import android.app.Application;

import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWContract;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.Teacher;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.HomeworkSet;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CorrectHWModel extends BaseModel implements CorrectHWContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CorrectHWModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<HomeworkSet>> homeworkCorrecting(int type, String studentHomeworkId, int studentCount) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("studentHomeWorkId", studentHomeworkId);
        params.put("type", type);
        params.put("studentCount", studentCount);


        return mRepositoryManager
                .obtainRetrofitService(Teacher.class)
                .homeworkCorrecting(ParamsUtils.fromMap(mApplication,params));
    }

    @Override
    public Observable<BaseResponse> questionCorrect(Question question, String subjectId, String studentHomeworkId) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());

        params.put("studentHomeworkId", studentHomeworkId);
        params.put("subjectId", subjectId);

        params.put("correct", question.getCorrectParam());

        return mRepositoryManager
                .obtainRetrofitService(Teacher.class)
                .questionsCorrect(ParamsUtils.fromMap(mApplication,params));
    }
}