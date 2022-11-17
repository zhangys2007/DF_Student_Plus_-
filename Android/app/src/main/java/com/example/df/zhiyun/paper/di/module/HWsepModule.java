package com.example.df.zhiyun.paper.di.module;

import com.example.df.zhiyun.paper.mvp.contract.HWsepContract;
import com.example.df.zhiyun.paper.mvp.model.HWsepModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 16:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HWsepModule {

    @Binds
    abstract HWsepContract.Model bindHWsepModel(HWsepModel model);
}