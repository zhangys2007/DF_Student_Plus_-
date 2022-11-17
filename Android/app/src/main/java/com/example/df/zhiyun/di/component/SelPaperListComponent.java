package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.SelPaperListModule;
import com.example.df.zhiyun.mvp.contract.SelPaperListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.ui.activity.SelPaperListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SelPaperListModule.class, dependencies = AppComponent.class)
public interface SelPaperListComponent {
    void inject(SelPaperListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SelPaperListComponent.Builder view(SelPaperListContract.View view);

        SelPaperListComponent.Builder appComponent(AppComponent appComponent);

        SelPaperListComponent build();
    }
}