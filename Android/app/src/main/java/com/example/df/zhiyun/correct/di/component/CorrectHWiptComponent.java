package com.example.df.zhiyun.correct.di.component;

import com.example.df.zhiyun.correct.di.module.CorrectHWiptModule;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWiptContract;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWiptFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 17:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CorrectHWiptModule.class, dependencies = AppComponent.class)
public interface CorrectHWiptComponent {
    void inject(CorrectHWiptFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CorrectHWiptComponent.Builder view(CorrectHWiptContract.View view);

        CorrectHWiptComponent.Builder appComponent(AppComponent appComponent);

        CorrectHWiptComponent build();
    }
}