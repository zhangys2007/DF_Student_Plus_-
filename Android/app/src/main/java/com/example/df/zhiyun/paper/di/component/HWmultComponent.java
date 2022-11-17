package com.example.df.zhiyun.paper.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.paper.di.module.HWmultModule;
import com.example.df.zhiyun.paper.mvp.contract.HWmultContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWmultFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 12:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HWmultModule.class, dependencies = AppComponent.class)
public interface HWmultComponent {
    void inject(HWmultFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HWmultComponent.Builder view(HWmultContract.View view);

        HWmultComponent.Builder appComponent(AppComponent appComponent);

        HWmultComponent build();
    }
}