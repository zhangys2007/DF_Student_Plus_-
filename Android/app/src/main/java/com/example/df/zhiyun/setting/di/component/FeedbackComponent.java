package com.example.df.zhiyun.setting.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.setting.di.module.FeedbackModule;
import com.example.df.zhiyun.setting.mvp.contract.FeedbackContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.setting.mvp.ui.activity.FeedbackActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/17/2019 22:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FeedbackModule.class, dependencies = AppComponent.class)
public interface FeedbackComponent {
    void inject(FeedbackActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FeedbackComponent.Builder view(FeedbackContract.View view);

        FeedbackComponent.Builder appComponent(AppComponent appComponent);

        FeedbackComponent build();
    }
}