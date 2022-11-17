package com.example.df.zhiyun.correct.di.component;

import com.example.df.zhiyun.correct.di.module.CorrectHWselModule;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWselContract;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWselFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 15:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CorrectHWselModule.class, dependencies = AppComponent.class)
public interface CorrectHWselComponent {
    void inject(CorrectHWselFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CorrectHWselComponent.Builder view(CorrectHWselContract.View view);

        CorrectHWselComponent.Builder appComponent(AppComponent appComponent);

        CorrectHWselComponent build();
    }
}