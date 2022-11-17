package com.example.df.zhiyun.organize.di.module;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.model.entity.MemberItem;
import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.organize.mvp.contract.ClassMemberContract;
import com.example.df.zhiyun.organize.mvp.model.ClassMemberModel;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.organize.mvp.ui.activity.ClassMemberActivity;
import com.example.df.zhiyun.mvp.ui.adapter.ClassMemberAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 12:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ClassMemberModule {

    @Binds
    abstract ClassMemberContract.Model bindClassMemberModel(ClassMemberModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(ClassMemberContract.View view) {
        return new RecycleViewDivider(view.getActivity(), LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(view.getActivity(), R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(ClassMemberContract.View view) {
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(view.getActivity());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<MemberItem> list){
        return new ClassMemberAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<MemberItem> provideDatas() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static String provideClassId(ClassMemberContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), ClassMemberActivity.KEY);
//        return view.getActivity().getIntent().getStringExtra(ClassMemberActivity.KEY);
    }
}