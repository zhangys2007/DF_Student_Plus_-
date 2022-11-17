package com.example.df.zhiyun.preview.di.component;

import com.example.df.zhiyun.preview.di.module.PreviewHomeworkModule;
import com.example.df.zhiyun.preview.mvp.contract.PreviewHomeworkContract;
import com.example.df.zhiyun.preview.mvp.ui.activity.PreviewHomeworkActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PreviewHomeworkModule.class, dependencies = AppComponent.class)
public interface PreviewHomeworkComponent {
    void inject(PreviewHomeworkActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PreviewHomeworkComponent.Builder view(PreviewHomeworkContract.View view);

        PreviewHomeworkComponent.Builder appComponent(AppComponent appComponent);

        PreviewHomeworkComponent build();
    }
}