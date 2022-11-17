package com.example.df.zhiyun.analy.di.module;

import android.content.Context;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.analy.mvp.contract.ScoreTraceContract;
import com.example.df.zhiyun.analy.mvp.model.ScoreTraceModel;
import com.kaopiz.kprogresshud.KProgressHUD;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 10:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ScoreTraceModule {

    @Binds
    abstract ScoreTraceContract.Model bindScoreTraceModel(ScoreTraceModel model);

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(ScoreTraceContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }
}