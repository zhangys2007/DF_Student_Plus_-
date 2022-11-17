package com.example.df.zhiyun.my.di.module;

import com.example.df.zhiyun.my.mvp.contract.MyTchContract;
import com.example.df.zhiyun.my.mvp.model.MyTchModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 09:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class MyTchModule {

    @Binds
    abstract MyTchContract.Model bindMyTchModel(MyTchModel model);
}