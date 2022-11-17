package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.HomeworkModule;
import com.example.df.zhiyun.mvp.contract.HomeworkContract;

import com.jess.arms.di.scope.FragmentScope;

import com.example.df.zhiyun.mvp.ui.fragment.HomeworkFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 10:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeworkModule.class, dependencies = AppComponent.class)
public interface HomeworkComponent {
    void inject(HomeworkFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeworkComponent.Builder view(HomeworkContract.View view);

        HomeworkComponent.Builder appComponent(AppComponent appComponent);

        HomeworkComponent build();
    }
}