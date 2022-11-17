package com.example.df.zhiyun.paper.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.paper.di.module.HWcomModule;
import com.example.df.zhiyun.paper.mvp.contract.HWcomContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWcomFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2019 11:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HWcomModule.class, dependencies = AppComponent.class)
public interface HWcomComponent {
    void inject(HWcomFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HWcomComponent.Builder view(HWcomContract.View view);

        HWcomComponent.Builder appComponent(AppComponent appComponent);

        HWcomComponent build();
    }
}