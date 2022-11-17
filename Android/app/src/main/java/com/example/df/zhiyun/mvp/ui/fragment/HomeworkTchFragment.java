package com.example.df.zhiyun.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.ui.activity.ArrangeHWActivity;
import com.example.df.zhiyun.mvp.ui.activity.FormedPapersActivity;
import com.example.df.zhiyun.mvp.ui.activity.SelPaperListActivity;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.ui.activity.ClassSelActivity;
import com.example.df.zhiyun.correct.mvp.ui.activity.CorrectHomeworkActivity;
import com.example.df.zhiyun.mvp.ui.activity.UnpayActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import com.example.df.zhiyun.di.component.DaggerHomeworkTchComponent;
import com.example.df.zhiyun.mvp.contract.HomeworkTchContract;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.model.entity.HomeworkArrange;
import com.example.df.zhiyun.mvp.presenter.HomeworkTchPresenter;

import com.example.df.zhiyun.R;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 13:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeworkTchFragment extends BaseLazyLoadFragment<HomeworkTchPresenter> implements HomeworkTchContract.View
        , SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private static final int REQ_SEL = 89;
    @BindView(R.id.toolbar_left_title)
    TextView tvToolbarLeft;
    @BindView(R.id.title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_search_homework)
    TextView tvToolbarRight;

    @BindView(R.id.swipeRefreshLayout_hw_put)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView_hw_put)
    RecyclerView recyclerView;

    @Inject
    BaseQuickAdapter mAdapter;
//    @Inject
//    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    public static HomeworkTchFragment newInstance() {
        HomeworkTchFragment fragment = new HomeworkTchFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeworkTchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homework_tch, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar();
        initRecyclerView();
        initRefreshLayout();
    }

    private void initToolBar(){
        tvToolbarLeft.setText("...");
        tvToolbarTitle.setText(R.string.home_work);
        tvToolbarRight.setText(R.string.search_homework);

        tvToolbarRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search_homework:
                mPresenter.clickSearch();
                break;
            case R.id.tv_hw_more:
                ArmsUtils.startActivity(ArrangeHWActivity.class);
                break;
            case R.id.cv_cloud_hw:
                FormedPapersActivity.launchActivity(getContext(), Api.PUT_TYPE_CLOUD);
                break;
            case R.id.cv_formed_paper:
                FormedPapersActivity.launchActivity(getContext(), Api.PUT_TYPE_FORMED);
                break;
            case R.id.cv_selected_paper:
                ArmsUtils.startActivity(SelPaperListActivity.class);
                break;
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
        mAdapter.removeAllFooterView();
        mAdapter.removeAllHeaderView();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View head = inflater.inflate(R.layout.view_homework_head,null,false);
        mAdapter.addHeaderView(head);
        View footer = inflater.inflate(R.layout.view_homework_footer,null,false);
        mAdapter.addFooterView(footer);

        head.findViewById(R.id.tv_hw_more).setOnClickListener(this);
        footer.findViewById(R.id.cv_cloud_hw).setOnClickListener(this);
        footer.findViewById(R.id.cv_selected_paper).setOnClickListener(this);
        footer.findViewById(R.id.cv_formed_paper).setOnClickListener(this);

        RecyclerView.LayoutManager  manager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        mAdapter.setOnItemChildClickListener(hwItemChildClickListener);
        recyclerView.setAdapter(mAdapter);
    }

    public void bindHomeworkDatas(List<HomeworkArrange> datas){
        mAdapter.setNewData(datas);
    }

    @Override
    public void setData(@Nullable Object data) {}


    private BaseQuickAdapter.OnItemChildClickListener hwItemChildClickListener = new BaseQuickAdapter.OnItemChildClickListener(){
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            HomeworkArrange item = (HomeworkArrange)adapter.getData().get(position);
            switch (view.getId()){
                case R.id.ll_corrected:
                    CorrectHomeworkActivity.toCorrectPage(getContext(),item,true);
                    break;
                case R.id.iv_start:
                    CorrectHomeworkActivity.toCorrectPage(getContext(),item,false);
                    break;
                case R.id.ll_uncorrected:
                    CorrectHomeworkActivity.toCorrectPage(getContext(),item,false);
                    break;
                case R.id.ll_unsubmit:
                    UnpayActivity.toUnpayPage(getContext(),item);
                    break;
            }
        }
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            Timber.tag(TAG).d("onActivityCreated");
            lazyLoadData();
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.requestData();
        mPresenter.classList();
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
    protected void lazyLoadData() {
        mPresenter.requestData();
        mPresenter.classList();
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

    @Override
    public void bindClassInfo(BelongClass belongClass) {
        tvToolbarLeft.setText(belongClass.getClassName());
        tvToolbarLeft.setOnClickListener(classClickListener);
    }

    private View.OnClickListener classClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), ClassSelActivity.class);
            startActivityForResult(intent,REQ_SEL);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_SEL && resultCode == Activity.RESULT_OK){
            String name = data.getStringExtra(ClassSelActivity.KEY_NAME);
            onClassChange(data.getStringExtra(ClassSelActivity.KEY_NAME),
                    data.getStringExtra(ClassSelActivity.KEY_ID));
        }
    }

    private void onClassChange(String name,String id){
        tvToolbarLeft.setText(name);
        mPresenter.changeClass(id);
        mPresenter.requestData();
    }
}
