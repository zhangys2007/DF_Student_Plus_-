package com.example.df.zhiyun.analy.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.analy.di.module.ScoreTraceModule;
import com.example.df.zhiyun.analy.mvp.contract.ScoreTraceContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.analy.mvp.ui.activity.ScoreTraceActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 10:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ScoreTraceModule.class, dependencies = AppComponent.class)
public interface ScoreTraceComponent {
    void inject(ScoreTraceActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ScoreTraceComponent.Builder view(ScoreTraceContract.View view);

        ScoreTraceComponent.Builder appComponent(AppComponent appComponent);

        ScoreTraceComponent build();
    }
}