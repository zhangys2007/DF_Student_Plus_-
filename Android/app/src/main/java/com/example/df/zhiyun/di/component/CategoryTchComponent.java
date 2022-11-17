package com.example.df.zhiyun.di.component;

import com.example.df.zhiyun.di.module.CategoryTchModule;
import com.example.df.zhiyun.mvp.contract.CategoryTchContract;
import com.example.df.zhiyun.mvp.ui.activity.CategoryTchActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/18/2019 00:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CategoryTchModule.class, dependencies = AppComponent.class)
public interface CategoryTchComponent {
    void inject(CategoryTchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CategoryTchComponent.Builder view(CategoryTchContract.View view);

        CategoryTchComponent.Builder appComponent(AppComponent appComponent);

        CategoryTchComponent build();
    }
}