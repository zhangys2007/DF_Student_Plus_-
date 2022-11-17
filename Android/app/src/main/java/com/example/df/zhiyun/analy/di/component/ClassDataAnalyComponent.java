package com.example.df.zhiyun.analy.di.component;

import com.example.df.zhiyun.analy.di.module.ClassDataAnalyModule;
import com.example.df.zhiyun.analy.mvp.contract.ClassDataAnalyContract;
import com.example.df.zhiyun.analy.mvp.ui.activity.ClassDataAnalyActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 12:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ClassDataAnalyModule.class, dependencies = AppComponent.class)
public interface ClassDataAnalyComponent {
    void inject(ClassDataAnalyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ClassDataAnalyComponent.Builder view(ClassDataAnalyContract.View view);

        ClassDataAnalyComponent.Builder appComponent(AppComponent appComponent);

        ClassDataAnalyComponent build();
    }
}