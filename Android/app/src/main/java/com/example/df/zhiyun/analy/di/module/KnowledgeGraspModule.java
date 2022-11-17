package com.example.df.zhiyun.analy.di.module;

import android.support.v4.app.Fragment;

import com.example.df.zhiyun.analy.mvp.contract.KnowledgeGraspContract;
import com.example.df.zhiyun.analy.mvp.model.KnowledgeGraspModel;
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
public abstract class KnowledgeGraspModule {

    @Binds
    abstract KnowledgeGraspContract.Model bindMyModel(KnowledgeGraspModel model);


    @FragmentScope
    @Provides
    @Named(KnowledgeGraspContract.View.KEY_FZ)
    static Integer provFzId(KnowledgeGraspContract.View view) {
        return ((Fragment)view).getArguments().getInt(KnowledgeGraspContract.View.KEY_FZ,0);
    }

    @FragmentScope
    @Provides
    @Named(KnowledgeGraspContract.View.KEY_TYPE)
    static Integer provType(KnowledgeGraspContract.View view) {
        return ((Fragment)view).getArguments().getInt(KnowledgeGraspContract.View.KEY_TYPE,0);
    }

    @FragmentScope
    @Provides
    @Named(KnowledgeGraspContract.View.KEY_GRADEID)
    static Integer provGrade(KnowledgeGraspContract.View view) {
        return ((Fragment)view).getArguments().getInt(KnowledgeGraspContract.View.KEY_GRADEID,0);
    }

    @FragmentScope
    @Provides
    @Named(KnowledgeGraspContract.View.KEY_SCHOOLID)
    static Integer provSchoolId(KnowledgeGraspContract.View view) {
        return ((Fragment)view).getArguments().getInt(KnowledgeGraspContract.View.KEY_SCHOOLID,0);
    }

    @FragmentScope
    @Provides
    @Named(KnowledgeGraspContract.View.KEY_HOMEWORK_ID)
    static String provHomeworkId(KnowledgeGraspContract.View view) {
        return ((Fragment)view).getArguments().getString(KnowledgeGraspContract.View.KEY_HOMEWORK_ID,"0");
    }

    @FragmentScope
    @Provides
    @Named(KnowledgeGraspContract.View.KEY_SUBJID)
    static Integer provSubjId(KnowledgeGraspContract.View view) {
        return ((Fragment)view).getArguments().getInt(KnowledgeGraspContract.View.KEY_SUBJID,0);
    }
}