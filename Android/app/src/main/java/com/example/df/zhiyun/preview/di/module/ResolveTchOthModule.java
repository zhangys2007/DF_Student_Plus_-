package com.example.df.zhiyun.preview.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.common.mvp.model.entity.CommonExpandableItem;
import com.example.df.zhiyun.preview.mvp.contract.ResolveTchOthContract;
import com.example.df.zhiyun.preview.mvp.model.ResolveTchOthModel;
import com.example.df.zhiyun.preview.mvp.ui.adapter.OptionMemberAdapter;
import com.example.df.zhiyun.preview.mvp.ui.adapter.OthMemberAdapter;
import com.example.df.zhiyun.preview.mvp.ui.widget.SelMemberSpanLookup;
import com.jess.arms.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/29/2020 11:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ResolveTchOthModule {

    @Binds
    abstract ResolveTchOthContract.Model bindResolveTchOthModel(ResolveTchOthModel model);

    @Provides
    @FragmentScope
    static Boolean providePageType(ResolveTchOthContract.View view){
        Fragment fragment = (Fragment)view;
        return fragment.getArguments().getBoolean(
                ResolveTchOthContract.View.KEY_PAGE_STATIC ,false);
    }

    @Provides
    @FragmentScope
    static BaseQuickAdapter.SpanSizeLookup provideSpanSizeLookup(ResolveTchOthContract.View view){
        return new SelMemberSpanLookup();
    }

    @Provides
    @FragmentScope
    static RecyclerView.LayoutManager provideLayoutManagerStd(ResolveTchOthContract.View view) {
        Context context = ((Fragment)view).getContext();
        GridLayoutManager manager = new GridLayoutManager(context,5);
        return manager;
    }

    @Provides
    @FragmentScope
    static BaseQuickAdapter provideAdapter(){
        List<MultiItemEntity> list = new ArrayList<>();

        for(int i=0;i<2;i++){
            CommonExpandableItem mainItem = new CommonExpandableItem("",0);
            list.add(mainItem);

            for(int j=0;j<8;j++){
                CommonExpandableItem subItem = new CommonExpandableItem("",1);
                list.add(subItem);
            }
        }
        return new OthMemberAdapter(list);
    }
}