package com.example.df.zhiyun.my.di.module;

import android.support.v4.app.FragmentActivity;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.my.mvp.contract.ProfileContract;
import com.example.df.zhiyun.my.mvp.model.ProfileModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


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
@Module
public abstract class ProfileModule {

    @Binds
    abstract ProfileContract.Model bindProfileModel(ProfileModel model);

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(ProfileContract.View view) {
        return new RxPermissions( (FragmentActivity)view.getMyActivity());
    }
}