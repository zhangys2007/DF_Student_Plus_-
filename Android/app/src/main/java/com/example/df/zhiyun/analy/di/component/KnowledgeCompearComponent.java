package com.example.df.zhiyun.analy.di.component;

import com.example.df.zhiyun.analy.di.module.KnowledgeCompearModule;
import com.example.df.zhiyun.analy.mvp.contract.KnowledgeCompearContract;
import com.example.df.zhiyun.analy.mvp.ui.fragment.KnowledgeCompearFragment;
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
@Component(modules = KnowledgeCompearModule.class, dependencies = AppComponent.class)
public interface KnowledgeCompearComponent {
    void inject(KnowledgeCompearFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        KnowledgeCompearComponent.Builder view(KnowledgeCompearContract.View view);

        KnowledgeCompearComponent.Builder appComponent(AppComponent appComponent);

        KnowledgeCompearComponent build();
    }
}