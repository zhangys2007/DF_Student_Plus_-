package com.example.df.zhiyun.analy.di.component;

import com.example.df.zhiyun.analy.di.module.HistoryKnowledgePointModule;
import com.example.df.zhiyun.analy.mvp.contract.HistoryKnowledgePointContract;
import com.example.df.zhiyun.analy.mvp.ui.activity.HistoryKnowledgePointActivity;
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
@Component(modules = HistoryKnowledgePointModule.class, dependencies = AppComponent.class)
public interface HistoryKnowledgePointComponent {
    void inject(HistoryKnowledgePointActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HistoryKnowledgePointComponent.Builder view(HistoryKnowledgePointContract.View view);

        HistoryKnowledgePointComponent.Builder appComponent(AppComponent appComponent);

        HistoryKnowledgePointComponent build();
    }
}