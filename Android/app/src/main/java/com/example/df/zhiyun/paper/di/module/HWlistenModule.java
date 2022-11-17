package com.example.df.zhiyun.paper.di.module;

import com.example.df.zhiyun.paper.mvp.model.HWlistenModel;

import dagger.Binds;
import dagger.Module;

import com.example.df.zhiyun.paper.mvp.contract.HWlistenContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/19/2019 11:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HWlistenModule {

    @Binds
    abstract HWlistenContract.Model bindHWlistenModel(HWlistenModel model);
}