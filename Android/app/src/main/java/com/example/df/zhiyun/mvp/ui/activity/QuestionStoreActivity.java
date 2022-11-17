package com.example.df.zhiyun.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.contract.QuestionStoreContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.QuestionMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.Subject;
import com.example.df.zhiyun.mvp.presenter.QuestionStorePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import com.example.df.zhiyun.di.component.DaggerQuestionStoreComponent;

import com.example.df.zhiyun.R;
import me.kareluo.ui.OptionMenu;
import me.kareluo.ui.OptionMenuView;
import me.kareluo.ui.PopupMenuView;
import me.kareluo.ui.PopupView;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 我的收藏 各种题
 * <p>
 * Created by MVPArmsTemplate on 07/26/2019 15:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class QuestionStoreActivity extends BaseStatusActivity<QuestionStorePresenter> implements QuestionStoreContract.View
    ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener,View.OnClickListener
        ,BaseQuickAdapter.OnItemClickListener{
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    BaseMultiItemQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    @BindView(R.id.toolbar_right_title)
    TextView tvToolbarRight;
    PopupMenuView popupMenuView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerQuestionStoreComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_question_store; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        initRecyclerView();
//        tvToolbarRight.setText(getString(R.string.all));
//        Drawable arrow = ContextCompat.getDrawable(this,R.mipmap.arrow_down);
//        arrow.setBounds(0,0,arrow.getIntrinsicWidth(),arrow.getIntrinsicHeight());
//        tvToolbarRight.setCompoundDrawables(null,null, arrow,null);
//        tvToolbarRight.setOnClickListener(this);
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        tvToolbarRight.setText(getString(R.string.all));
        Drawable arrow = ContextCompat.getDrawable(this,R.mipmap.arrow_down);
        arrow.setBounds(0,0,arrow.getIntrinsicWidth(),arrow.getIntrinsicHeight());
        tvToolbarRight.setCompoundDrawables(null,null, arrow,null);
        tvToolbarRight.setOnClickListener(this);
        mPresenter.getSubjectList();
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

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar_right_title:
                showSubjectPopupMenu();
                break;
        }
    }

    @Override
    public void onGetSubjectData(List<Subject> data) {
        if(data != null && data.size() > 0){
            tvToolbarRight.setText(data.get(0).getName());
            mPresenter.clickPopupMenu(0);
        }
    }

    private void showSubjectPopupMenu(){
        if(popupMenuView == null){
            List<OptionMenu> menus = new ArrayList<>();
            popupMenuView = new PopupMenuView(this);
            List<Subject> subjects = mPresenter.getSubjectItems();
            if(subjects != null){
                for(Subject subject: subjects){
                    menus.add(new OptionMenu(subject.getName()));
                }
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
            mPresenter.clickPopupMenu(position);
            return true;
        }
    };

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
    public Context getPageContext() {
        return this;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        QuestionMultipleItem item = (QuestionMultipleItem)adapter.getData().get(position);
        EQDetailActivity.startErrorDetailPage(this,item.getData().getQuestionId(),
                item.getData().getSubjectId());
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requesData(false);
    }


    @Override
    public void onRefresh() {
        mPresenter.requesData(true);
    }

    @Override
    public void setCollectionCount(int count) {
        tvCount.setText(""+count);
    }
}
