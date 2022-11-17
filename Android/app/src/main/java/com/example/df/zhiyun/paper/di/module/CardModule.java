package com.example.df.zhiyun.paper.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.paper.mvp.contract.CardContract;
import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.paper.mvp.model.CardModel;
import com.example.df.zhiyun.mvp.model.adapterEntity.CardMultipleItem;
import com.example.df.zhiyun.paper.mvp.ui.adapter.CardAdapter;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2019 12:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CardModule {

    @Binds
    abstract CardContract.Model bindCardModel(CardModel model);

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(CardContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Provides
    @ActivityScope
    static RecyclerView.LayoutManager provideLayoutManager(CardContract.View view) {
        RecyclerView.LayoutManager  manager = new GridLayoutManager((Activity)view, 10);
        return manager;
    }


    @Provides
    @ActivityScope
    static BaseMultiItemQuickAdapter provideAdapter(){
        List<CardMultipleItem> list = new ArrayList<>();
        return new CardAdapter(list);
    }

    @Provides
    @Named(CardContract.View.KEY_TIME_REMAIN)
    @ActivityScope
    static Long provideTimeRemaind(CardContract.View view) {
        return ((Activity)view).getIntent().getLongExtra(CardContract.View.KEY_TIME_REMAIN,0);
    }

    @Provides
    @ActivityScope
    @Named(CardContract.View.KEY_SUBJECTID)
    static Integer provideSubjectId(CardContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(CardContract.View.KEY_SUBJECTID,0);
    }

    @Provides
    @ActivityScope
    @Named(CardContract.View.KEY_JOB_TYPE)
    static Integer provideJobType(CardContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(CardContract.View.KEY_JOB_TYPE,0);
    }

    @Provides
    @ActivityScope
    @Named(CardContract.View.KEY_STD_HW_ID)
    static String provideSTDHWId(CardContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CardContract.View.KEY_STD_HW_ID);
    }

    @Provides
    @ActivityScope
    @Named(CardContract.View.KEY_HW_ID)
    static String provideHWId(CardContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CardContract.View.KEY_HW_ID);
    }

    @Provides
    @ActivityScope
    @Named(CardContract.View.KEY_UUID)
    static String provideUUID(CardContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CardContract.View.KEY_UUID);
    }

    @Provides
    @ActivityScope
    @Named(CardContract.View.KEY_SCHOOLID)
    static String provideSchoolID(CardContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CardContract.View.KEY_SCHOOLID);
    }
}