package com.example.df.zhiyun.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.di.component.DaggerCategoryTchComponent;
import com.example.df.zhiyun.mvp.contract.CategoryTchContract;
import com.example.df.zhiyun.mvp.contract.VersionSwitchContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.CategorySubMultipleItem;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.CategoryNode;
import com.example.df.zhiyun.mvp.model.entity.SyncPractice;
import com.example.df.zhiyun.mvp.presenter.CategoryTchPresenter;
import com.example.df.zhiyun.preview.mvp.ui.activity.PreviewHomeworkActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 教材习题 目录页
 * <p>
 * Created by MVPArmsTemplate on 08/18/2019 00:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CategoryTchActivity extends BaseStatusActivity<CategoryTchPresenter> implements CategoryTchContract.View
        , SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener{
    public static final int REQ_CODE = 54;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_left_title)
    TextView tvLeft;

    @BindView(R.id.tv_switch)
    TextView tvSwitch;
    @BindView(R.id.tv_book_name)
    TextView tvBookName;

    @Inject
    BaseMultiItemQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

//    @Inject
//    @Named("subjId")
//    String subjName;


    public static void startSyncExerById(Context context){
        Intent intent = new Intent(context, CategoryTchActivity.class);
//        intent.putExtra(CategoryContract.View.KEY_ID,subjName);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCategoryTchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_category; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        tvLeft.setText(R.string.home_work);
//        initRefreshLayout();
//        initRecyclerView();
//        initListener();
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        tvLeft.setText(R.string.home_work);
        initRefreshLayout();
        initRecyclerView();
        initListener();
        mPresenter.requestData(true);
    }

    @Override
    public void bindVersionInfo(SyncPractice info) {
        StringBuilder builder = new StringBuilder();
        if(!TextUtils.isEmpty(info.getLearningSection())){
            builder.append(info.getLearningSection());
        }

        if(!TextUtils.isEmpty(info.getSemester())){
            builder.append(info.getSemester());
        }
        builder.append(" (");
        if(!TextUtils.isEmpty(info.getTextbookEdition())){
            builder.append(info.getTextbookEdition());
        }
        builder.append(")");

        tvBookName.setText(builder.toString());
    }

    private void initListener(){
//        tvBookName.setOnClickListener(this);
        tvSwitch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_book_name:
                break;
            case R.id.tv_switch:
                VersionSwitchActivity.startVersionSwitchPage(this,"",REQ_CODE);
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
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(itemDecoration);
//        mAdapter.setOnItemClickListener(this::onItemChildClick);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setEnableLoadMore(false);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if(view.getId() == R.id.tv_question_practice){
            Timber.tag(TAG).d("click practice");
            final CategorySubMultipleItem data = (CategorySubMultipleItem) adapter.getData().get(position);
            CategoryNode categoryNode = (CategoryNode)(data.getData());
            PreviewHomeworkActivity.launchActivity(this,categoryNode.getId(),
                    Api.STUDUNTEN_ANSWER_TYPE_SYNC,categoryNode.getSubjectId());
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE && resultCode == Activity.RESULT_OK){
            String strPublisherId = data.getStringExtra(VersionSwitchContract.View.DATA_PUBLISHER);
            String strItemId = data.getStringExtra(VersionSwitchContract.View.DATA_ITEM);

            mPresenter.setBookVersionId(strPublisherId);
            mPresenter.setObligatoryId(strItemId);
            onRefresh();
        }
    }
}
