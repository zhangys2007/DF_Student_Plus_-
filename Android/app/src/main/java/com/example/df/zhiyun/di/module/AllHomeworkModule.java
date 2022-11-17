package com.example.df.zhiyun.di.module;

import com.example.df.zhiyun.mvp.contract.AllHomeworkContract;
import com.example.df.zhiyun.mvp.model.AllHomeworkModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 11:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class AllHomeworkModule {

    @Binds
    abstract AllHomeworkContract.Model bindAllHomeworkModel(AllHomeworkModel model);
}