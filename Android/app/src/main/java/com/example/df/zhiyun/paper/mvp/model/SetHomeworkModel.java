package com.example.df.zhiyun.paper.mvp.model;

import android.app.Application;
import android.text.TextUtils;

import com.example.df.zhiyun.mvp.model.entity.HomeworkSetWrap;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.HomeworkService;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.HomeworkSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.paper.mvp.contract.SetHomeworkContract;


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
public class SetHomeworkModel extends BaseModel implements SetHomeworkContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SetHomeworkModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<HomeworkSet>> studentDoHomework(String studentHomeWorkId, String uuid,
                                                               int subjectId,int jobType, String schoolId) {

        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("studentHomeWorkId", studentHomeWorkId);
        params.put("uuid", uuid);
        params.put("subjectId", subjectId);
        params.put("type", 0);  //做作业/考试
        params.put("schoolId", schoolId);
        params.put("jobType", jobType);


        return mRepositoryManager
                .obtainRetrofitService(HomeworkService.class)
                .studentDoHomework(ParamsUtils.fromMap(mApplication,params))
                .map(new Function<BaseResponse<HomeworkSetWrap>, BaseResponse<HomeworkSet>>() {
                    @Override
                    public BaseResponse<HomeworkSet> apply(BaseResponse<HomeworkSetWrap> response) throws Exception {
                        BaseResponse<HomeworkSet> realResponse = new BaseResponse<HomeworkSet>();
                        realResponse.setCode(response.getCode());
                        realResponse.setMessage(response.getMessage());
                        realResponse.setData(HomeworkSetWrap.conver2HomeworkSet(response.getData()));
                        return realResponse;
                    }
                });
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
    public Observable<BaseResponse> submitQuestion(String studentHomeWorkId,String questionId,String uuid,int type, Answer answer,String schoolId) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("studentHomeWorkId", studentHomeWorkId);
        params.put("questionId", questionId);
        params.put("uuid", uuid);
        params.put("type", type);
        params.put("schoolId", schoolId);

        parseAnswer(params,answer);

        return mRepositoryManager
                .obtainRetrofitService(HomeworkService.class)
                .submitQuestion(ParamsUtils.fromMap(mApplication,params));
    }

    private void parseAnswer(Map<String,Object> params,  Answer answer){
        if(answer.getSubAnswer() != null){
            List<Map<String,Object>> subList = new ArrayList<>();
            Map<String,Object> tempSub;
            for(Answer subAnswer: answer.getSubAnswer()){
                tempSub = new HashMap<>();
                tempSub.put("questionId",subAnswer.getQuestionId());
                parseAnswer(tempSub,subAnswer);
                subList.add(tempSub);
            }
            params.put("answer",subList);
        }else{
            params.put("answer",answer.getAnswer());
        }
    }
}