package com.example.df.zhiyun.analy.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.analy.di.module.ClsHwReportModule;
import com.example.df.zhiyun.analy.mvp.contract.ClsHwReportContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.analy.mvp.ui.activity.ClsHwReportActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/25/2020 10:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ClsHwReportModule.class, dependencies = AppComponent.class)
public interface ClsHwReportComponent {
    void inject(ClsHwReportActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        ClsHwReportComponent.Builder view(ClsHwReportContract.View view);
        ClsHwReportComponent.Builder appComponent(AppComponent appComponent);
        ClsHwReportComponent build();
    }
}