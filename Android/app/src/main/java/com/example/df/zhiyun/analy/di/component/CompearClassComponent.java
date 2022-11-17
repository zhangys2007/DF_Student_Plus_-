package com.example.df.zhiyun.analy.di.component;

import com.example.df.zhiyun.analy.di.module.CompearClassModule;
import com.example.df.zhiyun.analy.mvp.contract.CompearClassContract;
import com.example.df.zhiyun.analy.mvp.ui.fragment.CompearClassFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CompearClassModule.class, dependencies = AppComponent.class)
public interface CompearClassComponent {
    void inject(CompearClassFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CompearClassComponent.Builder view(CompearClassContract.View view);

        CompearClassComponent.Builder appComponent(AppComponent appComponent);

        CompearClassComponent build();
    }
}