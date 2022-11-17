package com.example.df.zhiyun.analy.di.module;

import android.app.Activity;

import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.analy.mvp.contract.ClassDataAnalyContract;
import com.example.df.zhiyun.analy.mvp.model.ClassDataAnalyModel;
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
public abstract class ClassDataAnalyModule {

    @Binds
    abstract ClassDataAnalyContract.Model bindClassMemberModel(ClassDataAnalyModel model);


    @ActivityScope
    @Provides
    @Named(ClassDataAnalyContract.View.KEY_HOMEWORK_ID)
    static String provideId(ClassDataAnalyContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), ClassDataAnalyContract.View.KEY_HOMEWORK_ID);
    }

    @ActivityScope
    @Provides
    @Named(ClassDataAnalyContract.View.KEY_FZ)
    static Integer provideFZId(ClassDataAnalyContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(ClassDataAnalyContract.View.KEY_FZ, 0);
    }

    @ActivityScope
    @Provides
    @Named(ClassDataAnalyContract.View.KEY_TYPE)
    static Integer provideTYPE(ClassDataAnalyContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(ClassDataAnalyContract.View.KEY_TYPE, 0);
    }

    @ActivityScope
    @Provides
    @Named(ClassDataAnalyContract.View.KEY_SCHOOLID)
    static Integer provideSchoolId(ClassDataAnalyContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(ClassDataAnalyContract.View.KEY_SCHOOLID, 0);
    }

    @ActivityScope
    @Provides
    @Named(ClassDataAnalyContract.View.KEY_GRADEID)
    static Integer provideGradeId(ClassDataAnalyContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(ClassDataAnalyContract.View.KEY_GRADEID, 0);
    }

    @ActivityScope
    @Provides
    @Named(ClassDataAnalyContract.View.KEY_SUBJID)
    static Integer provideSubjId(ClassDataAnalyContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(ClassDataAnalyContract.View.KEY_SUBJID, 0);
    }
}