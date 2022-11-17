package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.EQDetailModule;
import com.example.df.zhiyun.mvp.contract.EQDetailContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.mvp.ui.activity.EQDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/02/2019 09:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EQDetailModule.class, dependencies = AppComponent.class)
public interface EQDetailComponent {
    void inject(EQDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EQDetailComponent.Builder view(EQDetailContract.View view);

        EQDetailComponent.Builder appComponent(AppComponent appComponent);

        EQDetailComponent build();
    }
}