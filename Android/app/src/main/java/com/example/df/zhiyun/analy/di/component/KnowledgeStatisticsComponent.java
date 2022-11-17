package com.example.df.zhiyun.analy.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.analy.di.module.KnowledgeStatisticsModule;
import com.example.df.zhiyun.analy.mvp.contract.KnowledgeStatisticsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.analy.mvp.ui.activity.KnowledgeStatisticsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 10:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = KnowledgeStatisticsModule.class, dependencies = AppComponent.class)
public interface KnowledgeStatisticsComponent {
    void inject(KnowledgeStatisticsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        KnowledgeStatisticsComponent.Builder view(KnowledgeStatisticsContract.View view);

        KnowledgeStatisticsComponent.Builder appComponent(AppComponent appComponent);

        KnowledgeStatisticsComponent build();
    }
}