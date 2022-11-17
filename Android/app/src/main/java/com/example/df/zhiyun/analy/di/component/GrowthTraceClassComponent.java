package com.example.df.zhiyun.analy.di.component;

import com.example.df.zhiyun.analy.di.module.GrowthTraceClassModule;
import com.example.df.zhiyun.analy.mvp.contract.GrowthTraceClassContract;
import com.example.df.zhiyun.analy.mvp.ui.activity.GrowthTraceClassActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


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
@Component(modules = GrowthTraceClassModule.class, dependencies = AppComponent.class)
public interface GrowthTraceClassComponent {
    void inject(GrowthTraceClassActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GrowthTraceClassComponent.Builder view(GrowthTraceClassContract.View view);

        GrowthTraceClassComponent.Builder appComponent(AppComponent appComponent);

        GrowthTraceClassComponent build();
    }
}