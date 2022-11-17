package com.example.df.zhiyun.correct.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.correct.di.module.CorrectSomeOneModule;
import com.example.df.zhiyun.correct.mvp.contract.CorrectSomeOneContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.correct.mvp.ui.activity.CorrectSomeOneActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 14:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CorrectSomeOneModule.class, dependencies = AppComponent.class)
public interface CorrectSomeOneComponent {
    void inject(CorrectSomeOneActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CorrectSomeOneComponent.Builder view(CorrectSomeOneContract.View view);

        CorrectSomeOneComponent.Builder appComponent(AppComponent appComponent);

        CorrectSomeOneComponent build();
    }
}