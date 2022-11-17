package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.SubmitCorrectModule;
import com.example.df.zhiyun.mvp.contract.SubmitCorrectContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.ui.activity.SubmitCorrectActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2019 14:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SubmitCorrectModule.class, dependencies = AppComponent.class)
public interface SubmitCorrectComponent {
    void inject(SubmitCorrectActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SubmitCorrectComponent.Builder view(SubmitCorrectContract.View view);

        SubmitCorrectComponent.Builder appComponent(AppComponent appComponent);

        SubmitCorrectComponent build();
    }
}