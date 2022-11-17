package com.example.df.zhiyun.correct.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWContract;
import com.example.df.zhiyun.correct.mvp.model.CorrectHWModel;
import com.example.df.zhiyun.correct.mvp.ui.activity.CorrectHWActivity;
import com.jess.arms.di.scope.ActivityScope;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


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
@Module
public abstract class CorrectHWModule {

    @Binds
    abstract CorrectHWContract.Model bindPreviewHomeworkModel(CorrectHWModel model);


    @ActivityScope
    @Provides
    static KProgressHUD provideKP(CorrectHWContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(CorrectHWContract.View view) {
        return new RxPermissions( (FragmentActivity)view);
    }

    @ActivityScope
    @Provides
    @Named(CorrectHWContract.View.KEY_TYPE)
    static Integer provideType(CorrectHWContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(CorrectHWActivity.KEY_TYPE,0);
    }

    @ActivityScope
    @Provides
    @Named(CorrectHWContract.View.KEY_STUDENT_COUNT)
    static Integer provideStudentCount(CorrectHWContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(CorrectHWActivity.KEY_STUDENT_COUNT,1);
    }

    @ActivityScope
    @Provides
    @Named(CorrectHWContract.View.KEY_ID)
    static String provideId(CorrectHWContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CorrectHWContract.View.KEY_ID);
//        return ((Activity)view).getIntent().getStringExtra(CorrectHWContract.View.KEY_ID);
    }

    @ActivityScope
    @Provides
    static Boolean provideCorrect(CorrectHWContract.View view) {
        return ((Activity)view).getIntent().getBooleanExtra(CorrectHWContract.View.KEY_CORRECT,false);
    }
}