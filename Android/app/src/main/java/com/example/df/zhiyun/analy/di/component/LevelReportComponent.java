package com.example.df.zhiyun.analy.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.analy.di.module.LevelReportModule;
import com.example.df.zhiyun.analy.mvp.contract.LevelReportContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.analy.mvp.ui.activity.LevelReportActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/14/2020 09:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LevelReportModule.class, dependencies = AppComponent.class)
public interface LevelReportComponent {
    void inject(LevelReportActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        LevelReportComponent.Builder view(LevelReportContract.View view);
        LevelReportComponent.Builder appComponent(AppComponent appComponent);
        LevelReportComponent build();
    }
}