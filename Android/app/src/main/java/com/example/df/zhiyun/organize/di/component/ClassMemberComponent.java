package com.example.df.zhiyun.organize.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.organize.di.module.ClassMemberModule;
import com.example.df.zhiyun.organize.mvp.contract.ClassMemberContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.organize.mvp.ui.activity.ClassMemberActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 12:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ClassMemberModule.class, dependencies = AppComponent.class)
public interface ClassMemberComponent {
    void inject(ClassMemberActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ClassMemberComponent.Builder view(ClassMemberContract.View view);

        ClassMemberComponent.Builder appComponent(AppComponent appComponent);

        ClassMemberComponent build();
    }
}