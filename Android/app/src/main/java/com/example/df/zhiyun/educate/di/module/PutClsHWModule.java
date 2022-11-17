package com.example.df.zhiyun.educate.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.common.mvp.model.entity.CommonExpandableItem;
import com.example.df.zhiyun.educate.mvp.contract.PutClsHWContract;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.educate.mvp.ui.adapter.PutHWClassAdapter;
import com.example.df.zhiyun.mvp.model.entity.PutStudent;
import com.example.df.zhiyun.mvp.ui.widget.PutHWRecycleViewGridDivider;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.educate.mvp.model.PutClsHWModel;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 15:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PutClsHWModule {

    @Binds
    abstract PutClsHWContract.Model bindPutHWModel(PutClsHWModel model);

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(PutClsHWContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(PutClsHWContract.View view) {
        Context context = (Context)view;
        int divpix = ArmsUtils.dip2px(context,5);
        int c = ContextCompat.getColor(context, R.color.white);
        return new PutHWRecycleViewGridDivider(divpix,c,4);
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(PutClsHWContract.View view) {
        Context context = (Context)view;
        RecyclerView.LayoutManager  manager = new GridLayoutManager(context, 3);
        return manager;
    }

    @ActivityScope
    @Provides
    static IView provideBaseIView(PutClsHWContract.View view){
        return (IView)view;
    }

    @ActivityScope
    @Provides
    static BaseMultiItemQuickAdapter provideAdapter(List<MultiItemEntity> list){
        return new PutHWClassAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<MultiItemEntity> provideDatas() {
        List<MultiItemEntity> list = new ArrayList<>();
        for(int i=0;i<3;i++){
            BelongClass data0 = new BelongClass();
            data0.setClassId(Integer.toString(i));
            data0.setClassName("班级"+i);
            CommonExpandableItem l0 = new CommonExpandableItem(data0,0);
            list.add(l0);
            for(int j=0;j<3;j++){
                PutStudent data1 = new PutStudent();
                data1.setId(Integer.toString(i*10+j));
                data1.setRealName(String.format("学生%d%d",i,j));
                CommonExpandableItem l1 = new CommonExpandableItem(data1,1);
                l0.addSubItem(l1);
            }
        }
        return list;
    }

    @ActivityScope
    @Provides
    @Named(PutClsHWContract.View.KEY_PAPER_NAME)
    static String providePaperName(PutClsHWContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), PutClsHWContract.View.KEY_PAPER_NAME);
//        return ((Activity)view).getIntent().getStringExtra(PutClsHWContract.View.KEY_PAPER_NAME);
    }

    @ActivityScope
    @Provides
    @Named(PutClsHWContract.View.KEY_PAPER_ID)
    static String providePaperId(PutClsHWContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), PutClsHWContract.View.KEY_PAPER_ID);
//        return ((Activity)view).getIntent().getStringExtra(PutClsHWContract.View.KEY_PAPER_ID);
    }

    @ActivityScope
    @Provides
    @Named(PutClsHWContract.View.KEY_SYSTEM_ID)
    static String provideSystemId(PutClsHWContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), PutClsHWContract.View.KEY_SYSTEM_ID);
//        return ((Activity)view).getIntent().getStringExtra(PutClsHWContract.View.KEY_SYSTEM_ID);
    }

    @ActivityScope
    @Provides
    @Named(PutClsHWContract.View.KEY_LINK_ID)
    static String provideLinkId(PutClsHWContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), PutClsHWContract.View.KEY_LINK_ID);
//        return ((Activity)view).getIntent().getStringExtra(PutClsHWContract.View.KEY_LINK_ID);
    }

    @ActivityScope
    @Provides
    @Named(PutClsHWContract.View.KEY_TYPE)
    static Integer provideType(PutClsHWContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(PutClsHWContract.View.KEY_TYPE,0);
    }
}