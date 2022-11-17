package com.example.df.zhiyun.comment.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.comment.di.module.CommentAnalyDetailModule;
import com.example.df.zhiyun.comment.mvp.contract.CommentAnalyDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.comment.mvp.ui.activity.CommentAnalyDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 15:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CommentAnalyDetailModule.class, dependencies = AppComponent.class)
public interface CommentAnalyDetailComponent {
    void inject(CommentAnalyDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommentAnalyDetailComponent.Builder view(CommentAnalyDetailContract.View view);

        CommentAnalyDetailComponent.Builder appComponent(AppComponent appComponent);

        CommentAnalyDetailComponent build();
    }
}