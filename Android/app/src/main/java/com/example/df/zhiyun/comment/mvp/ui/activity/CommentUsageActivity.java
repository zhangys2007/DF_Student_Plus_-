package com.example.df.zhiyun.comment.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.comment.di.component.DaggerCommentUsageComponent;
import com.example.df.zhiyun.mvp.model.entity.Tag;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.mvp.ui.widget.PickViewHelper;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.comment.mvp.contract.CommentUsageContract;
import com.example.df.zhiyun.comment.mvp.presenter.CommentUsagePresenter;

import com.example.df.zhiyun.R;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.autosize.internal.CustomAdapt;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:讲评报告使用分析
 * <p>
 * Created by MVPArmsTemplate on 10/31/2019 11:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CommentUsageActivity extends BaseStatusActivity<CommentUsagePresenter> implements CommentUsageContract.View
, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, CustomAdapt {
    @BindView(R.id.ll_search_bar)
    LinearLayout llSearchbar;
    @BindView(R.id.fl_grade)
    FrameLayout flGrade;
    @BindView(R.id.fl_count)
    FrameLayout flCount;

    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.tv_count)
    TextView tvCount;

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;

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

    private String[] arrayTag;

    private OptionsPickerView pickerView;
    private List<String> op1;
    private List<List<String>> op2;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommentUsageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_comment_usage; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) { }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        flGrade.setOnClickListener(this);
        flCount.setOnClickListener(this);
        tvSearch.setOnClickListener(this);

        initRecyclerView();
        initRefreshLayout();
        mPresenter.findFilterData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_grade:
            case R.id.fl_count:
                if(mPresenter.isClass()){
                    showPickView1();
                }else{
                    showPickView2();
                }
                break;
            case R.id.tvSearch:
                mPresenter.changeSearch(etSearch.getEditableText().toString());
                mPresenter.requestData(true);
                break;
            default:
                break;
        }
    }

    /**
     * 带班级和统计的滚轮
     */
    private void showPickView2(){
        if(op1 == null || op2 == null ){
            return;
        }

        if(pickerView == null){
            pickerView = PickViewHelper.getOptionsPickerView2(this,onOptionsSelectListener,
                    null,"",arrayTag,op1,op2);
        }
        if(pickerView.isShowing()){
            pickerView.dismiss();
        }

        pickerView.show();
    }

    /**
     * 只有统计的滚轮
     */
    private void showPickView1(){
        if(op1 == null ){
            return;
        }

        if(pickerView == null){
            pickerView = PickViewHelper.getOptionPicker(this,op1,onOptionsSelectListener
            );
        }
        if(pickerView.isShowing()){
            pickerView.dismiss();
        }

        pickerView.show();
    }

    private OnOptionsSelectListener onOptionsSelectListener = new OnOptionsSelectListener(){
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            if(mPresenter.isClass()){
                initFilterBar1(options1);
                mPresenter.countTypeChange(options1);
            }else{
                initFilterBar2(options1,options2);
                mPresenter.filterItemChange(options1,options2);
            }
            mPresenter.requestData(true);
        }
    };

    @Override
    public void initPickViewData(List<Tag> tags,boolean isClass) {
        if(isClass){
            llSearchbar.setVisibility(View.INVISIBLE);
            flGrade.setVisibility(View.INVISIBLE);
        }

        op1 = new ArrayList<>();
        op2 = new ArrayList<>();
        if(tags == null){
            return;
        }

        List<String> countTypes = new ArrayList<>();
        countTypes.add("日统计");
        countTypes.add("周统计");
        countTypes.add("月统计");

        if(isClass){
            arrayTag = new String[]{"班级","统计"};
            op1 = countTypes;
            initFilterBar1(0);
        }else{
            arrayTag = new String[]{"年级","统计"};
            for(Tag tag: tags){
                op1.add(tag.getTitle());
                op2.add(countTypes);
            }
            initFilterBar2(0,0);
        }
    }



    private void initFilterBar1(int index1){
        if(op1 != null && op1.size() > index1){
            tvCount.setText(op1.get(index1));
        }
    }

    private void initFilterBar2(int index1,int index2){
        if(op1 != null && op1.size() > index1){
            tvGrade.setText(op1.get(index1));
        }

        if(op2 != null && op2.size() > index1 && op2.get(index1) != null && op2.get(index1).size() > index2){
            tvCount.setText(op2.get(index1).get(index2));
        }
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
    protected void onDestroy() {
        super.onDestroy();
        if(pickerView != null && pickerView.isShowing()){
            pickerView.dismiss();
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.requestData(true);
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
    public void onLoadMoreRequested() {
        mPresenter.requestData(false);
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

        mAdapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter.setEnableLoadMore(true);
//        mAdapter.setPreLoadNumber(2);   //预加载
//        mAdapter.setLoadMoreView(new ListLoadmoreView()); //自定义的loadmoreview
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }
}
