package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.ClassModule;
import com.example.df.zhiyun.mvp.contract.ClassContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.mvp.ui.activity.ClassActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/19/2019 14:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ClassModule.class, dependencies = AppComponent.class)
public interface ClassComponent {
    void inject(ClassActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ClassComponent.Builder view(ClassContract.View view);

        ClassComponent.Builder appComponent(AppComponent appComponent);

        ClassComponent build();
    }
}