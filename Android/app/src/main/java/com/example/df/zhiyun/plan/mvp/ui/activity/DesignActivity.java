package com.example.df.zhiyun.plan.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.plan.di.component.DaggerDesignComponent;
import com.example.df.zhiyun.plan.mvp.ui.fragment.PlanListFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.plan.mvp.contract.DesignContract;
import com.example.df.zhiyun.plan.mvp.presenter.DesignPresenter;

import com.example.df.zhiyun.R;


import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/03/2019 22:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class DesignActivity extends BaseStatusActivity<DesignPresenter> implements DesignContract.View {
    @BindView(R.id.fl_content_plan)
    FrameLayout frameLayout;

    @Inject
    @Named("isClass")
    Integer mIsClass;
    @Inject
    @Named("isCloud")
    Integer mIsCloud;
    @Inject
    @Named("parentId")
    String mParentId;
    @Inject
    @Named("title")
    String mTitle;

    PlanListFragment fragment;
    boolean isInited;  //是否加载过数据

    public static void launchActivity(Context context,String title, int isClass, int isCloud, String parentId) {
        Intent intent = new Intent(context,DesignActivity.class);
        intent.putExtra(DesignContract.View.KEY_TITLE,title);
        intent.putExtra(DesignContract.View.KEY_CLASS,isClass);
        intent.putExtra(DesignContract.View.KEY_CLOUD,isCloud);
        intent.putExtra(DesignContract.View.KEY_PARENT_ID,parentId);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDesignComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!isInited){
            fragment.onRefresh();
            isInited= true;
        }
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_design; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        setTitle(mTitle);
//        initFragment(savedInstanceState);
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        setTitle(mTitle);
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            fragment = (PlanListFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, "PlanListFragment");
        } else {
            fragment = PlanListFragment.newInstance(mIsClass,mIsCloud,mParentId);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content_plan, fragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // 存储 Fragment 的状态。
        getSupportFragmentManager().putFragment(outState, "PlanListFragment", fragment);
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
