package com.example.df.zhiyun.preview.di.component;

import com.example.df.zhiyun.preview.di.module.PrevHWcomModule;
import com.example.df.zhiyun.preview.mvp.contract.PrevHWcomContract;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWcomFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2019 11:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PrevHWcomModule.class, dependencies = AppComponent.class)
public interface PrevHWcomComponent {
    void inject(PrevHWcomFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PrevHWcomComponent.Builder view(PrevHWcomContract.View view);

        PrevHWcomComponent.Builder appComponent(AppComponent appComponent);

        PrevHWcomComponent build();
    }
}