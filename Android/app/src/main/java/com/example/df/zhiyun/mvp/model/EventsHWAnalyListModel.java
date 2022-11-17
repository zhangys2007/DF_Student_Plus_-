package com.example.df.zhiyun.mvp.model;

import android.app.Application;
import android.text.TextUtils;

import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.mvp.contract.EventsHWAnalyListContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.Teacher;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.FilterClass;
import com.example.df.zhiyun.mvp.model.entity.FilterGrade;
import com.example.df.zhiyun.mvp.model.entity.FilterSubject;
import com.example.df.zhiyun.mvp.model.entity.GrowthTraceItem;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 10:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class EventsHWAnalyListModel extends BaseModel implements EventsHWAnalyListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EventsHWAnalyListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    /**
     * 解析第一层
     * @param data
     * @param op1
     * @param op2
     * @param op3
     */
    public void parseLevel1(List<FilterGrade> data,List<String> op1,List<List<String>> op2,List<List<List<String>>> op3){
        if(data == null || data.size() == 0){
            return ;
        }

        for(FilterGrade grade : data){
            if(TextUtils.isEmpty(grade.getGrade())){
                op1.add("--");
            }else{
                op1.add(grade.getGrade());
            }
            List<String> op2Sub = new ArrayList<>();
            op2.add(op2Sub);

            List<List<String>> op3Sub = new ArrayList<>();
            op3.add(op3Sub);

            parseLevel2(grade.getClassList(),op2Sub,op3Sub);
        }
    }

    /**
     * 解析第二层
     * @param data
     * @param op2
     * @param op3
     */
    private void parseLevel2(List<FilterClass> data,List<String> op2, List<List<String>> op3){
        if(data == null || data.size() == 0){
            return ;
        }

        for(FilterClass filterClass : data){
            if(TextUtils.isEmpty(filterClass.getClassName())){
                op2.add("--");
            }else{
                op2.add(filterClass.getClassName());
            }
            List<String> op3Sub = new ArrayList<>();
            op3.add(op3Sub);

            parseLevel3(filterClass.getSubject(),op3Sub);
        }
    }

    /**
     * 解析第三层
     * @param data
     */
    private void parseLevel3(List<FilterSubject> data, List<String> op3){
        if(data == null || data.size() == 0){
            return ;
        }

        for(FilterSubject filterSubject : data){
            if(TextUtils.isEmpty(filterSubject.getName())){
                op3.add("--");
            }else{
                op3.add(filterSubject.getName());
            }
        }
    }


    @Override
    public Observable<BaseResponse<List<GrowthTraceItem>>> growthTraceItems(int pageNo,String subjectId, String classId) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("subjectId",subjectId);
        params.put("classId",classId);
        params.put("pageNo",pageNo);
        params.put("size", Api.PageSize);

        return mRepositoryManager
                .obtainRetrofitService(Teacher.class)
                .studentImproveHistory(ParamsUtils.fromMap(mApplication,params));
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