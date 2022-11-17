package com.example.df.zhiyun.plan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.plan.di.module.DesignModule;
import com.example.df.zhiyun.plan.mvp.contract.DesignContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.plan.mvp.ui.activity.DesignActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/03/2019 22:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DesignModule.class, dependencies = AppComponent.class)
public interface DesignComponent {
    void inject(DesignActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DesignComponent.Builder view(DesignContract.View view);

        DesignComponent.Builder appComponent(AppComponent appComponent);

        DesignComponent build();
    }
}