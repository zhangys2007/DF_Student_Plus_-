package com.example.df.zhiyun.di.component;

import com.example.df.zhiyun.di.module.KGDetailModule;
import com.example.df.zhiyun.mvp.contract.KGDetailContract;
import com.example.df.zhiyun.mvp.ui.fragment.KGDetailFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 11:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = KGDetailModule.class, dependencies = AppComponent.class)
public interface KGDetailComponent {
    void inject(KGDetailFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        KGDetailComponent.Builder view(KGDetailContract.View view);

        KGDetailComponent.Builder appComponent(AppComponent appComponent);

        KGDetailComponent build();
    }
}