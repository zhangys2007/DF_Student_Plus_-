package com.example.df.zhiyun.preview.di.component;

import com.example.df.zhiyun.preview.di.module.PrevHWiptModule;
import com.example.df.zhiyun.preview.mvp.contract.PrevHWiptContract;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWiptFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 17:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PrevHWiptModule.class, dependencies = AppComponent.class)
public interface PrevHWiptComponent {
    void inject(PrevHWiptFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PrevHWiptComponent.Builder view(PrevHWiptContract.View view);

        PrevHWiptComponent.Builder appComponent(AppComponent appComponent);

        PrevHWiptComponent build();
    }
}