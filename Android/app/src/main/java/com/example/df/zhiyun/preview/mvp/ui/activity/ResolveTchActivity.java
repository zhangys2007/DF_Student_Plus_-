package com.example.df.zhiyun.preview.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;
import com.example.df.zhiyun.preview.mvp.ui.adapter.PreviewHomeworkAdapter;
import com.example.df.zhiyun.preview.mvp.ui.adapter.ResolveTchAdapter;
import com.example.df.zhiyun.preview.mvp.ui.adapter.ResolveTchIndexAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.preview.di.component.DaggerResolveTchComponent;
import com.example.df.zhiyun.preview.mvp.contract.ResolveTchContract;
import com.example.df.zhiyun.preview.mvp.presenter.ResolveTchPresenter;

import com.example.df.zhiyun.R;
import com.kaopiz.kprogresshud.KProgressHUD;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 教师端的题目分析
 * <p>
 * Created by MVPArmsTemplate on 04/27/2020 18:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ResolveTchActivity extends BaseActivity<ResolveTchPresenter> implements
        ResolveTchContract.View , View.OnClickListener{
    @BindView(R.id.recyclerView_index)
    RecyclerView recyclerViewIndex;
    @BindView(R.id.vp_resolve)
    ViewPager vpContainer;
    @BindView(R.id.ibtn_expand)
    ImageButton ibtnMore;

    @Inject
    KProgressHUD progressHUD;

    @Inject
    RecyclerView.ItemDecoration mItemDecoration;
    @Inject
    BaseQuickAdapter mAdapterIndex;
    @Inject
    RecyclerView.LayoutManager layoutManagerIndex;

    ResolveTchAdapter mPageAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerResolveTchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_resolve_tch; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ibtnMore.setOnClickListener(this);

        recyclerViewIndex.addItemDecoration(mItemDecoration);
        recyclerViewIndex.setLayoutManager(layoutManagerIndex);
        recyclerViewIndex.setAdapter(mAdapterIndex);
        ((ResolveTchIndexAdapter)mAdapterIndex).setSelIndex(0);
        recyclerViewIndex.addOnScrollListener(scrollListenerIndex);
        mAdapterIndex.setOnItemClickListener(itemClickListenerIndex);


        List<Question> list = new ArrayList<>();
        for(int i=0;i<20;i++){
            list.add(new Question());
        }

        if (mPageAdapter == null) {
            mPageAdapter = new ResolveTchAdapter(getSupportFragmentManager(),list);
        }
        vpContainer.setAdapter(mPageAdapter);
        vpContainer.setOffscreenPageLimit(1);
        vpContainer.addOnPageChangeListener(vpListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtn_expand:
                scrollNextSection();
                break;
        }
    }

    /**
     * 题目序号滚动到下一部分
     */
    private void scrollNextSection(){
        LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerViewIndex.getLayoutManager();
        int index = layoutManager.findLastVisibleItemPosition();
        if((index+8) <= mAdapterIndex.getData().size()-1){ //未显示的多余等于八个
            recyclerViewIndex.smoothScrollToPosition(index+8);
        }else{  //未显示的不够八个
            recyclerViewIndex.smoothScrollToPosition(mAdapterIndex.getData().size()-1);
        }
    }
    /**
     * 题目序号滚动到上一部分
     */
    private void scrollLastSection(){
        LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerViewIndex.getLayoutManager();
        int index = layoutManager.findFirstVisibleItemPosition();
        if((index-8) >= 0){ //未显示的多余等于八个
            recyclerViewIndex.smoothScrollToPosition(index-8);
        }else {  //未显示的不够八个
            recyclerViewIndex.smoothScrollToPosition(0);
        }
    }

    /**
     * 控制题号的右向按钮显示
     */
    private RecyclerView.OnScrollListener scrollListenerIndex = new RecyclerView.OnScrollListener(){
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState == RecyclerView.SCROLL_STATE_IDLE){
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItem = manager.findLastVisibleItemPosition();
                int totalItem = manager.getItemCount();
                if(lastVisibleItem == totalItem-1){
                    ibtnMore.setVisibility(View.INVISIBLE);
                }else{
                    ibtnMore.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    private BaseQuickAdapter.OnItemClickListener itemClickListenerIndex = new BaseQuickAdapter.OnItemClickListener(){
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            vpContainer.setCurrentItem(position);
            ((ResolveTchIndexAdapter)mAdapterIndex).setSelIndex(position);
        }
    };


    private ViewPager.OnPageChangeListener vpListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
//            LinearLayoutManager manager = (LinearLayoutManager) recyclerViewIndex.getLayoutManager();
//            int lastVisibleItem = manager.findLastVisibleItemPosition();
//            int firstVisibleItem = manager.findFirstVisibleItemPosition();
//            if(i < firstVisibleItem){
//                scrollLastSection();
//            }else if(i > lastVisibleItem){
//                scrollNextSection();
//            }
            ((ResolveTchIndexAdapter)mAdapterIndex).setSelIndex(i);
            recyclerViewIndex.smoothScrollToPosition(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressHUD != null){
            progressHUD.dismiss();
        }
    }

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
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
