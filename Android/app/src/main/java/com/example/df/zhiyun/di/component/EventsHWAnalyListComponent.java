package com.example.df.zhiyun.di.component;

import com.example.df.zhiyun.di.module.EventsHWAnalyListModule;
import com.example.df.zhiyun.mvp.contract.EventsHWAnalyListContract;
import com.example.df.zhiyun.mvp.ui.activity.EventsHWAnalyListActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 10:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EventsHWAnalyListModule.class, dependencies = AppComponent.class)
public interface EventsHWAnalyListComponent {
    void inject(EventsHWAnalyListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EventsHWAnalyListComponent.Builder view(EventsHWAnalyListContract.View view);

        EventsHWAnalyListComponent.Builder appComponent(AppComponent appComponent);

        EventsHWAnalyListComponent build();
    }
}