package com.example.df.zhiyun.analy.di.component;

import com.example.df.zhiyun.analy.di.module.KnowledgeGraspModule;
import com.example.df.zhiyun.analy.mvp.contract.KnowledgeGraspContract;
import com.example.df.zhiyun.analy.mvp.ui.fragment.KnowledgeGraspFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = KnowledgeGraspModule.class, dependencies = AppComponent.class)
public interface KnowledgeGraspComponent {
    void inject(KnowledgeGraspFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        KnowledgeGraspComponent.Builder view(KnowledgeGraspContract.View view);

        KnowledgeGraspComponent.Builder appComponent(AppComponent appComponent);

        KnowledgeGraspComponent build();
    }
}