package com.example.df.zhiyun.paper.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.jess.arms.di.scope.ActivityScope;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.paper.mvp.contract.SetHomeworkContract;
import com.example.df.zhiyun.paper.mvp.model.SetHomeworkModel;

import javax.inject.Named;


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
public abstract class SetHomeworkModule {

    @Binds
    abstract SetHomeworkContract.Model bindSetHomeworkModel(SetHomeworkModel model);

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(SetHomeworkContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(SetHomeworkContract.View view) {
        return new RxPermissions( (FragmentActivity)view);
    }


    @Provides
    @ActivityScope
    @Named(SetHomeworkContract.View.KEY_SUBJECTID)
    static Integer provideSubjectId(SetHomeworkContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(SetHomeworkContract.View.KEY_SUBJECTID,0);
    }

    @Provides
    @ActivityScope
    @Named(SetHomeworkContract.View.KEY_JOB_TYPE)
    static Integer provideJobType(SetHomeworkContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(SetHomeworkContract.View.KEY_JOB_TYPE,0);
    }

    @Provides
    @ActivityScope
    @Named(SetHomeworkContract.View.KEY_STD_HW_ID)
    static String provideSTDHWId(SetHomeworkContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), SetHomeworkContract.View.KEY_STD_HW_ID);
    }

    @Provides
    @ActivityScope
    @Named(SetHomeworkContract.View.KEY_UUID)
    static String provideUUID(SetHomeworkContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), SetHomeworkContract.View.KEY_UUID);
    }

    @Provides
    @ActivityScope
    @Named(SetHomeworkContract.View.KEY_SCHOOLID)
    static String provideSchoolID(SetHomeworkContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), SetHomeworkContract.View.KEY_SCHOOLID);
    }
}