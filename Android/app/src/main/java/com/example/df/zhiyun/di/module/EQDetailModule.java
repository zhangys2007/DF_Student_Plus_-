package com.example.df.zhiyun.di.module;

import android.app.Activity;
import android.content.Context;

import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.jess.arms.di.scope.ActivityScope;
import com.kaopiz.kprogresshud.KProgressHUD;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.mvp.contract.EQDetailContract;
import com.example.df.zhiyun.mvp.model.EQDetailModel;
import com.example.df.zhiyun.mvp.ui.activity.EQDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/02/2019 09:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class EQDetailModule {

    @Binds
    abstract EQDetailContract.Model bindEQDetailModel(EQDetailModel model);

    @ActivityScope
    @Provides
    @Named("questionId")
    static String provideQuestionId(EQDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),EQDetailActivity.KEY);
//        return ((Activity)view).getIntent().getStringExtra(EQDetailActivity.KEY);
    }

    @ActivityScope
    @Provides
    @Named("subjectId")
    static String provideSubjectId(EQDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), EQDetailActivity.KEY_SUBJID);
//        return ((Activity)view).getIntent().getStringExtra(EQDetailActivity.KEY_SUBJID);
    }

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(EQDetailContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @ActivityScope
    @Provides
    static Boolean provideHideMyAnswer(EQDetailContract.View view) {
        return ((Activity)view).getIntent().getBooleanExtra(EQDetailActivity.KEY_HIDE_MY,false);
    }
}