package com.example.df.zhiyun.di.component;

import com.example.df.zhiyun.di.module.SelPaperModule;
import com.example.df.zhiyun.mvp.contract.SelPaperContract;
import com.example.df.zhiyun.mvp.ui.activity.SelPaperActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SelPaperModule.class, dependencies = AppComponent.class)
public interface SelPaperComponent {
    void inject(SelPaperActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SelPaperComponent.Builder view(SelPaperContract.View view);

        SelPaperComponent.Builder appComponent(AppComponent appComponent);

        SelPaperComponent build();
    }
}