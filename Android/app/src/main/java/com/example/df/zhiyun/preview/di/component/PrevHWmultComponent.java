package com.example.df.zhiyun.preview.di.component;

import com.example.df.zhiyun.preview.di.module.PrevHWmultModule;
import com.example.df.zhiyun.preview.mvp.contract.PrevHWmultContract;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWmultFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 12:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PrevHWmultModule.class, dependencies = AppComponent.class)
public interface PrevHWmultComponent {
    void inject(PrevHWmultFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PrevHWmultComponent.Builder view(PrevHWmultContract.View view);

        PrevHWmultComponent.Builder appComponent(AppComponent appComponent);

        PrevHWmultComponent build();
    }
}