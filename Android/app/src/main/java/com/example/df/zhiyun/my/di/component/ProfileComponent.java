package com.example.df.zhiyun.my.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.my.di.module.ProfileModule;
import com.example.df.zhiyun.my.mvp.contract.ProfileContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.my.mvp.ui.activity.ProfileActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/21/2019 15:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ProfileModule.class, dependencies = AppComponent.class)
public interface ProfileComponent {
    void inject(ProfileActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ProfileComponent.Builder view(ProfileContract.View view);

        ProfileComponent.Builder appComponent(AppComponent appComponent);

        ProfileComponent build();
    }
}