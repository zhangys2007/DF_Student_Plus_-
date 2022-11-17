package com.example.df.zhiyun.correct.di.component;

import com.example.df.zhiyun.correct.di.module.CorrectHWcpModule;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWcpContract;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWcpFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/10/2019 13:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CorrectHWcpModule.class, dependencies = AppComponent.class)
public interface CorrectHWcpComponent {
    void inject(CorrectHWcpFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CorrectHWcpComponent.Builder view(CorrectHWcpContract.View view);

        CorrectHWcpComponent.Builder appComponent(AppComponent appComponent);

        CorrectHWcpComponent build();
    }
}