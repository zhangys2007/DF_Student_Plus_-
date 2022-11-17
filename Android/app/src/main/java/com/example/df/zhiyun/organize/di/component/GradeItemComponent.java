package com.example.df.zhiyun.organize.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.example.df.zhiyun.organize.mvp.contract.GradeItemContract;
import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.organize.di.module.GradeItemModule;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.organize.mvp.ui.activity.GradeItemActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 14:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GradeItemModule.class, dependencies = AppComponent.class)
public interface GradeItemComponent {
    void inject(GradeItemActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GradeItemComponent.Builder view(GradeItemContract.View view);

        GradeItemComponent.Builder appComponent(AppComponent appComponent);

        GradeItemComponent build();
    }
}