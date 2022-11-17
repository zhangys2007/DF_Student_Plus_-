package com.example.df.zhiyun.di.component;

import com.example.df.zhiyun.di.module.PutHWDetailModule;
import com.example.df.zhiyun.mvp.contract.PutHWDetailContract;
import com.example.df.zhiyun.mvp.ui.activity.PutHWDetailActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 15:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = {PutHWDetailModule.class}, dependencies = AppComponent.class)
public interface PutHWDetailComponent {
    void inject(PutHWDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PutHWDetailComponent.Builder view(PutHWDetailContract.View view);

        PutHWDetailComponent.Builder appComponent(AppComponent appComponent);

        PutHWDetailComponent build();
    }
}