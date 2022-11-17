package com.example.df.zhiyun.preview.di.module;

import com.example.df.zhiyun.preview.mvp.contract.PrevHWlistenContract;
import com.example.df.zhiyun.preview.mvp.model.PrevHWlistenModel;

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
public abstract class PrevHWlistenModule {

    @Binds
    abstract PrevHWlistenContract.Model bindHWlistenModel(PrevHWlistenModel model);
}