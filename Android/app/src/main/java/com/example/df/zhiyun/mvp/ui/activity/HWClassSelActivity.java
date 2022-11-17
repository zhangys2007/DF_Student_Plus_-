package com.example.df.zhiyun.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.di.component.DaggerHWClassSelComponent;
import com.example.df.zhiyun.mvp.contract.HWClassSelContract;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.presenter.HWClassSelPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:老师布置作业班级
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 16:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HWClassSelActivity extends BaseStatusActivity<HWClassSelPresenter> implements HWClassSelContract.View
        ,BaseQuickAdapter.OnItemChildClickListener{
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
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

    public static void startHWClassSelPage(Activity context,int reqCode, String paperId, int type, String teachSystemId, String linkId){
        Intent intent = new Intent(context,HWClassSelActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(HWClassSelContract.View.KEY_PAPER_ID,paperId);
        bundle.putInt(HWClassSelContract.View.KEY_TYPE,type);
        bundle.putString(HWClassSelContract.View.KEY_SYSTEM_ID,teachSystemId);
        bundle.putString(HWClassSelContract.View.KEY_LINK_ID,linkId);
        intent.putExtras(bundle);

        context.startActivityForResult(intent,reqCode);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHWClassSelComponent //如找不到该类,请编译一下项目
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
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        initRefreshLayout();
        mPresenter.requestData();
    }

    private void initRefreshLayout(){
        swipeRefreshLayout.setEnabled(false);
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

//        mAdapter.setOnLoadMoreListener(this, recyclerView);
//        mAdapter.setEnableLoadMore(true);
//        mAdapter.setPreLoadNumber(2);   //预加载
//        mAdapter.setLoadMoreView(new ListLoadmoreView()); //自定义的loadmoreview
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Timber.tag(TAG).d("click position: "+ position);
        BelongClass data = (BelongClass)adapter.getData().get(position);
        Intent intent = new Intent();
        intent.putExtra(KEY_ID,data.getClassId());
        intent.putExtra(KEY_NAME,data.getClassName());
        setResult(Activity.RESULT_OK,intent);
        killMyself();
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
