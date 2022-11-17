package com.example.df.zhiyun.organize.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.organize.di.module.ClassItemModule;
import com.example.df.zhiyun.organize.mvp.contract.ClassItemContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.organize.mvp.ui.activity.ClassItemActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 15:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ClassItemModule.class, dependencies = AppComponent.class)
public interface ClassItemComponent {
    void inject(ClassItemActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ClassItemComponent.Builder view(ClassItemContract.View view);

        ClassItemComponent.Builder appComponent(AppComponent appComponent);

        ClassItemComponent build();
    }
}