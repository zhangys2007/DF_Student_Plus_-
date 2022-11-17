package com.example.df.zhiyun.plan.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.plan.di.module.PlanCloudModule;
import com.example.df.zhiyun.plan.mvp.contract.PlanCloudContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.plan.mvp.ui.activity.PlanCloudActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/07/2020 14:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PlanCloudModule.class, dependencies = AppComponent.class)
public interface PlanCloudComponent {
    void inject(PlanCloudActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        PlanCloudComponent.Builder view(PlanCloudContract.View view);
        PlanCloudComponent.Builder appComponent(AppComponent appComponent);
        PlanCloudComponent build();
    }
}