package com.example.df.zhiyun.comment.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.comment.mvp.contract.CommentListContract;
import com.example.df.zhiyun.comment.mvp.model.CommentListModel;
import com.example.df.zhiyun.mvp.ui.adapter.CommentItemAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.model.entity.CommentItem;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 10:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CommentListModule {

    @Binds
    abstract CommentListContract.Model bindCommentListModel(CommentListModel model);

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(CommentListContract.View view) {
        Context context = ((Fragment)view).getContext();
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,
                ArmsUtils.dip2px(context,1)
                , ContextCompat.getColor(context, R.color.bg_grey));
    }

//    @FragmentScope
//    @Provides
//    static RecyclerView.LayoutManager provideLayoutManager(CommentListContract.View view) {
//        Context context = ((Fragment)view).getContext();
//        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
//        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
//        return manager;
//    }

    @FragmentScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<CommentItem> list){
        return new CommentItemAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<CommentItem> provideList() {
        return new ArrayList<>();
    }
}