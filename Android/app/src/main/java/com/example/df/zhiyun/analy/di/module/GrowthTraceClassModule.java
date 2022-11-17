package com.example.df.zhiyun.analy.di.module;

import com.example.df.zhiyun.analy.mvp.contract.GrowthTraceClassContract;
import com.example.df.zhiyun.analy.mvp.model.GrowthTraceClassModel;


import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 10:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class GrowthTraceClassModule {

    @Binds
    abstract GrowthTraceClassContract.Model bindGrowthTraceListModel(GrowthTraceClassModel model);
}