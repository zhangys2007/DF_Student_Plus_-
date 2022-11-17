package com.example.df.zhiyun.my.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.df.zhiyun.my.di.module.MyTchModule;
import com.example.df.zhiyun.my.mvp.contract.MyTchContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.my.mvp.ui.fragment.MyTchFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 09:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MyTchModule.class, dependencies = AppComponent.class)
public interface MyTchComponent {
    void inject(MyTchFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyTchComponent.Builder view(MyTchContract.View view);

        MyTchComponent.Builder appComponent(AppComponent appComponent);

        MyTchComponent build();
    }
}