package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.QuestionStoreModule;
import com.example.df.zhiyun.mvp.contract.QuestionStoreContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.mvp.ui.activity.QuestionStoreActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/26/2019 15:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = QuestionStoreModule.class, dependencies = AppComponent.class)
public interface QuestionStoreComponent {
    void inject(QuestionStoreActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        QuestionStoreComponent.Builder view(QuestionStoreContract.View view);

        QuestionStoreComponent.Builder appComponent(AppComponent appComponent);

        QuestionStoreComponent build();
    }
}