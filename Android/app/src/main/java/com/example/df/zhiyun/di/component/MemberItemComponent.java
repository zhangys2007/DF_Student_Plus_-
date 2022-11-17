package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.MemberItemModule;
import com.example.df.zhiyun.mvp.contract.MemberItemContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.ui.activity.MemberItemActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 16:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MemberItemModule.class, dependencies = AppComponent.class)
public interface MemberItemComponent {
    void inject(MemberItemActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MemberItemComponent.Builder view(MemberItemContract.View view);

        MemberItemComponent.Builder appComponent(AppComponent appComponent);

        MemberItemComponent build();
    }
}