package com.example.df.zhiyun.comment.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.comment.di.module.CommentUsageModule;
import com.example.df.zhiyun.comment.mvp.contract.CommentUsageContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.comment.mvp.ui.activity.CommentUsageActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/2019 11:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CommentUsageModule.class, dependencies = AppComponent.class)
public interface CommentUsageComponent {
    void inject(CommentUsageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommentUsageComponent.Builder view(CommentUsageContract.View view);

        CommentUsageComponent.Builder appComponent(AppComponent appComponent);

        CommentUsageComponent build();
    }
}