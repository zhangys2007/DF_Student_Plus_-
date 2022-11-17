package com.example.df.zhiyun.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.main.di.module.MainFragmentModule;
import com.example.df.zhiyun.main.mvp.contract.MainFragmentContract;

import com.jess.arms.di.scope.FragmentScope;

import com.example.df.zhiyun.main.mvp.ui.fragment.MainFragmentFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/17/2019 13:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MainFragmentModule.class, dependencies = AppComponent.class)
public interface MainFragmentComponent {
    void inject(MainFragmentFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainFragmentComponent.Builder view(MainFragmentContract.View view);

        MainFragmentComponent.Builder appComponent(AppComponent appComponent);

        MainFragmentComponent build();
    }
}