package com.example.df.zhiyun.di.component;

import com.example.df.zhiyun.di.module.QuestionCompearModule;
import com.example.df.zhiyun.mvp.contract.QuestionCompearContract;
import com.example.df.zhiyun.mvp.ui.fragment.QuestionCompearFragment;
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
@Component(modules = QuestionCompearModule.class, dependencies = AppComponent.class)
public interface QuestionCompearComponent {
    void inject(QuestionCompearFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        QuestionCompearComponent.Builder view(QuestionCompearContract.View view);

        QuestionCompearComponent.Builder appComponent(AppComponent appComponent);

        QuestionCompearComponent build();
    }
}