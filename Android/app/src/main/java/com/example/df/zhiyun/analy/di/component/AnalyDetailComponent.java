package com.example.df.zhiyun.analy.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.analy.di.module.AnalyDetailModule;
import com.example.df.zhiyun.analy.mvp.contract.AnalyDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.analy.mvp.ui.activity.AnalyDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 14:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AnalyDetailModule.class, dependencies = AppComponent.class)
public interface AnalyDetailComponent {
    void inject(AnalyDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AnalyDetailComponent.Builder view(AnalyDetailContract.View view);

        AnalyDetailComponent.Builder appComponent(AppComponent appComponent);

        AnalyDetailComponent build();
    }
}