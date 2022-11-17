package com.example.df.zhiyun.preview.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.preview.di.module.ResolveTchModule;
import com.example.df.zhiyun.preview.mvp.contract.ResolveTchContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.preview.mvp.ui.activity.ResolveTchActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/27/2020 18:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ResolveTchModule.class, dependencies = AppComponent.class)
public interface ResolveTchComponent {
    void inject(ResolveTchActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        ResolveTchComponent.Builder view(ResolveTchContract.View view);
        ResolveTchComponent.Builder appComponent(AppComponent appComponent);
        ResolveTchComponent build();
    }
}