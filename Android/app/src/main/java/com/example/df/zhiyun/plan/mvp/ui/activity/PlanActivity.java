package com.example.df.zhiyun.plan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.plan.di.component.DaggerPlanComponent;
import com.example.df.zhiyun.plan.mvp.ui.fragment.PlanListFragment;
import com.example.df.zhiyun.mvp.ui.widget.SegmentControlView;
import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.plan.mvp.contract.PlanContract;
import com.example.df.zhiyun.plan.mvp.presenter.PlanPresenter;

import com.example.df.zhiyun.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:我的教案
 * <p>
 * Created by MVPArmsTemplate on 09/03/2019 16:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PlanActivity extends BaseStatusActivity<PlanPresenter> implements PlanContract.View {
    @BindArray(R.array.array_plan_type)
    String[] typesTitle;
    @BindView(R.id.segmentView)
    SegmentControlView segmentcontrolview;
    @BindView(R.id.vp_homework)
    ViewPager viewPager;

    List<Fragment> fragmentList = new ArrayList<>();
    AdapterViewPager adapterViewPager;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPlanComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_plan; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        initViewPager();
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initViewPager();

    }

    void initViewPager(){
        fragmentList.clear();
        fragmentList.add(PlanListFragment.newInstance(Api.PLAN_TYPE_PLAN,0,""));
        fragmentList.add(PlanListFragment.newInstance(Api.PLAN_TYPE_PLAN,1,""));

        if (adapterViewPager == null) {
            adapterViewPager = new AdapterViewPager(getSupportFragmentManager(), fragmentList, typesTitle);
        }
        viewPager.setAdapter(adapterViewPager);
        viewPager.setOffscreenPageLimit(3);

        segmentcontrolview.setOnSegmentChangedListener(new SegmentControlView.OnSegmentChangedListener() {
            @Override
            public void onSegmentChanged(int newSelectedIndex) {
                if(viewPager != null){
                    viewPager.setCurrentItem(newSelectedIndex, false);
                }
            }
        });
        segmentcontrolview.setViewPager(viewPager);
        segmentcontrolview.setSelectedIndex(0);
        segmentcontrolview.setGradient(true);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
