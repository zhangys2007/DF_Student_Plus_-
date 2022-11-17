package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.ExerRecordModule;
import com.example.df.zhiyun.mvp.contract.ExerRecordContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.mvp.ui.activity.ExerRecordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 16:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ExerRecordModule.class, dependencies = AppComponent.class)
public interface ExerRecordComponent {
    void inject(ExerRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExerRecordComponent.Builder view(ExerRecordContract.View view);

        ExerRecordComponent.Builder appComponent(AppComponent appComponent);

        ExerRecordComponent build();
    }
}