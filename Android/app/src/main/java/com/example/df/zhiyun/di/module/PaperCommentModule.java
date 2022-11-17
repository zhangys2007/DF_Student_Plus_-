package com.example.df.zhiyun.di.module;

import android.support.v4.app.Fragment;

import com.example.df.zhiyun.mvp.contract.PaperCommentContract;
import com.example.df.zhiyun.mvp.model.PaperCommentModel;
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
public abstract class PaperCommentModule {

    @Binds
    abstract PaperCommentContract.Model bindMyModel(PaperCommentModel model);


    @FragmentScope
    @Provides
    @Named(PaperCommentContract.View.KEY_FZ)
    static Integer provFzId(PaperCommentContract.View view) {
        return ((Fragment)view).getArguments().getInt(PaperCommentContract.View.KEY_FZ,0);
    }

    @FragmentScope
    @Provides
    @Named(PaperCommentContract.View.KEY_TYPE)
    static Integer provType(PaperCommentContract.View view) {
        return ((Fragment)view).getArguments().getInt(PaperCommentContract.View.KEY_TYPE,0);
    }

    @FragmentScope
    @Provides
    @Named(PaperCommentContract.View.KEY_GRADEID)
    static Integer provGrade(PaperCommentContract.View view) {
        return ((Fragment)view).getArguments().getInt(PaperCommentContract.View.KEY_GRADEID,0);
    }

    @FragmentScope
    @Provides
    @Named(PaperCommentContract.View.KEY_SCHOOLID)
    static Integer provSchoolId(PaperCommentContract.View view) {
        return ((Fragment)view).getArguments().getInt(PaperCommentContract.View.KEY_SCHOOLID,0);
    }

//    @FragmentScope
//    @Provides
//    @Named(PaperCommentContract.View.KEY_HOMEWORK_ID)
//    static String provHomeworkId(PaperCommentContract.View view) {
//        return ((Fragment)view).getArguments().getString(PaperCommentContract.View.KEY_HOMEWORK_ID,"0");
//    }

    @FragmentScope
    @Provides
    @Named(PaperCommentContract.View.KEY_SUBJID)
    static Integer provSubjId(PaperCommentContract.View view) {
        return ((Fragment)view).getArguments().getInt(PaperCommentContract.View.KEY_SUBJID,0);
    }
}