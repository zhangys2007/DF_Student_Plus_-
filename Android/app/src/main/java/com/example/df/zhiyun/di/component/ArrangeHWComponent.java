package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.ArrangeHWModule;
import com.example.df.zhiyun.mvp.contract.ArrangeHWContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.ui.activity.ArrangeHWActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/29/2019 16:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ArrangeHWModule.class, dependencies = AppComponent.class)
public interface ArrangeHWComponent {
    void inject(ArrangeHWActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ArrangeHWComponent.Builder view(ArrangeHWContract.View view);

        ArrangeHWComponent.Builder appComponent(AppComponent appComponent);

        ArrangeHWComponent build();
    }
}