package com.example.df.zhiyun.preview.di.component;

import com.example.df.zhiyun.preview.di.module.PrevHWcpModule;
import com.example.df.zhiyun.preview.mvp.contract.PrevHWcpContract;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWcpFragment;
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
@Component(modules = PrevHWcpModule.class, dependencies = AppComponent.class)
public interface PrevHWcpComponent {
    void inject(PrevHWcpFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PrevHWcpComponent.Builder view(PrevHWcpContract.View view);

        PrevHWcpComponent.Builder appComponent(AppComponent appComponent);

        PrevHWcpComponent build();
    }
}