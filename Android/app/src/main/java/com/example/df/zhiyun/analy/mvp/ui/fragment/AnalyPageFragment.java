package com.example.df.zhiyun.analy.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.mvp.ui.activity.ClsHwReportActivity;
import com.example.df.zhiyun.analy.mvp.ui.activity.LevelReportActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.analy.di.component.DaggerAnalyPageComponent;
import com.example.df.zhiyun.analy.mvp.contract.AnalyPageContract;
import com.example.df.zhiyun.analy.mvp.presenter.AnalyPagePresenter;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 首页上的分析
 * <p>
 * Created by MVPArmsTemplate on 04/23/2020 15:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AnalyPageFragment extends BaseFragment<AnalyPagePresenter> implements AnalyPageContract.View
    ,View.OnClickListener{
    @BindView(R.id.convenientBanner)
    ImageView ivLevelReport;

    @BindView(R.id.tv_part_hw)
    TextView tvPartHW;
    @BindView(R.id.tv_part_paper)
    TextView tvPartPaper;

    @BindView(R.id.recyclerView_level)
    RecyclerView recyclerViewLevel;
    @BindView(R.id.tv_level_more)
    TextView tvLevelMore;

    @BindView(R.id.recyclerView_rate)
    RecyclerView recyclerViewRate;
    @BindView(R.id.tv_rate_more)
    TextView tvRateMore;

    @Inject
    RecyclerView.ItemDecoration mItemDecoration;

    @Inject
    @Named(AnalyPageContract.View.KEY_RATE)
    BaseQuickAdapter mAdapterRate;
    @Inject
    @Named(AnalyPageContract.View.KEY_RATE)
    RecyclerView.LayoutManager layoutManagerRate;

    @Inject
    @Named(AnalyPageContract.View.KEY_LEVEL)
    BaseQuickAdapter mAdapterLevel;
    @Inject
    @Named(AnalyPageContract.View.KEY_LEVEL)
    RecyclerView.LayoutManager layoutManagerLevel;

    public static AnalyPageFragment newInstance() {
        AnalyPageFragment fragment = new AnalyPageFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAnalyPageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_analy_page, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initLayoutManager();
        tvPartHW.setSelected(true);
        tvPartHW.setOnClickListener(this);
        tvPartPaper.setOnClickListener(this);
        ivLevelReport.setOnClickListener(this);
    }

    private void initLayoutManager(){
        recyclerViewLevel.addItemDecoration(mItemDecoration);
        recyclerViewLevel.setLayoutManager(layoutManagerLevel);
        mAdapterLevel.setOnItemChildClickListener(levelChildClickListener);
        mAdapterLevel.setEnableLoadMore(false);
        recyclerViewLevel.setAdapter(mAdapterLevel);

//        recyclerViewTodo.addItemDecoration(mItemDecoration);
        recyclerViewRate.setLayoutManager(layoutManagerRate);
//        mAdapter.setOnItemClickListener(hwItemClickListener);
        mAdapterRate.setEnableLoadMore(false);
        recyclerViewRate.setAdapter(mAdapterRate);
    }

    private BaseQuickAdapter.OnItemChildClickListener levelChildClickListener =
            new BaseQuickAdapter.OnItemChildClickListener(){
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    ArmsUtils.startActivity(ClsHwReportActivity.class);
                }
            };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_part_hw:
                switchPartButton(true);
                break;
            case R.id.tv_part_paper:
                switchPartButton(false);
                break;
            case R.id.convenientBanner:
                ArmsUtils.startActivity(LevelReportActivity.class);
                break;
        }
    }

    /**
     * 切换作业和试卷按钮的
     * @param hwSel
     */
    private void switchPartButton(boolean hwSel){
        tvPartHW.setSelected(hwSel);
        tvPartPaper.setSelected(!hwSel);
    }

    @Override
    public void setData(@Nullable Object data) {

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

    }
}
