package com.example.df.zhiyun.preview.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.example.df.zhiyun.preview.mvp.contract.PreviewHomeworkContract;
import com.example.df.zhiyun.preview.mvp.model.PreviewHomeworkModel;
import com.example.df.zhiyun.preview.mvp.ui.activity.PreviewHomeworkActivity;
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
public abstract class PreviewHomeworkModule {

    @Binds
    abstract PreviewHomeworkContract.Model bindPreviewHomeworkModel(PreviewHomeworkModel model);

    @Provides
    @ActivityScope
    static String provideHomeworkId(PreviewHomeworkContract.View view) {
        return ((Activity)view).getIntent().getStringExtra(PreviewHomeworkActivity.KEY);
    }

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(PreviewHomeworkContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(PreviewHomeworkContract.View view) {
        return new RxPermissions( (FragmentActivity)view);
    }

    @Provides
    @ActivityScope
    @Named("type")
    static Integer provideType(PreviewHomeworkContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(PreviewHomeworkActivity.KEY_TYPE,0);
    }

    @Provides
    @ActivityScope
    @Named("subjectId")
    static Integer provideSubjectId(PreviewHomeworkContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(PreviewHomeworkActivity.KEY_SUBJECTID,0);
    }
}