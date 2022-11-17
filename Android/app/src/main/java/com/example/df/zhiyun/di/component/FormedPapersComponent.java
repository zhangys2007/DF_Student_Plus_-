package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.FormedPapersModule;
import com.example.df.zhiyun.mvp.contract.FormedPapersContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.ui.activity.FormedPapersActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 13:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FormedPapersModule.class, dependencies = AppComponent.class)
public interface FormedPapersComponent {
    void inject(FormedPapersActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FormedPapersComponent.Builder view(FormedPapersContract.View view);

        FormedPapersComponent.Builder appComponent(AppComponent appComponent);

        FormedPapersComponent build();
    }
}