package com.example.df.zhiyun.paper.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.paper.di.module.HWsepModule;
import com.example.df.zhiyun.paper.mvp.contract.HWsepContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWsepFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 16:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HWsepModule.class, dependencies = AppComponent.class)
public interface HWsepComponent {
    void inject(HWsepFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HWsepComponent.Builder view(HWsepContract.View view);

        HWsepComponent.Builder appComponent(AppComponent appComponent);

        HWsepComponent build();
    }
}