package com.example.df.zhiyun.educate.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.example.df.zhiyun.educate.di.module.PutClsHWModule;
import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.educate.mvp.contract.PutClsHWContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.educate.mvp.ui.activity.PutClsHWActivity;


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
@Component(modules = PutClsHWModule.class, dependencies = AppComponent.class)
public interface PutClsHWComponent {
    void inject(PutClsHWActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PutClsHWComponent.Builder view(PutClsHWContract.View view);

        PutClsHWComponent.Builder appComponent(AppComponent appComponent);

        PutClsHWComponent build();
    }
}