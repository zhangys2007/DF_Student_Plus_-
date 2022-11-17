package com.example.df.zhiyun.paper.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.paper.di.module.HWlistenModule;
import com.example.df.zhiyun.paper.mvp.contract.HWlistenContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWlistenFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/19/2019 11:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HWlistenModule.class, dependencies = AppComponent.class)
public interface HWlistenComponent {
    void inject(HWlistenFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HWlistenComponent.Builder view(HWlistenContract.View view);

        HWlistenComponent.Builder appComponent(AppComponent appComponent);

        HWlistenComponent build();
    }
}