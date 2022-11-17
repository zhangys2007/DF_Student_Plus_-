package com.example.df.zhiyun.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.di.component.DaggerPutHWDetailComponent;
import com.example.df.zhiyun.educate.mvp.ui.activity.PutClsHWActivity;
import com.example.df.zhiyun.mvp.contract.PutHWDetailContract;
import com.example.df.zhiyun.mvp.model.entity.PutHWDetail;
import com.example.df.zhiyun.mvp.presenter.PutHWDetailPresenter;
import com.example.df.zhiyun.mvp.ui.widget.GridDividerItemDecoration;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:布置作业详情
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 15:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PutHWDetailActivity extends BaseStatusActivity<PutHWDetailPresenter> implements PutHWDetailContract.View
    ,View.OnClickListener{
    private static SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分", Locale.getDefault());

    @BindView(R.id.toolbar_right_title)
    TextView tvClassName;
    @BindView(R.id.tv_put_hw_name)
    TextView tvName;
    @BindView(R.id.ll_star)
    LinearLayout llStart;
    @BindView(R.id.ll_end)
    LinearLayout llEnd;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.swt_start_hide)
    Switch swStart;
    @BindView(R.id.swt_submit_show)
    Switch swSubmit;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    BaseQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    GridDividerItemDecoration itemDecoration;

    @Inject
    KProgressHUD progressHUD;

    public static void startPutHWDetailPage(Context context,String className,String paperId,int type,String teachSystemId,String linkId,String classID){
        Intent intent = new Intent(context, PutHWDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(PutHWDetailContract.View.KEY_CLASS_NAME,className);
        bundle.putString(PutHWDetailContract.View.KEY_PAPER_ID,paperId);
        bundle.putInt(PutHWDetailContract.View.KEY_TYPE,type);
        bundle.putString(PutHWDetailContract.View.KEY_SYSTEM_ID,teachSystemId);
        bundle.putString(PutHWDetailContract.View.KEY_LINK_ID,linkId);
        bundle.putString(PutHWDetailContract.View.KEY_CLASS_ID,classID);
        intent.putExtras(bundle);

        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPutHWDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_put_hw_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        initRecyclerView();
//        Drawable arrow = ContextCompat.getDrawable(this,R.mipmap.arrow_down);
//        arrow.setBounds(0,0,arrow.getIntrinsicWidth(),arrow.getIntrinsicHeight());
//        tvClassName.setCompoundDrawables(null,null,arrow,null);
//        tvClassName.setOnClickListener(this);
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        Drawable arrow = ContextCompat.getDrawable(this,R.mipmap.arrow_down);
        arrow.setBounds(0,0,arrow.getIntrinsicWidth(),arrow.getIntrinsicHeight());
        tvClassName.setCompoundDrawables(null,null,arrow,null);
        tvClassName.setOnClickListener(this);
        mPresenter.startProcess();
    }

    @Override
    public void bindDetailInfo(PutHWDetail data) {
        if(data.getStrings() != null){
            mAdapter.setNewData(data.getStrings());
        }
        tvName.setText(data.getHomeworkName());
//        tvType.setText(data.getTypes()== PutClsHWActivity.TYPE_HW?R.string.home_work:R.string.test);
        tvStartTime.setText(ymdhm.format(new java.util.Date(data.getBeginTime())));
        tvEndTime.setText(ymdhm.format(new java.util.Date(data.getEndTime())));
        tvCreateTime.setText(ymdhm.format(new java.util.Date(data.getCreateTime())));
        swStart.setChecked(data.getIsHide() == 1);
        swSubmit.setChecked(data.getIsDisplayAnswer() == 1);
    }

    private void initRecyclerView(){
//        int padding = ArmsUtils.dip2px(this,10);
//        recyclerView.setPadding(padding,0,padding,0);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_right_title:
                mPresenter.clickClass();
                break;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressHUD != null){
            progressHUD.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PutHWDetailContract.View.REQ_SEL && resultCode == Activity.RESULT_OK){
            mPresenter.changeClass(data.getStringExtra(ClassSelActivity.KEY_ID)
                    ,data.getStringExtra(ClassSelActivity.KEY_NAME));
            updateClassName(data.getStringExtra(ClassSelActivity.KEY_NAME));
            mPresenter.getData();
        }
    }

    @Override
    public void updateClassName(String clName) {
        tvClassName.setText(clName);
    }
}
