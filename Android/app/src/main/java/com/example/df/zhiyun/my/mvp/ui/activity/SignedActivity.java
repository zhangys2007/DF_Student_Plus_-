package com.example.df.zhiyun.my.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.my.di.component.DaggerSignedComponent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import com.example.df.zhiyun.my.mvp.contract.SignedContract;
import com.example.df.zhiyun.my.mvp.presenter.SignedPresenter;

import com.example.df.zhiyun.R;
import timber.log.Timber;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 我的签到
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 10:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SignedActivity extends BaseStatusActivity<SignedPresenter> implements SignedContract.View
            , View.OnClickListener{
    @BindView(R.id.toolbar_left_title)
    TextView tvLeftTitle;
    @BindView(R.id.tv_count_big)
    TextView tvCountBig;
    @BindView(R.id.tv_moon)
    TextView tvMoon;
    @BindView(R.id.tv_signed_count)
    TextView tvCount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fl_signed)
    FrameLayout flSigned;
    @BindView(R.id.swt_notifer)
    Switch swtNotice;

    @Inject
    BaseMultiItemQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;

    @Inject
    KProgressHUD progressHUD;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSignedComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_signed; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressHUD != null){
            progressHUD.dismiss();
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        initListener();
//        initRecyclerView();
//        Calendar calendar = Calendar.getInstance();
//
//        tvLeftTitle.setText(R.string.my);
//        tvMoon.setText(TimeUtils.getYM(calendar.getTimeInMillis()));
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initListener();
        initRecyclerView();
        Calendar calendar = Calendar.getInstance();

        tvLeftTitle.setText(R.string.my);
        tvMoon.setText(TimeUtils.getYM(calendar.getTimeInMillis()));
        mPresenter.getSignedList();
    }

    private void initListener(){
        flSigned.setOnClickListener(this);
        swtNotice.setOnCheckedChangeListener(noticeChangeListener);
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(layoutManager);
        mAdapter.setEnableLoadMore(false);
        recyclerView.setAdapter(mAdapter);
    }

    private CompoundButton.OnCheckedChangeListener noticeChangeListener = new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Timber.tag(TAG).d("notic change: " + b);
        }
    };

    @Override
    public void showLoading() {
        if(progressHUD!=null){
            if(progressHUD.isShowing()){
                progressHUD.dismiss();
            }
            progressHUD.show();
        }
    }

    @Override
    public void hideLoading() {
        if(progressHUD!=null){
            progressHUD.dismiss();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public Context getPageContext(){
        return this;
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fl_signed:
                mPresenter.signed();
                break;
            default:
                break;
        }
    }

    @Override
    public void updateSignedCount(int total, int totalMoon) {
        tvCount.setText(""+totalMoon);
        tvCountBig.setText(""+total);
    }
}
