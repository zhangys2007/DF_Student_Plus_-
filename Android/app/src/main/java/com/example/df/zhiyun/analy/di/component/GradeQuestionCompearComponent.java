package com.example.df.zhiyun.analy.di.component;

import com.example.df.zhiyun.analy.di.module.GradeQuestionCompearModule;
import com.example.df.zhiyun.analy.mvp.contract.GradeQuestionCompearContract;
import com.example.df.zhiyun.analy.mvp.ui.fragment.GradeQuestionCompearFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = GradeQuestionCompearModule.class, dependencies = AppComponent.class)
public interface GradeQuestionCompearComponent {
    void inject(GradeQuestionCompearFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GradeQuestionCompearComponent.Builder view(GradeQuestionCompearContract.View view);

        GradeQuestionCompearComponent.Builder appComponent(AppComponent appComponent);

        GradeQuestionCompearComponent build();
    }
}