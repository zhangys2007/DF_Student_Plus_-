package com.example.df.zhiyun.comment.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.comment.di.module.CommentListModule;
import com.example.df.zhiyun.comment.mvp.contract.CommentListContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.comment.mvp.ui.fragment.CommentListFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 10:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CommentListModule.class, dependencies = AppComponent.class)
public interface CommentListComponent {
    void inject(CommentListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommentListComponent.Builder view(CommentListContract.View view);

        CommentListComponent.Builder appComponent(AppComponent appComponent);

        CommentListComponent build();
    }
}