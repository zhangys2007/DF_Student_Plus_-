package com.example.df.zhiyun.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.mvp.ui.activity.GrowthTraceActivity;
import com.example.df.zhiyun.di.component.DaggerEventsHWAnalyListComponent;
import com.example.df.zhiyun.mvp.contract.EventsHWAnalyListContract;
import com.example.df.zhiyun.mvp.model.entity.GrowthTraceItem;
import com.example.df.zhiyun.mvp.presenter.EventsHWAnalyListPresenter;
import com.example.df.zhiyun.mvp.ui.widget.PickViewHelper;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 历次作业分析列表
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 10:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class EventsHWAnalyListActivity extends BaseStatusActivity<EventsHWAnalyListPresenter> implements EventsHWAnalyListContract.View
    , View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener
        ,BaseQuickAdapter.OnItemClickListener{
    @BindView(R.id.fl_grade)
    FrameLayout flGrade;
    @BindView(R.id.fl_class)
    FrameLayout flClass;
    @BindView(R.id.fl_subject)
    FrameLayout flSubject;

    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.tv_subject)
    TextView tvSubject;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    BaseQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    private String[] tags = new String[]{"年级","班级","学科"};
    private OptionsPickerView pickerView;
    private List<String> op1;
    private List<List<String>> op2;
    private List<List<List<String>>> op3;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEventsHWAnalyListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_growth_trace_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) { }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        flGrade.setOnClickListener(this);
        flClass.setOnClickListener(this);
        flSubject.setOnClickListener(this);

        initRecyclerView();
        initRefreshLayout();
        mPresenter.findFilterData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_grade:
            case R.id.fl_class:
            case R.id.fl_subject:
                showPickView();
                break;
            default:
                break;
        }
    }

    private void showPickView(){
        if(op1 == null || op2 == null || op3 == null){
            return;
        }

        if(pickerView == null){
            pickerView = PickViewHelper.getOptionsPickerView3(this,onOptionsSelectListener,
                    null,"",tags,op1,op2,op3);
        }
        if(pickerView.isShowing()){
            pickerView.dismiss();
        }

        pickerView.show();
    }

    private OnOptionsSelectListener onOptionsSelectListener = new OnOptionsSelectListener(){
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            initFilterBar(options1,options2,options3);
            mPresenter.filterItemChange(options1,options2,options3);
            onRefresh();
        }
    };

    @Override
    public void initPickViewData(List<String> op1, List<List<String>> op2, List<List<List<String>>> op3) {
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        initFilterBar(0,0,0);
    }

    private void initFilterBar(int index1,int index2,int index3){
        if(op1 != null && op1.size() > index1){
            tvGrade.setText(op1.get(index1));
        }

        if(op2 != null && op2.size() > index1 && op2.get(index1) != null && op2.get(index1).size() > index2){
            tvClass.setText(op2.get(index1).get(index2));
        }

        if(op3 != null && op3.size() > index1 && op3.get(index1) != null && op3.get(index1).size() > index2
            && op3.get(index1).get(index2) != null && op3.get(index1).get(index2).size() > index3){
            tvSubject.setText(op3.get(index1).get(index2).get(index3));
        }
    }

    private void initRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        mAdapter.setOnItemClickListener(this);

        mAdapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter.setEnableLoadMore(true);
//        mAdapter.setPreLoadNumber(2);   //预加载
//        mAdapter.setLoadMoreView(new ListLoadmoreView()); //自定义的loadmoreview
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
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
    public void onRefresh() {
        if(mPresenter.isFilterDataReady()){
            mPresenter.requestData(true);
        }else{
            mPresenter.findFilterData();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        GrowthTraceItem data = (GrowthTraceItem)adapter.getData().get(position);
        if(data != null && !TextUtils.isEmpty(mPresenter.getSubjectID()) && !TextUtils.isEmpty(tvSubject.getText().toString())){
            GrowthTraceActivity.launchActivity(this,data,mPresenter.getSubjectID(),tvSubject.getText().toString());
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestData(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pickerView != null && pickerView.isShowing()){
            pickerView.dismiss();
        }
    }
}
