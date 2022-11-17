package com.example.df.zhiyun.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.educate.mvp.ui.activity.PutClsHWActivity;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.model.entity.FormedPaper;
import com.example.df.zhiyun.preview.mvp.ui.activity.PreviewHomeworkActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.di.component.DaggerFormedPapersComponent;
import com.example.df.zhiyun.mvp.contract.FormedPapersContract;
import com.example.df.zhiyun.mvp.presenter.FormedPapersPresenter;

import com.example.df.zhiyun.R;


import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:已组试卷
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 13:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FormedPapersActivity extends BaseStatusActivity<FormedPapersPresenter> implements FormedPapersContract.View
        , SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {
    private static final int REQ_SEL = 89;
    @BindView(R.id.toolbar_right_title)
    TextView tvClassName;

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

    @Inject
    Integer mType;


    public static void launchActivity(Context context,int type){
        Intent intent = new Intent(context, FormedPapersActivity.class);
        intent.putExtra(FormedPapersContract.View.KEY_TYPE,type);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFormedPapersComponent //如找不到该类,请编译一下项目
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
//        setTitle(mType==Api.PUT_TYPE_CLOUD?R.string.clound_homework:R.string.formed_papers);
//        initRefreshLayout();
//        initRecyclerView();
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        setTitle(mType==Api.PUT_TYPE_CLOUD?R.string.clound_homework:R.string.formed_papers);
        initRefreshLayout();
        initRecyclerView();
        mPresenter.classList();
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
//        int padding = ArmsUtils.dip2px(this,10);
//        recyclerView.setPadding(padding,0,padding,0);
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
            FormedPaper item = (FormedPaper)adapter.getData().get(position);
            switch (view.getId()){
                case R.id.ib_sel_preview:
                    if(!TextUtils.isEmpty(item.getPaperId())){
                        if(mType==Api.PUT_TYPE_CLOUD){
                            PreviewHomeworkActivity.launchActivity(view.getContext(),item.getUuid(),Api.STUDUNTEN_ANSWER_TYPE_SEL,0);
                        }else{
                            PreviewHomeworkActivity.launchActivity(view.getContext(),item.getUuid(),Api.STUDUNTEN_ANSWER_TYPE_FORMED,0);
                        }
                    }
                    break;
                case R.id.ib_sel_arrange:
                    PutClsHWActivity.startPutHWPage(view.getContext(), item.getName(),item.getPaperId(),mType,
                            "","");
                    break;
                case R.id.ib_sel_cancel:
                    mPresenter.cancelHomework(position);
                    break;
                case R.id.ib_sel_detail:
                    PutHWDetailActivity.startPutHWDetailPage(view.getContext(), mPresenter.retriveClassName(),item.getPaperId(),mType,
                            "","",mPresenter.retriveClassId());
                    break;
            }
        }
    };

    @Subscriber(tag = EventBusTags.UPDATE_PUT_REMOVE_HW)
    private void updateList(Integer value){
        mPresenter.requestData(true);
    }

    @Override
    public void onRefresh() {
        mPresenter.requestData(true);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestData(false);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_SEL && resultCode == Activity.RESULT_OK){
            String name = data.getStringExtra(ClassSelActivity.KEY_NAME);
            onClassChange(data.getStringExtra(ClassSelActivity.KEY_NAME),
                    data.getStringExtra(ClassSelActivity.KEY_ID));
        }
    }

    private void onClassChange(String name,String id){
        tvClassName.setText(name);
        mPresenter.changeClass(id,name);
        mPresenter.requestData(true);
    }

    @Override
    public void bindClassInfo(BelongClass belongClass) {
        Drawable arrow = ContextCompat.getDrawable(this,R.mipmap.arrow_down);
        arrow.setBounds(0,0,arrow.getIntrinsicWidth(),arrow.getIntrinsicHeight());
        tvClassName.setCompoundDrawables(null,null,arrow,null);
        tvClassName.setText(belongClass.getClassName());
        tvClassName.setOnClickListener(classClickListener);
    }

    private View.OnClickListener classClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ClassSelActivity.class);
            startActivityForResult(intent,REQ_SEL);
        }
    };
}
