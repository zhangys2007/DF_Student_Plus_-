package com.example.df.zhiyun.preview.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.preview.di.module.ResolveTchOthModule;
import com.example.df.zhiyun.preview.mvp.contract.ResolveTchOthContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.preview.mvp.ui.fragment.ResolveTchOthFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/29/2020 11:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ResolveTchOthModule.class, dependencies = AppComponent.class)
public interface ResolveTchOthComponent {
    void inject(ResolveTchOthFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ResolveTchOthComponent.Builder view(ResolveTchOthContract.View view);

        ResolveTchOthComponent.Builder appComponent(AppComponent appComponent);

        ResolveTchOthComponent build();
    }
}