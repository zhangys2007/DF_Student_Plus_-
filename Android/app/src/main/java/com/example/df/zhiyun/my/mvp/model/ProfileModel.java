package com.example.df.zhiyun.my.mvp.model;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;
import com.example.df.zhiyun.my.mvp.contract.ProfileContract;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.UserService;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.UserInfo;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/21/2019 15:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ProfileModel extends BaseModel implements ProfileContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ProfileModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseResponse<UserInfo>> updateUserProfile(Map<String,Object> map) {
        Map<String,Object> params = new HashMap<>();
        params.put("birthday", "");
        params.put("email", "");
        params.put("headImage", "");
        params.put("password", "");
        params.put("phone", "");
        params.put("sex", "");
        params.put("username", "");

        params.putAll(map);
        params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .updateUserProfile(ParamsUtils.fromMap(mApplication,params));
    }


    //info 只有更新的那个字段有值
    @Override
    public void updateLocalInfo(UserInfo info) {
        UserInfo localInfo = AccountManager.getInstance().getUserInfo();



        if(!TextUtils.isEmpty(info.getHeadImage())){
            localInfo.setHeadImage(info.getHeadImage());
        }

        if(!TextUtils.isEmpty(info.getUserName())){
            localInfo.setUserName(info.getUserName());
        }

        if(info.getSex() >0 && info.getSex() < 3){
            localInfo.setSex(info.getSex());
        }

        if(info.getBirthday() > 0){
            localInfo.setBirthday(info.getBirthday());
        }

        if(!TextUtils.isEmpty(info.getEmail())){
            localInfo.setEmail(info.getEmail());
        }

        if(!TextUtils.isEmpty(info.getPhone())){
            localInfo.setPhone(info.getPhone());
        }

        if(!TextUtils.isEmpty(info.getSchool())){
            localInfo.setSchool(info.getSchool());
        }
    }



    /**
     * 上传图片
     * @return
     */
    public Observable<BaseResponse<UserInfo>> uploadPic(Bitmap bitmap){
        return Observable.just(bitmap)
                .map(new Function<Bitmap, String>() {
                    @Override
                    public String apply(Bitmap bitmap) throws Exception {
                        return Base64BitmapTransformor.getString(bitmap);
                    }
                }).flatMap(new Function<String, ObservableSource<BaseResponse<UserInfo>>>() {
            @Override
            public ObservableSource<BaseResponse<UserInfo>> apply(String s) throws Exception {
                Map<String,Object> params = new HashMap<>();
                params.put("headImage", s);
                return updateUserProfile(params);
            }
        });
    }
}