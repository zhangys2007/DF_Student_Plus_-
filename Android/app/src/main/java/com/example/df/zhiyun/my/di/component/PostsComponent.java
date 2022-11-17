package com.example.df.zhiyun.my.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.my.di.module.PostsModule;
import com.example.df.zhiyun.my.mvp.contract.PostsContract;

import com.jess.arms.di.scope.ActivityScope;

import com.example.df.zhiyun.my.mvp.ui.activity.PostsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 13:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PostsModule.class, dependencies = AppComponent.class)
public interface PostsComponent {
    void inject(PostsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PostsComponent.Builder view(PostsContract.View view);

        PostsComponent.Builder appComponent(AppComponent appComponent);

        PostsComponent build();
    }
}