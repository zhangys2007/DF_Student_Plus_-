package com.example.df.zhiyun.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.main.di.module.MainTchFragmentModule;
import com.example.df.zhiyun.main.mvp.contract.MainTchFragmentContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.main.mvp.ui.fragment.MainTchFragmentFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 10:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MainTchFragmentModule.class, dependencies = AppComponent.class)
public interface MainTchFragmentComponent {
    void inject(MainTchFragmentFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainTchFragmentComponent.Builder view(MainTchFragmentContract.View view);

        MainTchFragmentComponent.Builder appComponent(AppComponent appComponent);

        MainTchFragmentComponent build();
    }
}