package com.example.df.zhiyun.login.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.login.mvp.contract.LoginContract;
import com.example.df.zhiyun.login.mvp.model.LoginModel;
import com.jess.arms.mvp.IView;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/19/2019 12:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class LoginModule {

    @Binds
    abstract LoginContract.Model bindLoginModel(LoginModel model);

    @ActivityScope
    @Provides
    static IView provideBaseIView(LoginContract.View view){
        return (IView)view;
    }
}