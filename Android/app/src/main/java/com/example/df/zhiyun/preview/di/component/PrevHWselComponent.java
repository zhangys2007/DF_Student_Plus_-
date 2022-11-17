package com.example.df.zhiyun.preview.di.component;

import com.example.df.zhiyun.preview.di.module.PrevHWselModule;
import com.example.df.zhiyun.preview.mvp.contract.PrevHWselContract;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWselFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 15:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PrevHWselModule.class, dependencies = AppComponent.class)
public interface PrevHWselComponent {
    void inject(PrevHWselFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PrevHWselComponent.Builder view(PrevHWselContract.View view);

        PrevHWselComponent.Builder appComponent(AppComponent appComponent);

        PrevHWselComponent build();
    }
}