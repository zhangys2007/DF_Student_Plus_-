package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.ErrorExerModule;
import com.example.df.zhiyun.mvp.contract.ErrorExerContract;

import com.jess.arms.di.scope.FragmentScope;

import com.example.df.zhiyun.mvp.ui.fragment.ErrorExerFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ErrorExerModule.class, dependencies = AppComponent.class)
public interface ErrorExerComponent {
    void inject(ErrorExerFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ErrorExerComponent.Builder view(ErrorExerContract.View view);

        ErrorExerComponent.Builder appComponent(AppComponent appComponent);

        ErrorExerComponent build();
    }
}