package com.example.df.zhiyun.comment.mvp.model;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.comment.mvp.contract.CommentListContract;
import com.example.df.zhiyun.mvp.model.api.service.Teacher;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.model.entity.CommentItem;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 10:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class CommentListModel extends BaseModel implements CommentListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CommentListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<CommentItem>>> getCommentList(int page, String classId, int subjectId,
                                                                      String beginTime, String endTime) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("page",page);
        params.put("size", Api.PageSize);

//        if(!TextUtils.isEmpty(classId)){
            params.put("classId",classId);
//        }

        params.put("subjectId",subjectId >= 0 ?subjectId:"");
        params.put("beginTime", TextUtils.isEmpty(beginTime)?"":beginTime);
        params.put("endTime",TextUtils.isEmpty(endTime)?"":endTime);

        return mRepositoryManager
                .obtainRetrofitService(Teacher.class)
                .commentList(ParamsUtils.fromMap(mApplication,params));
    }

    @Override
    public Observable<BaseResponse<List<BelongClass>>> belongClass() {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("page",1);
        params.put("size",10);
        return mRepositoryManager
                .obtainRetrofitService(Teacher.class)
                .belongClass(ParamsUtils.fromMap(mApplication,params));
    }

    @Override
    public Observable<BaseResponse<String>> commentingBaseUrl(String analysisKey) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("analysisKey",analysisKey);
        return mRepositoryManager
                .obtainRetrofitService(Teacher.class)
                .commentingDetailUrl(ParamsUtils.fromMap(mApplication,params));
    }
}