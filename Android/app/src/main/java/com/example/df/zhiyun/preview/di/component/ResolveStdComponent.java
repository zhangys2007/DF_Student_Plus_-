package com.example.df.zhiyun.preview.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.preview.di.module.ResolveStdModule;
import com.example.df.zhiyun.preview.mvp.contract.ResolveStdContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.preview.mvp.ui.activity.ResolveStdActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/30/2020 11:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ResolveStdModule.class, dependencies = AppComponent.class)
public interface ResolveStdComponent {
    void inject(ResolveStdActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        ResolveStdComponent.Builder view(ResolveStdContract.View view);
        ResolveStdComponent.Builder appComponent(AppComponent appComponent);
        ResolveStdComponent build();
    }
}