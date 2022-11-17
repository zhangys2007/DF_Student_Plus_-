package com.example.df.zhiyun.paper.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.paper.di.module.SetHomeworkModule;
import com.example.df.zhiyun.paper.mvp.contract.SetHomeworkContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.paper.mvp.ui.activity.SetHomeworkActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SetHomeworkModule.class, dependencies = AppComponent.class)
public interface SetHomeworkComponent {
    void inject(SetHomeworkActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SetHomeworkComponent.Builder view(SetHomeworkContract.View view);

        SetHomeworkComponent.Builder appComponent(AppComponent appComponent);

        SetHomeworkComponent build();
    }
}