package com.example.df.zhiyun.correct.di.module;

import android.app.Activity;
import android.content.Context;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.correct.mvp.contract.CorrectSomeOneContract;
import com.example.df.zhiyun.correct.mvp.model.CorrectSomeOneModel;
import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 14:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CorrectSomeOneModule {

    @Binds
    abstract CorrectSomeOneContract.Model bindCorrectSomeOneModel(CorrectSomeOneModel model);

    @ActivityScope
    @Provides
    static String provideId(CorrectSomeOneContract.View view) {
        return ((Activity)view).getIntent().getStringExtra(CorrectSomeOneContract.View.KEY);
    }

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(CorrectSomeOneContract.View view) {
        return KProgressHUD.create((Context)view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @ActivityScope
    @Provides
    static Boolean provideCorrect(CorrectSomeOneContract.View view) {
        return ((Activity)view).getIntent().getBooleanExtra(CorrectSomeOneContract.View.KEY_CORRECT,false);
    }
}