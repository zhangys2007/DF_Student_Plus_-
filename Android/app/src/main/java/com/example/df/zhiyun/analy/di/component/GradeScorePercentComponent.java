package com.example.df.zhiyun.analy.di.component;

import com.example.df.zhiyun.analy.di.module.GradeScorePercentModule;
import com.example.df.zhiyun.analy.mvp.contract.GradeScorePercentContract;
import com.example.df.zhiyun.analy.mvp.ui.fragment.GradeScorePercentFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 11:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = GradeScorePercentModule.class, dependencies = AppComponent.class)
public interface GradeScorePercentComponent {
    void inject(GradeScorePercentFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GradeScorePercentComponent.Builder view(GradeScorePercentContract.View view);

        GradeScorePercentComponent.Builder appComponent(AppComponent appComponent);

        GradeScorePercentComponent build();
    }
}