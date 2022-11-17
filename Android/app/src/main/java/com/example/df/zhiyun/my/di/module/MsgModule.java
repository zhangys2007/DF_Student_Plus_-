package com.example.df.zhiyun.my.di.module;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.my.mvp.contract.MsgContract;
import com.example.df.zhiyun.mvp.model.entity.Message;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.my.mvp.model.MsgModel;
import com.example.df.zhiyun.mvp.ui.adapter.MessageAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 13:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class MsgModule {

    @Binds
    abstract MsgContract.Model bindMsgModel(MsgModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(MsgContract.View view) {
        return new RecycleViewDivider(view.getActivity(), LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(view.getActivity(), R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(MsgContract.View view) {
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(view.getActivity());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<Message> list){
        return new MessageAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<Message> provideDatas() {
        return new ArrayList<>();
    }
}