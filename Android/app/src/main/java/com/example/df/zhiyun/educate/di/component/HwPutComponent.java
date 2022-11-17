package com.example.df.zhiyun.educate.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.educate.di.module.HwPutModule;
import com.example.df.zhiyun.educate.mvp.contract.HwPutContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.educate.mvp.ui.activity.HwPutActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/08/2020 18:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HwPutModule.class, dependencies = AppComponent.class)
public interface HwPutComponent {
    void inject(HwPutActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        HwPutComponent.Builder view(HwPutContract.View view);
        HwPutComponent.Builder appComponent(AppComponent appComponent);
        HwPutComponent build();
    }
}