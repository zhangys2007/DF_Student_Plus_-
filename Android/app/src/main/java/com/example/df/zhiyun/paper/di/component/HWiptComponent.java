package com.example.df.zhiyun.paper.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.paper.di.module.HWiptModule;
import com.example.df.zhiyun.paper.mvp.contract.HWiptContract;

import com.jess.arms.di.scope.FragmentScope;

import com.example.df.zhiyun.paper.mvp.ui.fragment.HWiptFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 17:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HWiptModule.class, dependencies = AppComponent.class)
public interface HWiptComponent {
    void inject(HWiptFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HWiptComponent.Builder view(HWiptContract.View view);

        HWiptComponent.Builder appComponent(AppComponent appComponent);

        HWiptComponent build();
    }
}