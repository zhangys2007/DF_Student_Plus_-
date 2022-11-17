package com.example.df.zhiyun.correct.di.component;

import com.example.df.zhiyun.correct.di.module.CorrectHWmultModule;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWmultContract;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWmultFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 12:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CorrectHWmultModule.class, dependencies = AppComponent.class)
public interface CorrectHWmultComponent {
    void inject(CorrectHWmultFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CorrectHWmultComponent.Builder view(CorrectHWmultContract.View view);

        CorrectHWmultComponent.Builder appComponent(AppComponent appComponent);

        CorrectHWmultComponent build();
    }
}