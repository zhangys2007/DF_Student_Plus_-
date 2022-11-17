package com.example.df.zhiyun.preview.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.common.mvp.model.entity.CommonExpandableItem;
import com.example.df.zhiyun.preview.mvp.ui.adapter.OptionMemberAdapter;
import com.example.df.zhiyun.preview.mvp.ui.widget.SelMemberSpanLookup;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.preview.mvp.contract.ResolveTchSelFragmentContract;
import com.example.df.zhiyun.preview.mvp.model.ResolveTchSelFragmentModel;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/28/2020 16:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ResolveTchSelFragmentModule {

    @Binds
    abstract ResolveTchSelFragmentContract.Model bindResolveTchSelFragmentModel(ResolveTchSelFragmentModel model);

    @Provides
    @FragmentScope
    static Boolean providePageType(ResolveTchSelFragmentContract.View view){
        Fragment fragment = (Fragment)view;
        return fragment.getArguments().getBoolean(
                ResolveTchSelFragmentContract.View.KEY_PAGE_STATIC ,false);
    }

    @Provides
    @FragmentScope
    static BaseQuickAdapter.SpanSizeLookup provideSpanSizeLookup(ResolveTchSelFragmentContract.View view){
        return new SelMemberSpanLookup();
    }

    @Provides
    @FragmentScope
    static RecyclerView.LayoutManager provideLayoutManagerStd(ResolveTchSelFragmentContract.View view) {
        Context context = ((Fragment)view).getContext();
        GridLayoutManager manager = new GridLayoutManager(context,5);
        return manager;
    }

    @Provides
    @FragmentScope
    static BaseQuickAdapter provideAdapter(){
        List<MultiItemEntity> list = new ArrayList<>();

        for(int i=0;i<4;i++){
            CommonExpandableItem mainItem = new CommonExpandableItem("",0);
            list.add(mainItem);

            List<CommonExpandableItem> subList = new ArrayList<>();
            mainItem.setSubItems(subList);
            for(int j=0;j<8;j++){
                CommonExpandableItem subItem = new CommonExpandableItem("",1);
                subList.add(subItem);
            }
        }
        return new OptionMemberAdapter(list);
    }
}