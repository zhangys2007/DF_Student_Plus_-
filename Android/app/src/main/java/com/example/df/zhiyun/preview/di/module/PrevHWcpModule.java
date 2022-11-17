package com.example.df.zhiyun.preview.di.module;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.preview.mvp.contract.PrevHWcpContract;
import com.example.df.zhiyun.preview.mvp.model.PrevHWcpModel;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.preview.mvp.ui.adapter.PrevHWAnalyAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


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
public abstract class PrevHWcpModule {

    @Binds
    abstract PrevHWcpContract.Model bindHWcpModel(PrevHWcpModel model);

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(PrevHWcpContract.View view) {
        return new RecycleViewDivider(((Fragment)view).getContext(), LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(((Fragment)view).getContext(),10)
                , ContextCompat.getColor(((Fragment)view).getContext(), android.R.color.transparent));
    }

//    @FragmentScope
//    @Provides
//    static RecyclerView.LayoutManager provideLayoutManager(PrevHWcpContract.View view) {
//        RecyclerView.LayoutManager  manager = new LinearLayoutManager(((Fragment)view).getContext());
//        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
//        return manager;
//    }

    @FragmentScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<Question> list){
        return new PrevHWAnalyAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<Question> provideList() {
        return new ArrayList<>();
    }
}