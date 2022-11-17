package com.example.df.zhiyun.main.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.UserService;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.main.mvp.contract.TchMainContract;
import com.example.df.zhiyun.mvp.model.entity.PersonCenter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 18:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class TchMainModel extends BaseModel implements TchMainContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TchMainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<PersonCenter>> getPersonCenterInfo() {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());

        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .personCenter(ParamsUtils.fromMap(mApplication,params));
    }
}