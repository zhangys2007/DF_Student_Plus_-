package com.example.df.zhiyun.paper.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.paper.di.module.HWcpModule;
import com.example.df.zhiyun.paper.mvp.contract.HWcpContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWcpFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/10/2019 13:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HWcpModule.class, dependencies = AppComponent.class)
public interface HWcpComponent {
    void inject(HWcpFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HWcpComponent.Builder view(HWcpContract.View view);

        HWcpComponent.Builder appComponent(AppComponent appComponent);

        HWcpComponent build();
    }
}