package com.example.df.zhiyun.correct.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.correct.di.module.CorrectHomeworkModule;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHomeworkContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.correct.mvp.ui.activity.CorrectHomeworkActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 18:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CorrectHomeworkModule.class, dependencies = AppComponent.class)
public interface CorrectHomeworkComponent {
    void inject(CorrectHomeworkActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CorrectHomeworkComponent.Builder view(CorrectHomeworkContract.View view);

        CorrectHomeworkComponent.Builder appComponent(AppComponent appComponent);

        CorrectHomeworkComponent build();
    }
}