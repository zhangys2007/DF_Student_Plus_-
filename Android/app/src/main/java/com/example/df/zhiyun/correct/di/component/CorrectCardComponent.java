package com.example.df.zhiyun.correct.di.component;

import com.example.df.zhiyun.correct.di.module.CorrectCardModule;
import com.example.df.zhiyun.correct.mvp.contract.CorrectCardContract;
import com.example.df.zhiyun.correct.mvp.ui.activity.CorrectCardActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2019 12:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CorrectCardModule.class, dependencies = AppComponent.class)
public interface CorrectCardComponent {
    void inject(CorrectCardActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CorrectCardComponent.Builder view(CorrectCardContract.View view);

        CorrectCardComponent.Builder appComponent(AppComponent appComponent);

        CorrectCardComponent build();
    }
}