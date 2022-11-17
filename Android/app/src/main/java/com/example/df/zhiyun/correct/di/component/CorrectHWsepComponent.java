package com.example.df.zhiyun.correct.di.component;

import com.example.df.zhiyun.correct.di.module.CorrectHWsepModule;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWsepContract;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWsepFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 16:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CorrectHWsepModule.class, dependencies = AppComponent.class)
public interface CorrectHWsepComponent {
    void inject(CorrectHWsepFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CorrectHWsepComponent.Builder view(CorrectHWsepContract.View view);

        CorrectHWsepComponent.Builder appComponent(AppComponent appComponent);

        CorrectHWsepComponent build();
    }
}