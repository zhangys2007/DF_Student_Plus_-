package com.example.df.zhiyun.mvp.ui.activity;

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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.educate.mvp.ui.activity.PutClsHWActivity;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.SelPaperItem;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.di.component.DaggerSelPaperListComponent;
import com.example.df.zhiyun.mvp.contract.SelPaperListContract;
import com.example.df.zhiyun.mvp.presenter.SelPaperListPresenter;

import com.example.df.zhiyun.R;


import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.kareluo.ui.OptionMenu;
import me.kareluo.ui.OptionMenuView;
import me.kareluo.ui.PopupMenuView;
import me.kareluo.ui.PopupView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 精选试卷列表
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SelPaperListActivity extends BaseStatusActivity<SelPaperListPresenter> implements SelPaperListContract.View
    , SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener , View.OnClickListener{
    @BindView(R.id.toolbar_right_title)
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

    PopupMenuView popupMenuView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSelPaperListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.base_activity_refresh_recycler; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        initRecyclerView();
//        initRefreshLayout();
//
//        Drawable arrow = ContextCompat.getDrawable(this,R.mipmap.arrow_down);
//        arrow.setBounds(0,0,arrow.getIntrinsicWidth(),arrow.getIntrinsicHeight());
//        tvSubject.setCompoundDrawables(null,null,arrow,null);
//        tvSubject.setText(getString(R.string.chinese));
//        tvSubject.setOnClickListener(this);
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        initRefreshLayout();

        Drawable arrow = ContextCompat.getDrawable(this,R.mipmap.arrow_down);
        arrow.setBounds(0,0,arrow.getIntrinsicWidth(),arrow.getIntrinsicHeight());
        tvSubject.setCompoundDrawables(null,null,arrow,null);
        tvSubject.setText(getString(R.string.chinese));
        tvSubject.setOnClickListener(this);
        mPresenter.requestData(true);
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
        int padding = ArmsUtils.dip2px(this,10);
        recyclerView.setPadding(padding,0,padding,0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        mAdapter.setOnItemChildClickListener(hwItemChildClickListener);

        mAdapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter.setEnableLoadMore(true);
//        mAdapter.setPreLoadNumber(2);   //预加载
//        mAdapter.setLoadMoreView(new ListLoadmoreView()); //自定义的loadmoreview
        recyclerView.setAdapter(mAdapter);
    }

    private BaseQuickAdapter.OnItemChildClickListener hwItemChildClickListener = new BaseQuickAdapter.OnItemChildClickListener(){
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            SelPaperItem item = (SelPaperItem)adapter.getData().get(position);
            switch (view.getId()){
                case R.id.ib_sel_preview:
                    mPresenter.clickPreview(item);
                    break;
                case R.id.ib_sel_arrange:
                    PutClsHWActivity.startPutHWPage(view.getContext(),item.getPaperName(),
                            item.getPaperId(), Api.PUT_TYPE_SELECTED,item.getTeachSystemId(),item.getLinkId());
                    break;
                case R.id.ib_sel_cancel:
                    CancelHWActivity.startCancelHWPage(view.getContext(),item.getPaperName(),
                            item.getPaperId(), Api.PUT_TYPE_SELECTED,item.getTeachSystemId(),item.getLinkId());
                    break;
                case R.id.ib_sel_detail:
                    PutHWDetailActivity.startPutHWDetailPage(view.getContext(),"",
                            item.getPaperId(), Api.PUT_TYPE_SELECTED,item.getTeachSystemId(),item.getLinkId(),"");
                    break;
            }
        }
    };

    @Subscriber(tag = EventBusTags.UPDATE_PUT_REMOVE_HW)
    private void updateList(Integer value){
        mPresenter.requestData(true);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestData(false);
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
    public void onClick(View v) {
        showSubjectPopupMenu();
    }

    private void showSubjectPopupMenu(){
        if(popupMenuView == null){
            List<OptionMenu> menus = new ArrayList<>();
            popupMenuView = new PopupMenuView(this);

            menus.add(new OptionMenu(getString(R.string.chinese)));
            menus.add(new OptionMenu(getString(R.string.math)));
            menus.add(new OptionMenu(getString(R.string.english)));

            popupMenuView.setMenuItems(menus);
            popupMenuView.setSites(PopupView.SITE_BOTTOM);
            popupMenuView.setOrientation(LinearLayout.VERTICAL);
            popupMenuView.setOnMenuClickListener(popupMenuClickListener);
        }
        popupMenuView.show(tvSubject);
    }

    private OptionMenuView.OnOptionMenuClickListener popupMenuClickListener = new OptionMenuView.OnOptionMenuClickListener(){

        @Override
        public boolean onOptionMenuClick(int position, OptionMenu menu) {
            tvSubject.setText(menu.getTitle());
            mPresenter.changeSubject(position);
            onRefresh();
            return true;
        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(popupMenuView != null &&  popupMenuView.isShowing()){
            popupMenuView.dismiss();
            popupMenuView = null;
        }
    }
}
