package com.example.df.zhiyun.main.di.module;

import com.example.df.zhiyun.main.mvp.contract.TchMainContract;
import com.example.df.zhiyun.main.mvp.model.TchMainModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 18:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class TchMainModule {

    @Binds
    abstract TchMainContract.Model bindTchMainModel(TchMainModel model);
}