package com.example.df.zhiyun.analy.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.analy.di.module.GrowthTraceListModule;
import com.example.df.zhiyun.analy.mvp.contract.GrowthTraceListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.analy.mvp.ui.activity.GrowthTraceListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 10:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GrowthTraceListModule.class, dependencies = AppComponent.class)
public interface GrowthTraceListComponent {
    void inject(GrowthTraceListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GrowthTraceListComponent.Builder view(GrowthTraceListContract.View view);

        GrowthTraceListComponent.Builder appComponent(AppComponent appComponent);

        GrowthTraceListComponent build();
    }
}