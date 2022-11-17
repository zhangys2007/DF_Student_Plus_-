package com.example.df.zhiyun.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.correct.mvp.ui.activity.CorrectHomeworkActivity;
import com.example.df.zhiyun.di.component.DaggerSearchTchComponent;
import com.example.df.zhiyun.mvp.contract.SearchTchContract;
import com.example.df.zhiyun.mvp.model.entity.HomeworkArrange;
import com.example.df.zhiyun.mvp.presenter.SearchTchPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 16:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SearchTchActivity extends BaseStatusActivity<SearchTchPresenter> implements SearchTchContract.View, View.OnClickListener
    , SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener
        ,BaseQuickAdapter.OnItemChildClickListener{
    @BindView(R.id.toolbar_right_title)
    TextView tvSubmit;
//    @BindView(R.id.tv_search_type)
//    TextView tvSearchType;
    @BindView(R.id.et_search_content)
    EditText etSearchContent;

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

    public static void startSearchPage(Context context, String classId){
        Intent intent = new Intent(context, SearchTchActivity.class);
        intent.putExtra(SearchTchContract.View.KEY_CLASSID,classId);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchTchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search_tch; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        tvSubmit.setOnClickListener(this);
//        etSearchContent.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
//        etSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            public boolean onEditorAction(TextView v, int actionId,                   KeyEvent event)  {
//                if (actionId==EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
//                {
//                    Timber.tag(TAG).d("enter...");
//                    mPresenter.search(true,etSearchContent.getText().toString());
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        initRefreshLayout();
//        initRecyclerView();
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        tvSubmit.setOnClickListener(this);
        etSearchContent.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        etSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,                   KeyEvent event)  {
                if (actionId==EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
                    Timber.tag(TAG).d("enter...");
                    mPresenter.search(true,etSearchContent.getText().toString());
                    return true;
                }
                return false;
            }
        });

        initRefreshLayout();
        initRecyclerView();
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
        mAdapter.setOnItemChildClickListener(this::onItemChildClick);

        mAdapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter.setEnableLoadMore(true);
//        mAdapter.setPreLoadNumber(2);   //预加载
//        mAdapter.setLoadMoreView(new ListLoadmoreView()); //自定义的loadmoreview
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.search(false,etSearchContent.getText().toString());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        HomeworkArrange item = (HomeworkArrange)adapter.getData().get(position);
        switch (view.getId()){
            case R.id.ll_corrected:
                CorrectHomeworkActivity.toCorrectPage(this,item,true);
                break;
            case R.id.iv_start:
                CorrectHomeworkActivity.toCorrectPage(this,item,false);
                break;
            case R.id.ll_uncorrected:
                CorrectHomeworkActivity.toCorrectPage(this,item,false);
                break;
            case R.id.ll_unsubmit:
                UnpayActivity.toUnpayPage(this,item);
                break;
        }
    }
    @Override
    public void onRefresh() {
        mPresenter.search(true,etSearchContent.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_right_title:
                mPresenter.search(true,etSearchContent.getText().toString());
                break;
        }
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
}
