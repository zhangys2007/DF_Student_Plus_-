package com.example.df.zhiyun.preview.di.component;

import com.example.df.zhiyun.preview.di.module.PrevHWsmpModule;
import com.example.df.zhiyun.preview.mvp.contract.PrevHWsmpContract;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWsmpFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2019 11:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PrevHWsmpModule.class, dependencies = AppComponent.class)
public interface PrevHWsmpComponent {
    void inject(PrevHWsmpFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PrevHWsmpComponent.Builder view(PrevHWsmpContract.View view);

        PrevHWsmpComponent.Builder appComponent(AppComponent appComponent);

        PrevHWsmpComponent build();
    }
}