package com.example.df.zhiyun.analy.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.analy.di.module.AnalyReportModule;
import com.example.df.zhiyun.analy.mvp.contract.AnalyReportContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.analy.mvp.ui.activity.AnalyReportActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/25/2019 15:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AnalyReportModule.class, dependencies = AppComponent.class)
public interface AnalyReportComponent {
    void inject(AnalyReportActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AnalyReportComponent.Builder view(AnalyReportContract.View view);

        AnalyReportComponent.Builder appComponent(AppComponent appComponent);

        AnalyReportComponent build();
    }
}