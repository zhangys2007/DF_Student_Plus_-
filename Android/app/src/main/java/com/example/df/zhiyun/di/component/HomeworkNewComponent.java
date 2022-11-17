package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.HomeworkNewModule;
import com.example.df.zhiyun.mvp.contract.HomeworkNewContract;
import com.example.df.zhiyun.mvp.ui.fragment.HomeworkNewFragment;

import com.jess.arms.di.scope.FragmentScope;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 11:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeworkNewModule.class, dependencies = AppComponent.class)
public interface HomeworkNewComponent {
    void inject(HomeworkNewFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeworkNewComponent.Builder view(HomeworkNewContract.View view);

        HomeworkNewComponent.Builder appComponent(AppComponent appComponent);

        HomeworkNewComponent build();
    }
}