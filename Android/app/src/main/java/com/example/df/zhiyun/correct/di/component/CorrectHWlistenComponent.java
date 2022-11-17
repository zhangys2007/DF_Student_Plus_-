package com.example.df.zhiyun.correct.di.component;

import com.example.df.zhiyun.correct.di.module.CorrectHWlistenModule;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWlistenContract;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWlistenFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/19/2019 11:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CorrectHWlistenModule.class, dependencies = AppComponent.class)
public interface CorrectHWlistenComponent {
    void inject(CorrectHWlistenFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CorrectHWlistenComponent.Builder view(CorrectHWlistenContract.View view);

        CorrectHWlistenComponent.Builder appComponent(AppComponent appComponent);

        CorrectHWlistenComponent build();
    }
}