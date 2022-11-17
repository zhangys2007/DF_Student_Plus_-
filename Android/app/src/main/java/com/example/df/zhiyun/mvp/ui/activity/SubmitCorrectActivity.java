package com.example.df.zhiyun.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.utils.CompoundDrawableUtil;
import com.example.df.zhiyun.mvp.contract.SubmitCorrectContract;
import com.example.df.zhiyun.mvp.ui.widget.PickViewHelper;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.di.component.DaggerSubmitCorrectComponent;
import com.example.df.zhiyun.mvp.presenter.SubmitCorrectPresenter;

import com.example.df.zhiyun.R;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import me.jessyan.autosize.internal.CustomAdapt;
import me.kareluo.ui.OptionMenu;
import me.kareluo.ui.OptionMenuView;
import me.kareluo.ui.PopupMenuView;
import me.kareluo.ui.PopupView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:作业提交及批改
 * <p>
 * Created by MVPArmsTemplate on 10/29/2019 14:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SubmitCorrectActivity extends BaseStatusActivity<SubmitCorrectPresenter> implements SubmitCorrectContract.View
        , View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, CustomAdapt {
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

    @BindArray(R.array.count_type)
    String[] arrCountType;

    @BindView(R.id.toolbar_right_title)
    TextView tvToolbarRight;
    PopupMenuView popupMenuView;

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
        DaggerSubmitCorrectComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_submit_correct; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) { }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        flGrade.setOnClickListener(this);
        flClass.setOnClickListener(this);
        flSubject.setOnClickListener(this);
        tvToolbarRight.setOnClickListener(this);
        tvToolbarRight.setText(arrCountType[mPresenter.getCountTypeIndex()]);
        CompoundDrawableUtil.setCompoundDrawable(this,tvToolbarRight,0,0,R.mipmap.arrow_down,0);

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
            case R.id.toolbar_right_title:
                showTypePopupMenu();
                break;
            default:
                break;
        }
    }

    private void showTypePopupMenu(){
        if(popupMenuView == null){
            List<OptionMenu> menus = new ArrayList<>();
            popupMenuView = new PopupMenuView(this);

            for(int i=0;i<arrCountType.length;i++){
                menus.add(new OptionMenu(arrCountType[i]));
            }

            popupMenuView.setMenuItems(menus);
            popupMenuView.setSites(PopupView.SITE_BOTTOM);
            popupMenuView.setOrientation(LinearLayout.VERTICAL);
            popupMenuView.setOnMenuClickListener(popupMenuClickListener);
        }
        popupMenuView.show(tvToolbarRight);
    }

    private OptionMenuView.OnOptionMenuClickListener popupMenuClickListener = new OptionMenuView.OnOptionMenuClickListener(){

        @Override
        public boolean onOptionMenuClick(int position, OptionMenu menu) {
            tvToolbarRight.setText(menu.getTitle());
            mPresenter.changeCountType(position);
            mPresenter.requestData(true);
            return true;
        }
    };

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
            mPresenter.requestData(true);
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
