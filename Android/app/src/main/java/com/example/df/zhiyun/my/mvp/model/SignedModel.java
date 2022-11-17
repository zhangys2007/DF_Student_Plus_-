package com.example.df.zhiyun.my.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.UserService;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.SignedInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

import com.example.df.zhiyun.my.mvp.contract.SignedContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 10:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SignedModel extends BaseModel implements SignedContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;
    private SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM", Locale.getDefault());

    @Inject
    public SignedModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<SignedInfo>> getSignedData() {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("time", TimeUtils.getYM(Calendar.getInstance().getTimeInMillis()));
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getSignedData(ParamsUtils.fromMap(mApplication,params));
    }

    @Override
    public Observable<BaseResponse> signed() {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .signed(ParamsUtils.fromMap(mApplication,params));
    }
}