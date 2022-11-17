package com.example.df.zhiyun.my.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.my.di.module.MsgModule;
import com.example.df.zhiyun.my.mvp.contract.MsgContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.my.mvp.ui.activity.MsgActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 13:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MsgModule.class, dependencies = AppComponent.class)
public interface MsgComponent {
    void inject(MsgActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MsgComponent.Builder view(MsgContract.View view);

        MsgComponent.Builder appComponent(AppComponent appComponent);

        MsgComponent build();
    }
}