package com.example.df.zhiyun.analy.di.module;

import android.app.Activity;
import android.content.Context;

import com.example.df.zhiyun.analy.mvp.contract.GrowthTraceContract;
import com.example.df.zhiyun.analy.mvp.model.GrowthTraceModel;
import com.example.df.zhiyun.mvp.model.entity.GrowthTraceItem;
import com.jess.arms.di.scope.ActivityScope;
import com.kaopiz.kprogresshud.KProgressHUD;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


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
public abstract class GrowthTraceModule {

    @Binds
    abstract GrowthTraceContract.Model bindScoreTraceModel(GrowthTraceModel model);

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(GrowthTraceContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @ActivityScope
    @Provides
    @Named(GrowthTraceContract.View.KEY_SUBJ)
    static String provideSubjectId(GrowthTraceContract.View view) {
        return ((Activity)view).getIntent().getStringExtra(GrowthTraceContract.View.KEY_SUBJ);
    }

    @ActivityScope
    @Provides
    @Named(GrowthTraceContract.View.KEY_SUBJ_NAME)
    static String provideSubjectName(GrowthTraceContract.View view) {
        return ((Activity)view).getIntent().getStringExtra(GrowthTraceContract.View.KEY_SUBJ_NAME);
    }

    @ActivityScope
    @Provides
    static GrowthTraceItem provideGrowthTrace(GrowthTraceContract.View view) {
        return ((Activity)view).getIntent().getParcelableExtra(GrowthTraceContract.View.KEY_DATA);
    }
}