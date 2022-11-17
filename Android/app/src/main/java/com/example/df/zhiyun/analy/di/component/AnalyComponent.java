package com.example.df.zhiyun.analy.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.analy.di.module.AnalyModule;
import com.example.df.zhiyun.analy.mvp.contract.AnalyContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.analy.mvp.ui.activity.AnalyActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/04/2019 10:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AnalyModule.class, dependencies = AppComponent.class)
public interface AnalyComponent {
    void inject(AnalyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AnalyComponent.Builder view(AnalyContract.View view);

        AnalyComponent.Builder appComponent(AppComponent appComponent);

        AnalyComponent build();
    }
}