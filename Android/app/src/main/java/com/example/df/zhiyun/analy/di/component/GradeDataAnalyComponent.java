package com.example.df.zhiyun.analy.di.component;

import com.example.df.zhiyun.analy.di.module.GradeDataAnalyModule;
import com.example.df.zhiyun.analy.mvp.contract.GradeDataAnalyContract;
import com.example.df.zhiyun.analy.mvp.ui.activity.GradeDataAnalyActivity;
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
@Component(modules = GradeDataAnalyModule.class, dependencies = AppComponent.class)
public interface GradeDataAnalyComponent {
    void inject(GradeDataAnalyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GradeDataAnalyComponent.Builder view(GradeDataAnalyContract.View view);

        GradeDataAnalyComponent.Builder appComponent(AppComponent appComponent);

        GradeDataAnalyComponent build();
    }
}