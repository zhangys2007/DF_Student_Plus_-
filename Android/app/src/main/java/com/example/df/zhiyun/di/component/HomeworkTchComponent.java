package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.HomeworkTchModule;
import com.example.df.zhiyun.mvp.contract.HomeworkTchContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.mvp.ui.fragment.HomeworkTchFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 13:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeworkTchModule.class, dependencies = AppComponent.class)
public interface HomeworkTchComponent {
    void inject(HomeworkTchFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeworkTchComponent.Builder view(HomeworkTchContract.View view);

        HomeworkTchComponent.Builder appComponent(AppComponent appComponent);

        HomeworkTchComponent build();
    }
}