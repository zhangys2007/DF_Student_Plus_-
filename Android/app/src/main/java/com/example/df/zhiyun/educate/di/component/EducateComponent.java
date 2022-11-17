package com.example.df.zhiyun.educate.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.educate.di.module.EducateModule;
import com.example.df.zhiyun.educate.mvp.contract.EducateContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.educate.mvp.ui.fragment.EducateFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/06/2020 09:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = EducateModule.class, dependencies = AppComponent.class)
public interface EducateComponent {
    void inject(EducateFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EducateComponent.Builder view(EducateContract.View view);

        EducateComponent.Builder appComponent(AppComponent appComponent);

        EducateComponent build();
    }
}