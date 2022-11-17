package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.ClassSelModule;
import com.example.df.zhiyun.mvp.contract.ClassSelContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.ui.activity.ClassSelActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 16:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ClassSelModule.class, dependencies = AppComponent.class)
public interface ClassSelComponent {
    void inject(ClassSelActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ClassSelComponent.Builder view(ClassSelContract.View view);

        ClassSelComponent.Builder appComponent(AppComponent appComponent);

        ClassSelComponent build();
    }
}