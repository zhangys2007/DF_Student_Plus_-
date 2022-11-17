package com.example.df.zhiyun.di.module;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.ui.adapter.HomeworkOldAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.mvp.contract.HomeworkOldContract;
import com.example.df.zhiyun.mvp.model.HomeworkOldModel;
import com.example.df.zhiyun.mvp.model.entity.Homework;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 10:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HomeworkOldModule {

    @Binds
    abstract HomeworkOldContract.Model bindHomeworkOldModel(HomeworkOldModel model);

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(HomeworkOldContract.View view) {
        return new RecycleViewDivider(view.getPageContext(), LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(view.getPageContext(), R.color.divider));
    }

//    @FragmentScope
//    @Provides
//    static RecyclerView.LayoutManager provideLayoutManager(HomeworkOldContract.View view) {
//        RecyclerView.LayoutManager  manager = new LinearLayoutManager(view.getPageContext());
//        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
//        return manager;
//    }

    @FragmentScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<Homework> list){
        return new HomeworkOldAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<Homework> provideHomeworkList() {
        return new ArrayList<>();
    }

//    @FragmentScope
//    @Provides
//    static Integer provInitSubjectId(HomeworkNewContract.View view) {
//        return view.getFragment().getArguments().getInt(HomeworkOldFragment.KEY_SUBJ,0);
//    }
}