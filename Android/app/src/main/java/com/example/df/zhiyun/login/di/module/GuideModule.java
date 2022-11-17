package com.example.df.zhiyun.login.di.module;

import dagger.Binds;
import dagger.Module;

import com.example.df.zhiyun.login.mvp.contract.GuideContract;
import com.example.df.zhiyun.login.mvp.model.GuideModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 11:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class GuideModule {

    @Binds
    abstract GuideContract.Model bindGuideModel(GuideModel model);
}