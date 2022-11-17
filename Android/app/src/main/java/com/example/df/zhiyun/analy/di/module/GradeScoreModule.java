package com.example.df.zhiyun.analy.di.module;

import android.support.v4.app.Fragment;

import com.example.df.zhiyun.analy.mvp.contract.GradeScoreContract;
import com.example.df.zhiyun.analy.mvp.model.GradeScoreModel;
import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class GradeScoreModule {

    @Binds
    abstract GradeScoreContract.Model bindMyModel(GradeScoreModel model);


    @FragmentScope
    @Provides
    @Named(GradeScoreContract.View.KEY_FZ)
    static Integer provFzId(GradeScoreContract.View view) {
        return ((Fragment)view).getArguments().getInt(GradeScoreContract.View.KEY_FZ,0);
    }

    @FragmentScope
    @Provides
    @Named(GradeScoreContract.View.KEY_TYPE)
    static Integer provType(GradeScoreContract.View view) {
        return ((Fragment)view).getArguments().getInt(GradeScoreContract.View.KEY_TYPE,0);
    }

    @FragmentScope
    @Provides
    @Named(GradeScoreContract.View.KEY_GRADEID)
    static Integer provGrade(GradeScoreContract.View view) {
        return ((Fragment)view).getArguments().getInt(GradeScoreContract.View.KEY_GRADEID,0);
    }

    @FragmentScope
    @Provides
    @Named(GradeScoreContract.View.KEY_SCHOOLID)
    static Integer provSchoolId(GradeScoreContract.View view) {
        return ((Fragment)view).getArguments().getInt(GradeScoreContract.View.KEY_SCHOOLID,0);
    }

//    @FragmentScope
//    @Provides
//    @Named(GradeScoreContract.View.KEY_HOMEWORK_ID)
//    static String provHomeworkId(GradeScoreContract.View view) {
//        return ((Fragment)view).getArguments().getString(GradeScoreContract.View.KEY_HOMEWORK_ID,"0");
//    }

    @FragmentScope
    @Provides
    @Named(GradeScoreContract.View.KEY_SUBJID)
    static Integer provSubjId(GradeScoreContract.View view) {
        return ((Fragment)view).getArguments().getInt(GradeScoreContract.View.KEY_SUBJID,0);
    }
}