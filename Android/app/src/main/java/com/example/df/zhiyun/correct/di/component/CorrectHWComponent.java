package com.example.df.zhiyun.correct.di.component;

import com.example.df.zhiyun.correct.di.module.CorrectHWModule;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWContract;
import com.example.df.zhiyun.correct.mvp.ui.activity.CorrectHWActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CorrectHWModule.class, dependencies = AppComponent.class)
public interface CorrectHWComponent {
    void inject(CorrectHWActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CorrectHWComponent.Builder view(CorrectHWContract.View view);

        CorrectHWComponent.Builder appComponent(AppComponent appComponent);

        CorrectHWComponent build();
    }
}