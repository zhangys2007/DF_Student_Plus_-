package com.example.df.zhiyun.analy.di.module;

import android.support.v4.app.Fragment;

import com.example.df.zhiyun.analy.mvp.contract.CompearClassContract;
import com.example.df.zhiyun.analy.mvp.model.CompearClassModel;
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
public abstract class CompearClassModule {

    @Binds
    abstract CompearClassContract.Model bindMyModel(CompearClassModel model);


    @FragmentScope
    @Provides
    @Named(CompearClassContract.View.KEY_FZ)
    static Integer provFzId(CompearClassContract.View view) {
        return ((Fragment)view).getArguments().getInt(CompearClassContract.View.KEY_FZ,0);
    }

    @FragmentScope
    @Provides
    @Named(CompearClassContract.View.KEY_TYPE)
    static Integer provType(CompearClassContract.View view) {
        return ((Fragment)view).getArguments().getInt(CompearClassContract.View.KEY_TYPE,0);
    }

    @FragmentScope
    @Provides
    @Named(CompearClassContract.View.KEY_GRADEID)
    static Integer provGrade(CompearClassContract.View view) {
        return ((Fragment)view).getArguments().getInt(CompearClassContract.View.KEY_GRADEID,0);
    }

    @FragmentScope
    @Provides
    @Named(CompearClassContract.View.KEY_SCHOOLID)
    static Integer provSchoolId(CompearClassContract.View view) {
        return ((Fragment)view).getArguments().getInt(CompearClassContract.View.KEY_SCHOOLID,0);
    }

    @FragmentScope
    @Provides
    @Named(CompearClassContract.View.KEY_HOMEWORK_ID)
    static String provHomeworkId(CompearClassContract.View view) {
        return ((Fragment)view).getArguments().getString(CompearClassContract.View.KEY_HOMEWORK_ID,"0");
    }

    @FragmentScope
    @Provides
    @Named(CompearClassContract.View.KEY_SUBJID)
    static Integer provSubjId(CompearClassContract.View view) {
        return ((Fragment)view).getArguments().getInt(CompearClassContract.View.KEY_SUBJID,0);
    }
}