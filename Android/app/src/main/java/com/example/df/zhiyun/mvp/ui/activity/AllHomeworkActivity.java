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
import com.example.df.zhiyun.mvp.contract.AllHomeworkContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.presenter.AllHomeworkPresenter;
import com.example.df.zhiyun.mvp.ui.widget.ViewSubjDateRecyclerHelper;

import butterknife.BindView;
import com.example.df.zhiyun.di.component.DaggerAllHomeworkComponent;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.ui.fragment.HomeworkNewFragment;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 11:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AllHomeworkActivity extends BaseStatusActivity<AllHomeworkPresenter> implements AllHomeworkContract.View
                    , ViewSubjDateRecyclerHelper.SubjDateInterface {
    //标题栏
    @BindView(R.id.toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_title)
    TextView tvToolbarLeft;
    @BindView(R.id.toolbar_right_title)
    TextView tvToolbarRight;


    HomeworkNewFragment homeworkNewFragment;
    boolean isInited;  //是否加载过数据

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAllHomeworkComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_all_homework; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initAll(Bundle savedInstanceState) {
        tvToolbarLeft.setText(R.string.home_work);
        tvToolbarTitle.setText(R.string.newest_homework);
//        Drawable drawable = ContextCompat.getDrawable(this,R.mipmap.fold);
//        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
//        tvToolbarRight.setCompoundDrawables(null,null, drawable,null);

        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            homeworkNewFragment = (HomeworkNewFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, "HomeworkNewFragment");
        } else {
            homeworkNewFragment = HomeworkNewFragment.newInstance(Api.SUBJECT_ALL);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content, homeworkNewFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isInited){
            homeworkNewFragment.getInitData();
            isInited= true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // 存储 Fragment 的状态。
        getSupportFragmentManager().putFragment(outState, "HomeworkNewFragment", homeworkNewFragment);
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
        homeworkNewFragment.onRefresh();
    }

    @Override
    public void onTimeChange(String start, String end) {
        homeworkNewFragment.onRefresh();
    }
}
