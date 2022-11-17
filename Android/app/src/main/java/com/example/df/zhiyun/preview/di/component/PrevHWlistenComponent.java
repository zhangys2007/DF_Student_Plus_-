package com.example.df.zhiyun.preview.di.component;

import com.example.df.zhiyun.preview.di.module.PrevHWlistenModule;
import com.example.df.zhiyun.preview.mvp.contract.PrevHWlistenContract;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWlistenFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/19/2019 11:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PrevHWlistenModule.class, dependencies = AppComponent.class)
public interface PrevHWlistenComponent {
    void inject(PrevHWlistenFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PrevHWlistenComponent.Builder view(PrevHWlistenContract.View view);

        PrevHWlistenComponent.Builder appComponent(AppComponent appComponent);

        PrevHWlistenComponent build();
    }
}