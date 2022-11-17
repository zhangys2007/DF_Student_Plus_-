package com.example.df.zhiyun.plan.di.module;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.plan.mvp.contract.PlanListContract;
import com.example.df.zhiyun.plan.mvp.model.PlanListModel;
import com.example.df.zhiyun.mvp.model.entity.Plan;
import com.example.df.zhiyun.mvp.ui.adapter.PlanAdapter;
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
public abstract class PlanListModule {

    @Binds
    abstract PlanListContract.Model bindHomeworkNewModel(PlanListModel model);

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(PlanListContract.View view) {
        return new RecycleViewDivider(view.getPageContext(),LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(view.getPageContext(), R.color.divider));
    }

//    @FragmentScope
//    @Provides
//    static RecyclerView.LayoutManager provideLayoutManager(PlanListContract.View view) {
//        RecyclerView.LayoutManager  manager = new LinearLayoutManager(view.getPageContext());
//        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
//        return manager;
//    }

    @FragmentScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<Plan> list){
        return new PlanAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<Plan> provideHomeworkList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    @Named("isClass")
    static Integer provideIsClass(PlanListContract.View view) {
        return ((Fragment)view).getArguments().getInt(PlanListContract.View.KEY_CLASS);
    }

    @FragmentScope
    @Provides
    @Named("isCloud")
    static Integer provideIsCloud(PlanListContract.View view) {
        return ((Fragment)view).getArguments().getInt(PlanListContract.View.KEY_CLOUD);
    }

    @FragmentScope
    @Provides
    @Named("parentId")
    static String provideParentId(PlanListContract.View view) {
        return ((Fragment)view).getArguments().getString(PlanListContract.View.KEY_PARENT_ID,"");
    }
}