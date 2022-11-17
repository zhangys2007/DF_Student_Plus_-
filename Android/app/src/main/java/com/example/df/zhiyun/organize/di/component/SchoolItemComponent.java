package com.example.df.zhiyun.organize.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.organize.di.module.SchoolItemModule;
import com.example.df.zhiyun.organize.mvp.contract.SchoolItemContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.organize.mvp.ui.activity.SchoolItemActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 13:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SchoolItemModule.class, dependencies = AppComponent.class)
public interface SchoolItemComponent {
    void inject(SchoolItemActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SchoolItemComponent.Builder view(SchoolItemContract.View view);

        SchoolItemComponent.Builder appComponent(AppComponent appComponent);

        SchoolItemComponent build();
    }
}