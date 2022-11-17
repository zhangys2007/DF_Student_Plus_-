package com.example.df.zhiyun.my.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.my.di.module.SignedModule;
import com.example.df.zhiyun.my.mvp.contract.SignedContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.my.mvp.ui.activity.SignedActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 10:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SignedModule.class, dependencies = AppComponent.class)
public interface SignedComponent {
    void inject(SignedActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SignedComponent.Builder view(SignedContract.View view);

        SignedComponent.Builder appComponent(AppComponent appComponent);

        SignedComponent build();
    }
}