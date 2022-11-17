package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.ExerListModule;
import com.example.df.zhiyun.mvp.contract.ExerListContract;

import com.jess.arms.di.scope.FragmentScope;

import com.example.df.zhiyun.mvp.ui.fragment.ExerListFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 17:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ExerListModule.class, dependencies = AppComponent.class)
public interface ExerListComponent {
    void inject(ExerListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExerListComponent.Builder view(ExerListContract.View view);

        ExerListComponent.Builder appComponent(AppComponent appComponent);

        ExerListComponent build();
    }
}