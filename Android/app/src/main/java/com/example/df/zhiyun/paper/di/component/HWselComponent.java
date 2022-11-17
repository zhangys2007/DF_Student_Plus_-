package com.example.df.zhiyun.paper.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.paper.di.module.HWselModule;
import com.example.df.zhiyun.paper.mvp.contract.HWselContract;

import com.jess.arms.di.scope.FragmentScope;

import com.example.df.zhiyun.paper.mvp.ui.fragment.HWselFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 15:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HWselModule.class, dependencies = AppComponent.class)
public interface HWselComponent {
    void inject(HWselFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HWselComponent.Builder view(HWselContract.View view);

        HWselComponent.Builder appComponent(AppComponent appComponent);

        HWselComponent build();
    }
}