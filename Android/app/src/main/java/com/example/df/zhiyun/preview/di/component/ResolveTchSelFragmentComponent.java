package com.example.df.zhiyun.preview.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.preview.di.module.ResolveTchSelFragmentModule;
import com.example.df.zhiyun.preview.mvp.contract.ResolveTchSelFragmentContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.preview.mvp.ui.fragment.ResolveTchSelFragmentFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/28/2020 16:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ResolveTchSelFragmentModule.class, dependencies = AppComponent.class)
public interface ResolveTchSelFragmentComponent {
    void inject(ResolveTchSelFragmentFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ResolveTchSelFragmentComponent.Builder view(ResolveTchSelFragmentContract.View view);

        ResolveTchSelFragmentComponent.Builder appComponent(AppComponent appComponent);

        ResolveTchSelFragmentComponent build();
    }
}