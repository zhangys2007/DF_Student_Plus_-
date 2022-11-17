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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.model.entity.Homework;
import com.example.df.zhiyun.paper.mvp.ui.activity.SetHomeworkActivity;
import com.example.df.zhiyun.preview.mvp.ui.activity.PreviewHomeworkActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.contract.SearchContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import com.example.df.zhiyun.di.component.DaggerSearchComponent;

import com.example.df.zhiyun.mvp.model.adapterEntity.SearchMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.Question;

import com.example.df.zhiyun.R;
import me.kareluo.ui.OptionMenu;
import me.kareluo.ui.OptionMenuView;
import me.kareluo.ui.PopupMenuView;
import me.kareluo.ui.PopupView;
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
public class SearchActivity extends BaseStatusActivity<SearchPresenter> implements SearchContract.View, View.OnClickListener
    , SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener
        ,BaseQuickAdapter.OnItemChildClickListener{
    @BindView(R.id.toolbar_right_title)
    TextView tvSubmit;
    @BindView(R.id.tv_search_type)
    TextView tvSearchType;
    @BindView(R.id.et_search_content)
    EditText etSearchContent;

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

    @Inject
    Integer searchType;
    PopupMenuView popupMenuView;

    public static void startSearchPage(Context context, int type){
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(SearchContract.View.KEY_TYPE,type);
        ArmsUtils.startActivity(intent);
    }

    public static void startSearchPage(Context context, int type, String subjId){
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(SearchContract.View.KEY_TYPE,type);
        intent.putExtra(SearchContract.View.KEY_SUBJECTID,subjId);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        tvSubmit.setOnClickListener(this);
//        tvSearchType.setOnClickListener(this);
//        updateSearchType(searchType);
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
        tvSearchType.setOnClickListener(this);
        updateSearchType(searchType);
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
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        mAdapter.setOnItemClickListener(this::onItemChildClick);

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
        SearchMultipleItem item = (SearchMultipleItem)adapter.getData().get(position);
        if(item.getItemType() == Api.SEARCH_PAPER){
            EQDetailActivity.startErrorDetailPage(this,((Question)item.getData()).getQuestionId(),
                    ((Question)item.getData()).getSubjectId(),true);
        }else if(item.getItemType() == Api.SEARCH_HOMEWORK){
            Homework homework = (Homework) item.getData();
            if(homework.getStatus() == 0){
//                SetHomeworkActivity.launchActivity(this,homework.getStudentHomeworkId(),
//                        Api.STUDUNTEN_ANSWER_TYPE_HOMEWORK);
                SetHomeworkActivity.launchActivity(this,homework.getStudentHomeworkId(),
                        "",0,"",0);
            }else{
                PreviewHomeworkActivity.launchActivity(this,homework.getStudentHomeworkId(),
                        Api.STUDUNTEN_ANSWER_TYPE_HISTORY,0);
            }
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
            case R.id.tv_search_type:
                showSubjectPopupMenu();
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

    private void showSubjectPopupMenu(){
        if(popupMenuView == null){
            List<OptionMenu> menus = new ArrayList<>();
            popupMenuView = new PopupMenuView(this);
            menus.add(new OptionMenu(getResources().getString(R.string.paper_question)));
            menus.add(new OptionMenu(getResources().getString(R.string.home_work)));
            popupMenuView.setMenuItems(menus);
            popupMenuView.setSites(PopupView.SITE_BOTTOM);
            popupMenuView.setOrientation(LinearLayout.VERTICAL);
            popupMenuView.setOnMenuClickListener(popupMenuClickListener);
        }
        popupMenuView.show(tvSearchType);
    }

    private OptionMenuView.OnOptionMenuClickListener popupMenuClickListener = new OptionMenuView.OnOptionMenuClickListener(){

        @Override
        public boolean onOptionMenuClick(int position, OptionMenu menu) {
            updateSearchType(position==0? Api.SEARCH_PAPER:Api.SEARCH_HOMEWORK);
            return true;
        }
    };

    private void updateSearchType(int type){
        tvSearchType.setText(type==Api.SEARCH_PAPER ? R.string.paper_question : R.string.home_work);
        mPresenter.changeType(type);
    }
}
