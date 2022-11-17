package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.HomeworkOldModule;
import com.example.df.zhiyun.mvp.contract.HomeworkOldContract;
import com.example.df.zhiyun.mvp.ui.fragment.HomeworkOldFragment;

import com.jess.arms.di.scope.FragmentScope;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 10:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeworkOldModule.class, dependencies = AppComponent.class)
public interface HomeworkOldComponent {
    void inject(HomeworkOldFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeworkOldComponent.Builder view(HomeworkOldContract.View view);

        HomeworkOldComponent.Builder appComponent(AppComponent appComponent);

        HomeworkOldComponent build();
    }
}