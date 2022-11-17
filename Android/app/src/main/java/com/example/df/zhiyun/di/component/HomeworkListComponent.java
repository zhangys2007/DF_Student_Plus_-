package com.example.df.zhiyun.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.di.module.HomeworkListModule;
import com.example.df.zhiyun.mvp.contract.HomeworkListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.ui.activity.HomeworkListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/20/2019 19:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HomeworkListModule.class, dependencies = AppComponent.class)
public interface HomeworkListComponent {
    void inject(HomeworkListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeworkListComponent.Builder view(HomeworkListContract.View view);

        HomeworkListComponent.Builder appComponent(AppComponent appComponent);

        HomeworkListComponent build();
    }
}