package com.example.df.zhiyun.plan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.plan.di.module.PlanModule;
import com.example.df.zhiyun.plan.mvp.contract.PlanContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.plan.mvp.ui.activity.PlanActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/03/2019 16:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PlanModule.class, dependencies = AppComponent.class)
public interface PlanComponent {
    void inject(PlanActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PlanComponent.Builder view(PlanContract.View view);

        PlanComponent.Builder appComponent(AppComponent appComponent);

        PlanComponent build();
    }
}