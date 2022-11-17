package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.VersionSwitchModule;
import com.example.df.zhiyun.mvp.contract.VersionSwitchContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.mvp.ui.activity.VersionSwitchActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/21/2019 13:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = VersionSwitchModule.class, dependencies = AppComponent.class)
public interface VersionSwitchComponent {
    void inject(VersionSwitchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        VersionSwitchComponent.Builder view(VersionSwitchContract.View view);

        VersionSwitchComponent.Builder appComponent(AppComponent appComponent);

        VersionSwitchComponent build();
    }
}