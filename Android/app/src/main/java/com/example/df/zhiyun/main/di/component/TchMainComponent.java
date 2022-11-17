package com.example.df.zhiyun.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.main.di.module.TchMainModule;
import com.example.df.zhiyun.main.mvp.contract.TchMainContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.main.mvp.ui.activity.TchMainActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 18:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TchMainModule.class, dependencies = AppComponent.class)
public interface TchMainComponent {
    void inject(TchMainActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TchMainComponent.Builder view(TchMainContract.View view);

        TchMainComponent.Builder appComponent(AppComponent appComponent);

        TchMainComponent build();
    }
}