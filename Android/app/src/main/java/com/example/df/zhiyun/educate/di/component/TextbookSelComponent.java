package com.example.df.zhiyun.educate.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.educate.di.module.TextbookSelModule;
import com.example.df.zhiyun.educate.mvp.contract.TextbookSelContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.educate.mvp.ui.activity.TextbookSelActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/11/2020 18:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TextbookSelModule.class, dependencies = AppComponent.class)
public interface TextbookSelComponent {
    void inject(TextbookSelActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        TextbookSelComponent.Builder view(TextbookSelContract.View view);
        TextbookSelComponent.Builder appComponent(AppComponent appComponent);
        TextbookSelComponent build();
    }
}