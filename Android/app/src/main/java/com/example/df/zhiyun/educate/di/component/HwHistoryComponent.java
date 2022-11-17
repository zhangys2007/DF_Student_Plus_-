package com.example.df.zhiyun.educate.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.educate.di.module.HwHistoryModule;
import com.example.df.zhiyun.educate.mvp.contract.HwHistoryContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.educate.mvp.ui.activity.HwHistoryActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/07/2020 17:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HwHistoryModule.class, dependencies = AppComponent.class)
public interface HwHistoryComponent {
    void inject(HwHistoryActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        HwHistoryComponent.Builder view(HwHistoryContract.View view);
        HwHistoryComponent.Builder appComponent(AppComponent appComponent);
        HwHistoryComponent build();
    }
}