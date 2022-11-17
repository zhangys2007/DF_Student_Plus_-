package com.example.df.zhiyun.educate.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.educate.di.module.DualTableModule;
import com.example.df.zhiyun.educate.mvp.contract.DualTableContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.educate.mvp.ui.activity.DualTableActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/12/2020 09:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DualTableModule.class, dependencies = AppComponent.class)
public interface DualTableComponent {
    void inject(DualTableActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        DualTableComponent.Builder view(DualTableContract.View view);
        DualTableComponent.Builder appComponent(AppComponent appComponent);
        DualTableComponent build();
    }
}