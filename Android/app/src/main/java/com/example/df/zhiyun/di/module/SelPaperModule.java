package com.example.df.zhiyun.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.example.df.zhiyun.mvp.contract.SelPaperContract;
import com.example.df.zhiyun.mvp.model.SelPaperModel;
import com.example.df.zhiyun.mvp.ui.activity.SelPaperActivity;
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
public abstract class SelPaperModule {

    @Binds
    abstract SelPaperContract.Model bindSetHomeworkModel(SelPaperModel model);

    @Provides
    @ActivityScope
    static String provideHomeworkId(SelPaperContract.View view) {
        return ((Activity)view).getIntent().getStringExtra(SelPaperActivity.KEY);
    }

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(SelPaperContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(SelPaperContract.View view) {
        return new RxPermissions( (FragmentActivity)view);
    }

    @Provides
    @ActivityScope
    @Named("type")
    static Integer provideType(SelPaperContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(SelPaperActivity.KEY_TYPE,0);
    }

    @Provides
    @ActivityScope
    @Named("subjectId")
    static Integer provideSubjectId(SelPaperContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(SelPaperActivity.KEY_SUBJECTID,0);
    }
}