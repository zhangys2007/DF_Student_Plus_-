package com.example.df.zhiyun.plan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.plan.di.module.PlanResourceModule;
import com.example.df.zhiyun.plan.mvp.contract.PlanResourceContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.plan.mvp.ui.activity.PlanResourceActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/04/2019 11:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PlanResourceModule.class, dependencies = AppComponent.class)
public interface PlanResourceComponent {
    void inject(PlanResourceActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PlanResourceComponent.Builder view(PlanResourceContract.View view);

        PlanResourceComponent.Builder appComponent(AppComponent appComponent);

        PlanResourceComponent build();
    }
}