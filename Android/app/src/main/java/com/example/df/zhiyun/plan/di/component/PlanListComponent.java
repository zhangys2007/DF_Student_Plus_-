package com.example.df.zhiyun.plan.di.component;

import com.example.df.zhiyun.plan.di.module.PlanListModule;
import com.example.df.zhiyun.plan.mvp.contract.PlanListContract;
import com.example.df.zhiyun.plan.mvp.ui.fragment.PlanListFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 11:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PlanListModule.class, dependencies = AppComponent.class)
public interface PlanListComponent {
    void inject(PlanListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PlanListComponent.Builder view(PlanListContract.View view);

        PlanListComponent.Builder appComponent(AppComponent appComponent);

        PlanListComponent build();
    }
}