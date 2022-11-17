package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.UnpayModule;
import com.example.df.zhiyun.mvp.contract.UnpayContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.ui.activity.UnpayActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 17:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UnpayModule.class, dependencies = AppComponent.class)
public interface UnpayComponent {
    void inject(UnpayActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UnpayComponent.Builder view(UnpayContract.View view);

        UnpayComponent.Builder appComponent(AppComponent appComponent);

        UnpayComponent build();
    }
}