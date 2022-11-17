package com.example.df.zhiyun.correct.di.component;

import com.example.df.zhiyun.correct.di.module.CorrectHWsmpModule;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWsmpContract;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWsmpFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2019 11:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CorrectHWsmpModule.class, dependencies = AppComponent.class)
public interface CorrectHWsmpComponent {
    void inject(CorrectHWsmpFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CorrectHWsmpComponent.Builder view(CorrectHWsmpContract.View view);

        CorrectHWsmpComponent.Builder appComponent(AppComponent appComponent);

        CorrectHWsmpComponent build();
    }
}