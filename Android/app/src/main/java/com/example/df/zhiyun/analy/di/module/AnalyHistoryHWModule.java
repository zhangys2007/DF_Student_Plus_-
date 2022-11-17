package com.example.df.zhiyun.analy.di.module;

import dagger.Binds;
import dagger.Module;

import com.example.df.zhiyun.analy.mvp.contract.AnalyHistoryHWContract;
import com.example.df.zhiyun.analy.mvp.model.AnalyHistoryHWModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/28/2019 18:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class AnalyHistoryHWModule {

    @Binds
    abstract AnalyHistoryHWContract.Model bindAnalyHistoryHWModel(AnalyHistoryHWModel model);
}