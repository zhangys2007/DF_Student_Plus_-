package com.example.df.zhiyun.preview.di.component;

import com.example.df.zhiyun.preview.di.module.PrevHWsepModule;
import com.example.df.zhiyun.preview.mvp.contract.PrevHWsepContract;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWsepFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 16:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PrevHWsepModule.class, dependencies = AppComponent.class)
public interface PrevHWsepComponent {
    void inject(PrevHWsepFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PrevHWsepComponent.Builder view(PrevHWsepContract.View view);

        PrevHWsepComponent.Builder appComponent(AppComponent appComponent);

        PrevHWsepComponent build();
    }
}