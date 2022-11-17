package com.example.df.zhiyun.login.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.login.di.module.GuideModule;
import com.example.df.zhiyun.login.mvp.contract.GuideContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.login.mvp.ui.activity.GuideActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 11:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GuideModule.class, dependencies = AppComponent.class)
public interface GuideComponent {
    void inject(GuideActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GuideComponent.Builder view(GuideContract.View view);

        GuideComponent.Builder appComponent(AppComponent appComponent);

        GuideComponent build();
    }
}