package com.example.df.zhiyun.di.component;

import com.example.df.zhiyun.di.module.ClassCorrectInfoModule;
import com.example.df.zhiyun.mvp.contract.ClassCorrectInfoContract;
import com.example.df.zhiyun.mvp.ui.activity.ClassCorrectInfoActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


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
@Component(modules = ClassCorrectInfoModule.class, dependencies = AppComponent.class)
public interface ClassCorrectInfoComponent {
    void inject(ClassCorrectInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ClassCorrectInfoComponent.Builder view(ClassCorrectInfoContract.View view);

        ClassCorrectInfoComponent.Builder appComponent(AppComponent appComponent);

        ClassCorrectInfoComponent build();
    }
}