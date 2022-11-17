package com.example.df.zhiyun.paper.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.paper.di.module.HWsmpModule;
import com.example.df.zhiyun.paper.mvp.contract.HWsmpContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWsmpFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2019 11:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HWsmpModule.class, dependencies = AppComponent.class)
public interface HWsmpComponent {
    void inject(HWsmpFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HWsmpComponent.Builder view(HWsmpContract.View view);

        HWsmpComponent.Builder appComponent(AppComponent appComponent);

        HWsmpComponent build();
    }
}