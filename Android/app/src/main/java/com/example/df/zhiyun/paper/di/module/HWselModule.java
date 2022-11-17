package com.example.df.zhiyun.paper.di.module;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.paper.mvp.contract.HWselContract;
import com.example.df.zhiyun.paper.mvp.model.HWselModel;
import com.example.df.zhiyun.paper.mvp.ui.adapter.HWselAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 15:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HWselModule {

    @Binds
    abstract HWselContract.Model bindHWselModel(HWselModel model);

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(HWselContract.View view) {
//        Bundle savedInstanceState = ((Fragment)view).getArguments();
//        int isEmbed = savedInstanceState.getInt("isEmbed",0);
        return new RecycleViewDivider(((Fragment)view).getContext(), LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(((Fragment)view).getContext(),10)
                , ContextCompat.getColor(((Fragment)view).getContext(), android.R.color.transparent));
    }

//    @FragmentScope
//    @Provides
//    static RecyclerView.LayoutManager provideLayoutManager(HWselContract.View view) {
//        RecyclerView.LayoutManager  manager = new LinearLayoutManager(((Fragment)view).getContext());
//        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
//        return manager;
//    }

    @FragmentScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<QuestionOption> list){
        return new HWselAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<QuestionOption> provideList() {
        return new ArrayList<>();
    }
}