package com.example.df.zhiyun.preview.mvp.model;

import android.app.Application;

import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.preview.mvp.contract.PreviewHomeworkContract;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.HomeworkService;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.HomeworkSet;
import com.example.df.zhiyun.mvp.model.entity.HomeworkSetWrap;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


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
public class PreviewHomeworkModel extends BaseModel implements PreviewHomeworkContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PreviewHomeworkModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<HomeworkSet>> getHomeworkSet(String id,int type, int subjectId) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        params.put("studentHomeWorkId", id);
        params.put("type", type);
        params.put("subjectId", subjectId);

//
//        return mRepositoryManager
//                .obtainRetrofitService(HomeworkService.class)
//                .getHomeworkSet(ParamsUtils.fromMap(mApplication,params));

        return mRepositoryManager
                .obtainRetrofitService(HomeworkService.class)
                .getHomeworkSet(ParamsUtils.fromMap(mApplication,params))
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
}