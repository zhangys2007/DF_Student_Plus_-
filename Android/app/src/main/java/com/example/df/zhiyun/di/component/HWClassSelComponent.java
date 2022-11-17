package com.example.df.zhiyun.di.component;

import com.example.df.zhiyun.di.module.HWClassSelModule;
import com.example.df.zhiyun.mvp.contract.HWClassSelContract;
import com.example.df.zhiyun.mvp.ui.activity.HWClassSelActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 16:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HWClassSelModule.class, dependencies = AppComponent.class)
public interface HWClassSelComponent {
    void inject(HWClassSelActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HWClassSelComponent.Builder view(HWClassSelContract.View view);

        HWClassSelComponent.Builder appComponent(AppComponent appComponent);

        HWClassSelComponent build();
    }
}