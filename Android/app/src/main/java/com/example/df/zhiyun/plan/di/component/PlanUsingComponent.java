package com.example.df.zhiyun.plan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.plan.di.module.PlanUsingModule;
import com.example.df.zhiyun.plan.mvp.contract.PlanUsingContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.plan.mvp.ui.activity.PlanUsingActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/30/2019 15:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PlanUsingModule.class, dependencies = AppComponent.class)
public interface PlanUsingComponent {
    void inject(PlanUsingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PlanUsingComponent.Builder view(PlanUsingContract.View view);

        PlanUsingComponent.Builder appComponent(AppComponent appComponent);

        PlanUsingComponent build();
    }
}