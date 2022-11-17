package com.example.df.zhiyun.paper.di.module;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.paper.mvp.contract.HWcomContract;
import com.example.df.zhiyun.paper.mvp.model.HWcomModel;
import com.example.df.zhiyun.paper.mvp.ui.adapter.SquareImageAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description: 作文
 * <p>
 * Created by MVPArmsTemplate on 08/16/2019 11:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HWcomModule {

    @Binds
    abstract HWcomContract.Model bindHWcomModel(HWcomModel model);

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(HWcomContract.View view) {
        return new RecycleViewDivider(((Fragment)view).getContext(), LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(((Fragment)view).getContext(),10)
                , ContextCompat.getColor(((Fragment)view).getContext(), android.R.color.transparent));
    }

//    @FragmentScope
//    @Provides
//    static RecyclerView.LayoutManager provideLayoutManager(HWcomContract.View view) {
//        RecyclerView.LayoutManager  manager = new GridLayoutManager(((Fragment)view).getContext(), 3);
//        return manager;
//    }

    @FragmentScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<String> list){
        return new SquareImageAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<String> provideList() {
        return new ArrayList<>();
    }
}