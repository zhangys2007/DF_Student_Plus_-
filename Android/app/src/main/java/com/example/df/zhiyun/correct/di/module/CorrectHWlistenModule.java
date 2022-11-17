package com.example.df.zhiyun.correct.di.module;

import com.example.df.zhiyun.correct.mvp.contract.CorrectHWlistenContract;
import com.example.df.zhiyun.correct.mvp.model.CorrectHWlistenModel;

import dagger.Binds;
import dagger.Module;


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
public abstract class CorrectHWlistenModule {

    @Binds
    abstract CorrectHWlistenContract.Model bindHWlistenModel(CorrectHWlistenModel model);
}