package com.example.df.zhiyun.educate.di.module;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.common.mvp.model.entity.CommonExpandableItem;
import com.example.df.zhiyun.educate.mvp.contract.HwPutContract;
import com.example.df.zhiyun.educate.mvp.model.HwPutModel;
import com.example.df.zhiyun.educate.mvp.ui.adapter.HwPutAdapter;
import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/08/2020 18:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HwPutModule {

    @Binds
    abstract HwPutContract.Model bindHwPutModel(HwPutModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(HwPutContract.View view) {
        Context activity = (Context)view;
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(activity);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseMultiItemQuickAdapter provideAdapter(List<MultiItemEntity> list){
        return new HwPutAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<MultiItemEntity> provideList() {
        List<MultiItemEntity> list = new ArrayList<>();
        for(int i=0;i<3;i++){
            CommonExpandableItem l0 = new CommonExpandableItem(null,0);
            list.add(l0);

            for(int j=0;j<3;j++){
                CommonExpandableItem l1 = new CommonExpandableItem(null,1);
                l0.addSubItem(l1);

                for(int k=0;k<3;k++){
                    CommonExpandableItem l2 = new CommonExpandableItem(null,2);
                    l1.addSubItem(l2);
                }
            }
        }
        return list;
    }

}