package com.example.df.zhiyun.analy.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.analy.di.module.AnalyPageModule;
import com.example.df.zhiyun.analy.mvp.contract.AnalyPageContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.analy.mvp.ui.fragment.AnalyPageFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/23/2020 15:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = AnalyPageModule.class, dependencies = AppComponent.class)
public interface AnalyPageComponent {
    void inject(AnalyPageFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AnalyPageComponent.Builder view(AnalyPageContract.View view);

        AnalyPageComponent.Builder appComponent(AppComponent appComponent);

        AnalyPageComponent build();
    }
}