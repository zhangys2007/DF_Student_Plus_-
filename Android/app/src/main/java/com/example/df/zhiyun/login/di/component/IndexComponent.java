package com.example.df.zhiyun.login.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.login.di.module.IndexModule;
import com.example.df.zhiyun.login.mvp.contract.IndexContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.login.mvp.ui.activity.IndexActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/17/2019 09:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = IndexModule.class, dependencies = {AppComponent.class})
public interface IndexComponent {
    void inject(IndexActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        IndexComponent.Builder view(IndexContract.View view);

        IndexComponent.Builder appComponent(AppComponent appComponent);
//        IndexComponent.Builder userInfoComponent(UserInfoComponent userInfoComponent);

        IndexComponent build();
    }
}