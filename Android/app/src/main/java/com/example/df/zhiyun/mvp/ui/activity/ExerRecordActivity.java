package com.example.df.zhiyun.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.mvp.contract.ExerRecordContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.presenter.ExerRecordPresenter;
import com.example.df.zhiyun.mvp.ui.widget.ViewSubjDateRecyclerHelper;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import com.example.df.zhiyun.di.component.DaggerExerRecordComponent;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.ui.fragment.ExerListFragment;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 练习记录
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 16:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ExerRecordActivity extends BaseStatusActivity<ExerRecordPresenter> implements ExerRecordContract.View
        , ViewSubjDateRecyclerHelper.SubjDateInterface {

    @BindView(R.id.tv_record_num)
    TextView tvNum;

    //标题栏
    @BindView(R.id.toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_title)
    TextView tvToolbarLeft;
    @BindView(R.id.toolbar_right_title)
    TextView tvToolbarRight;

    ExerListFragment fragment;
    boolean isInited;  //是否加载过数据


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExerRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }



    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_exer_record; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        tvToolbarLeft.setText(R.string.home_work);
//        Drawable drawable = ContextCompat.getDrawable(this,R.mipmap.fold);
//        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
//        tvToolbarRight.setCompoundDrawables(null,null, drawable,null);
//        tvToolbarTitle.setText(R.string.newest_homework);
//
//        initFragment(savedInstanceState);
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        tvToolbarLeft.setText(R.string.home_work);
//        Drawable drawable = ContextCompat.getDrawable(this,R.mipmap.fold);
//        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
//        tvToolbarRight.setCompoundDrawables(null,null, drawable,null);
        tvToolbarTitle.setText(R.string.newest_homework);

        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            fragment = (ExerListFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, "Fragment");
        } else {
            fragment = ExerListFragment.newInstance(Api.SUBJECT_ALL);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isInited){
            fragment.getInitData();
            isInited= true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // 存储 Fragment 的状态。
        getSupportFragmentManager().putFragment(outState, "Fragment", fragment);
    }


    @Subscriber(tag = EventBusTags.UPDATE_EXER_NUMB)
    public void setExerNumb(String numb) {
        tvNum.setText(numb);
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

    @Override
    public Context getPageContext() {
        return this;
    }

    @Override
    public void onSubjectChange(String subjId, String subName) {
        tvToolbarTitle.setText(subName);
        fragment.onRefresh();
    }

    @Override
    public void onTimeChange(String start, String end) {
        fragment.onRefresh();
    }
}
