package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.AllHomeworkModule;
import com.example.df.zhiyun.mvp.contract.AllHomeworkContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.mvp.ui.activity.AllHomeworkActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 11:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AllHomeworkModule.class, dependencies = AppComponent.class)
public interface AllHomeworkComponent {
    void inject(AllHomeworkActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AllHomeworkComponent.Builder view(AllHomeworkContract.View view);

        AllHomeworkComponent.Builder appComponent(AppComponent appComponent);

        AllHomeworkComponent build();
    }
}