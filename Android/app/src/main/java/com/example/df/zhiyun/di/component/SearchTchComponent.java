package com.example.df.zhiyun.di.component;

import com.example.df.zhiyun.di.module.SearchTchModule;
import com.example.df.zhiyun.mvp.contract.SearchTchContract;
import com.example.df.zhiyun.mvp.ui.activity.SearchActivity;
import com.example.df.zhiyun.mvp.ui.activity.SearchTchActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 16:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SearchTchModule.class, dependencies = AppComponent.class)
public interface SearchTchComponent {
    void inject(SearchTchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchTchComponent.Builder view(SearchTchContract.View view);

        SearchTchComponent.Builder appComponent(AppComponent appComponent);

        SearchTchComponent build();
    }
}