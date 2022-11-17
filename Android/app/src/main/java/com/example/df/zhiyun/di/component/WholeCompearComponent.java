package com.example.df.zhiyun.di.component;

import com.example.df.zhiyun.di.module.WholeCompearModule;
import com.example.df.zhiyun.mvp.contract.WholeCompearContract;
import com.example.df.zhiyun.mvp.ui.fragment.WholeCompearFragment;
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
@Component(modules = WholeCompearModule.class, dependencies = AppComponent.class)
public interface WholeCompearComponent {
    void inject(WholeCompearFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WholeCompearComponent.Builder view(WholeCompearContract.View view);

        WholeCompearComponent.Builder appComponent(AppComponent appComponent);

        WholeCompearComponent build();
    }
}