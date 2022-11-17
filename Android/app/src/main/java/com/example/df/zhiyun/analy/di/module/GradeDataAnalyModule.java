package com.example.df.zhiyun.analy.di.module;

import android.app.Activity;

import com.example.df.zhiyun.analy.mvp.contract.GradeDataAnalyContract;
import com.example.df.zhiyun.analy.mvp.model.GradeDataAnalyModel;
import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 12:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class GradeDataAnalyModule {

    @Binds
    abstract GradeDataAnalyContract.Model bindClassMemberModel(GradeDataAnalyModel model);


//    @ActivityScope
//    @Provides
//    @Named(GradeDataAnalyContract.View.KEY_HOMEWORK_ID)
//    static String provideId(GradeDataAnalyContract.View view) {
//        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), GradeDataAnalyContract.View.KEY_HOMEWORK_ID);
//    }

    @ActivityScope
    @Provides
    @Named(GradeDataAnalyContract.View.KEY_FZ)
    static Integer provideFZId(GradeDataAnalyContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(GradeDataAnalyContract.View.KEY_FZ, 0);
    }

    @ActivityScope
    @Provides
    @Named(GradeDataAnalyContract.View.KEY_TYPE)
    static Integer provideTYPE(GradeDataAnalyContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(GradeDataAnalyContract.View.KEY_TYPE, 0);
    }

    @ActivityScope
    @Provides
    @Named(GradeDataAnalyContract.View.KEY_SCHOOLID)
    static Integer provideSchoolId(GradeDataAnalyContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(GradeDataAnalyContract.View.KEY_SCHOOLID, 0);
    }

    @ActivityScope
    @Provides
    @Named(GradeDataAnalyContract.View.KEY_GRADEID)
    static Integer provideGradeId(GradeDataAnalyContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(GradeDataAnalyContract.View.KEY_GRADEID, 0);
    }

    @ActivityScope
    @Provides
    @Named(GradeDataAnalyContract.View.KEY_SUBJID)
    static Integer provideSubjId(GradeDataAnalyContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(GradeDataAnalyContract.View.KEY_SUBJID, 0);
    }
}