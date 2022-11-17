package com.example.df.zhiyun.mvp.model;

import android.app.Application;

import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.mvp.contract.PutHWDetailContract;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.Teacher;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.model.entity.PutHWDetail;
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
 * Created by MVPArmsTemplate on 08/30/2019 15:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class PutHWDetailModel extends BaseModel implements PutHWDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PutHWDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseResponse<PutHWDetail>> putHomeworkDetail(String homeworkId, String classId, int type) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("paperId", homeworkId);
        params.put("classId", classId);
        params.put("type", type);

        return mRepositoryManager
                .obtainRetrofitService(Teacher.class)
                .putHomeWorkDetail(ParamsUtils.fromMap(mApplication,params));
    }

    @Override
    public Observable<BaseResponse<List<BelongClass>>> homeworkClass(String paperId, int type, String teachSystemId, String linkId) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("paperId", paperId);
        params.put("type", type);
        params.put("teachSystemId", teachSystemId);
        params.put("linkId", linkId);

        return mRepositoryManager
                .obtainRetrofitService(Teacher.class)
                .teacherClass(ParamsUtils.fromMap(mApplication,params));
    }
}