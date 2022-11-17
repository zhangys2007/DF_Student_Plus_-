package com.example.df.zhiyun.correct.di.component;

import com.example.df.zhiyun.correct.di.module.CorrectHWcomModule;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWcomContract;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWcomFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2019 11:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CorrectHWcomModule.class, dependencies = AppComponent.class)
public interface CorrectHWcomComponent {
    void inject(CorrectHWcomFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CorrectHWcomComponent.Builder view(CorrectHWcomContract.View view);

        CorrectHWcomComponent.Builder appComponent(AppComponent appComponent);

        CorrectHWcomComponent build();
    }
}