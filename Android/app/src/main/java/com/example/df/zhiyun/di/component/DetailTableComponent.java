package com.example.df.zhiyun.di.component;

import com.example.df.zhiyun.di.module.DetailTableModule;
import com.example.df.zhiyun.mvp.contract.DetailTableContract;
import com.example.df.zhiyun.mvp.ui.fragment.DetailTableFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 11:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = DetailTableModule.class, dependencies = AppComponent.class)
public interface DetailTableComponent {
    void inject(DetailTableFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DetailTableComponent.Builder view(DetailTableContract.View view);

        DetailTableComponent.Builder appComponent(AppComponent appComponent);

        DetailTableComponent build();
    }
}