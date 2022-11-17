package com.example.df.zhiyun.organize.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.organize.mvp.contract.GradeItemContract;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.mvp.model.api.service.Teacher;
import com.example.df.zhiyun.mvp.model.entity.GradeItem;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 14:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GradeItemModel extends BaseModel implements GradeItemContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GradeItemModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<GradeItem>>> gradeList(String schoolId) {
        Map<String,Object> params = new HashMap<>();
        params.put("schoolId", schoolId);
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
//        params.put("page",page);
//        params.put("size", Api.PageSize);

        return mRepositoryManager
                .obtainRetrofitService(Teacher.class)
                .gradeList(ParamsUtils.fromMap(mApplication,params));
    }
}