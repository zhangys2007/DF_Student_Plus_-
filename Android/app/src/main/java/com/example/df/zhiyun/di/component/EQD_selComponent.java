package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.EQD_selModule;
import com.example.df.zhiyun.mvp.contract.EQD_selContract;

import com.jess.arms.di.scope.FragmentScope;

import com.example.df.zhiyun.mvp.ui.fragment.EQD_selFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/02/2019 13:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = EQD_selModule.class, dependencies = AppComponent.class)
public interface EQD_selComponent {
    void inject(EQD_selFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EQD_selComponent.Builder view(EQD_selContract.View view);

        EQD_selComponent.Builder appComponent(AppComponent appComponent);

        EQD_selComponent build();
    }
}