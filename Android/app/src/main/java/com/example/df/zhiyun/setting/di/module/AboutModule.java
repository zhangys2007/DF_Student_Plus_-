package com.example.df.zhiyun.setting.di.module;

import com.example.df.zhiyun.setting.mvp.contract.AboutContract;

import dagger.Binds;
import dagger.Module;

import com.example.df.zhiyun.setting.mvp.model.AboutModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 10:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class AboutModule {

    @Binds
    abstract AboutContract.Model bindAboutModel(AboutModel model);
}