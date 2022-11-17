package com.example.df.zhiyun.setting.di.module;

import android.content.Context;

import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.setting.mvp.contract.FeedbackContract;
import com.example.df.zhiyun.setting.mvp.model.FeedbackModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/17/2019 22:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class FeedbackModule {

    @Binds
    abstract FeedbackContract.Model bindFeedbackModel(FeedbackModel model);

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(FeedbackContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }
}