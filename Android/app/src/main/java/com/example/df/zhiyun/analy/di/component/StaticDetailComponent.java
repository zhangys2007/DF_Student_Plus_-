package com.example.df.zhiyun.analy.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.analy.di.module.StaticDetailModule;
import com.example.df.zhiyun.analy.mvp.contract.StaticDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.analy.mvp.ui.activity.StaticDetailActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/16/2020 16:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = StaticDetailModule.class, dependencies = AppComponent.class)
public interface StaticDetailComponent {
    void inject(StaticDetailActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        StaticDetailComponent.Builder view(StaticDetailContract.View view);
        StaticDetailComponent.Builder appComponent(AppComponent appComponent);
        StaticDetailComponent build();
    }
}