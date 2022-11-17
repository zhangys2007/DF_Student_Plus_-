package com.example.df.zhiyun.analy.di.component;

import com.example.df.zhiyun.analy.di.module.GrowthTraceModule;
import com.example.df.zhiyun.analy.mvp.contract.GrowthTraceContract;
import com.example.df.zhiyun.analy.mvp.ui.activity.GrowthTraceActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 10:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GrowthTraceModule.class, dependencies = AppComponent.class)
public interface GrowthTraceComponent {
    void inject(GrowthTraceActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GrowthTraceComponent.Builder view(GrowthTraceContract.View view);

        GrowthTraceComponent.Builder appComponent(AppComponent appComponent);

        GrowthTraceComponent build();
    }
}