package com.example.df.zhiyun.di.component;

import com.example.df.zhiyun.di.module.PaperCommentModule;
import com.example.df.zhiyun.mvp.contract.PaperCommentContract;
import com.example.df.zhiyun.mvp.ui.fragment.PaperCommentFragment;
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
@Component(modules = PaperCommentModule.class, dependencies = AppComponent.class)
public interface PaperCommentComponent {
    void inject(PaperCommentFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PaperCommentComponent.Builder view(PaperCommentContract.View view);

        PaperCommentComponent.Builder appComponent(AppComponent appComponent);

        PaperCommentComponent build();
    }
}