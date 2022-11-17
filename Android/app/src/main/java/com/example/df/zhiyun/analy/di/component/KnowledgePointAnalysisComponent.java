package com.example.df.zhiyun.analy.di.component;

import com.example.df.zhiyun.analy.di.module.KnowledgePointAnalysisModule;
import com.example.df.zhiyun.analy.mvp.contract.KnowledgePointAnalysisContract;
import com.example.df.zhiyun.analy.mvp.presenter.KnowledgePointAnalysisActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 15:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = KnowledgePointAnalysisModule.class, dependencies = AppComponent.class)
public interface KnowledgePointAnalysisComponent {
    void inject(KnowledgePointAnalysisActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        KnowledgePointAnalysisComponent.Builder view(KnowledgePointAnalysisContract.View view);

        KnowledgePointAnalysisComponent.Builder appComponent(AppComponent appComponent);

        KnowledgePointAnalysisComponent build();
    }
}