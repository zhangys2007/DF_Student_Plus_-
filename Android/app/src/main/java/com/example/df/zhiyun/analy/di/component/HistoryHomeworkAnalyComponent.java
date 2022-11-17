package com.example.df.zhiyun.analy.di.component;

import com.example.df.zhiyun.analy.di.module.HistoryHomeworkAnalyModule;
import com.example.df.zhiyun.analy.mvp.contract.HistoryHomeworkAnalyContract;
import com.example.df.zhiyun.analy.mvp.ui.activity.HistoryHomeworkAnalyActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 15:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HistoryHomeworkAnalyModule.class, dependencies = AppComponent.class)
public interface HistoryHomeworkAnalyComponent {
    void inject(HistoryHomeworkAnalyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HistoryHomeworkAnalyComponent.Builder view(HistoryHomeworkAnalyContract.View view);

        HistoryHomeworkAnalyComponent.Builder appComponent(AppComponent appComponent);

        HistoryHomeworkAnalyComponent build();
    }
}