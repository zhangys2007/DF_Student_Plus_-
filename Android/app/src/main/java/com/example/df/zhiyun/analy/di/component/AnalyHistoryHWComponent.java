package com.example.df.zhiyun.analy.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.analy.di.module.AnalyHistoryHWModule;
import com.example.df.zhiyun.analy.mvp.contract.AnalyHistoryHWContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.analy.mvp.ui.activity.AnalyHistoryHWActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/28/2019 18:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AnalyHistoryHWModule.class, dependencies = AppComponent.class)
public interface AnalyHistoryHWComponent {
    void inject(AnalyHistoryHWActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AnalyHistoryHWComponent.Builder view(AnalyHistoryHWContract.View view);

        AnalyHistoryHWComponent.Builder appComponent(AppComponent appComponent);

        AnalyHistoryHWComponent build();
    }
}