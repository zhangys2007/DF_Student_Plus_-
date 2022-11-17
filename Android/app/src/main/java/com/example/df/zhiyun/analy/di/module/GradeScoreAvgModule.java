package com.example.df.zhiyun.analy.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.mvp.contract.GradeScoreAvgContract;
import com.example.df.zhiyun.analy.mvp.model.GradeScoreAvgModel;
import com.example.df.zhiyun.mvp.model.entity.GradeAvg;
import com.example.df.zhiyun.mvp.ui.adapter.GradeAvgAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 11:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class GradeScoreAvgModule {

    @Binds
    abstract GradeScoreAvgContract.Model bindHomeworkNewModel(GradeScoreAvgModel model);

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(GradeScoreAvgContract.View view) {
        Context context = ((Fragment)view).getContext();
        return new RecycleViewDivider(context,LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(context, R.color.divider));
    }

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(GradeScoreAvgContract.View view) {
        Context context = ((Fragment)view).getContext();
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @FragmentScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<GradeAvg> list){
        return new GradeAvgAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<GradeAvg> provideHomeworkList() {
        return new ArrayList<>();
    }


    @FragmentScope
    @Provides
    @Named(GradeScoreAvgContract.View.KEY_FZ)
    static Integer provFzId(GradeScoreAvgContract.View view) {
        return ((Fragment)view).getArguments().getInt(GradeScoreAvgContract.View.KEY_FZ,0);
    }

    @FragmentScope
    @Provides
    @Named(GradeScoreAvgContract.View.KEY_TYPE)
    static Integer provType(GradeScoreAvgContract.View view) {
        return ((Fragment)view).getArguments().getInt(GradeScoreAvgContract.View.KEY_TYPE,0);
    }

    @FragmentScope
    @Provides
    @Named(GradeScoreAvgContract.View.KEY_GRADEID)
    static Integer provGrade(GradeScoreAvgContract.View view) {
        return ((Fragment)view).getArguments().getInt(GradeScoreAvgContract.View.KEY_GRADEID,0);
    }

    @FragmentScope
    @Provides
    @Named(GradeScoreAvgContract.View.KEY_SCHOOLID)
    static Integer provSchoolId(GradeScoreAvgContract.View view) {
        return ((Fragment)view).getArguments().getInt(GradeScoreAvgContract.View.KEY_SCHOOLID,0);
    }

//    @FragmentScope
//    @Provides
//    @Named(GradeScoreAvgContract.View.KEY_HOMEWORK_ID)
//    static String provHomeworkId(GradeScoreAvgContract.View view) {
//        return ((Fragment)view).getArguments().getString(GradeScoreAvgContract.View.KEY_HOMEWORK_ID,"0");
//    }

    @FragmentScope
    @Provides
    @Named(GradeScoreAvgContract.View.KEY_SUBJID)
    static Integer provSubjId(GradeScoreAvgContract.View view) {
        return ((Fragment)view).getArguments().getInt(GradeScoreAvgContract.View.KEY_SUBJID,0);
    }
}