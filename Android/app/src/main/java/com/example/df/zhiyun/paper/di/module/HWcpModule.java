package com.example.df.zhiyun.paper.di.module;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.paper.mvp.contract.HWcpContract;
import com.example.df.zhiyun.paper.mvp.model.HWcpModel;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.mvp.model.adapterEntity.CpQuestionMultipleItem;
import com.example.df.zhiyun.paper.mvp.ui.adapter.HWcpAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/10/2019 13:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HWcpModule {

    @Binds
    abstract HWcpContract.Model bindHWcpModel(HWcpModel model);

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(HWcpContract.View view) {
        return new RecycleViewDivider(((Fragment)view).getContext(), LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(((Fragment)view).getContext(),10)
                , ContextCompat.getColor(((Fragment)view).getContext(), android.R.color.transparent));
    }

//    @FragmentScope
//    @Provides
//    static RecyclerView.LayoutManager provideLayoutManager(HWcpContract.View view) {
//        RecyclerView.LayoutManager  manager = new LinearLayoutManager(((Fragment)view).getContext());
//        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
//        return manager;
//    }

    @FragmentScope
    @Provides
    static BaseMultiItemQuickAdapter provideAdapter(List<CpQuestionMultipleItem> list){
        return new HWcpAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<CpQuestionMultipleItem> provideList() {
        return new ArrayList<>();
    }
}