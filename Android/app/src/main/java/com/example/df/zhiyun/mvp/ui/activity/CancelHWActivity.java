package com.example.df.zhiyun.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.di.component.DaggerCancelHWComponent;
import com.example.df.zhiyun.mvp.contract.CancelHWContract;
import com.example.df.zhiyun.mvp.presenter.CancelHWPresenter;
import com.example.df.zhiyun.mvp.ui.adapter.CancelHWClassAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 从精选试卷列表进入的撤销
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 15:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CancelHWActivity extends BaseStatusActivity<CancelHWPresenter> implements CancelHWContract.View
    ,BaseQuickAdapter.OnItemClickListener, View.OnClickListener{
    public static int TYPE_HW = 0;
    public static int TYPE_TEST = 1;

    @BindView(R.id.tv_put_hw_name)
    TextView tvName;
    @BindView(R.id.tv_put)
    TextView tvPut;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    BaseQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;

    @Inject
    KProgressHUD progressHUD;

    @Inject
    @Named(CancelHWContract.View.KEY_PAPER_NAME)
    String mPaperName;
//    TimePickerView pickerView;

    public static void startCancelHWPage(Context context,String name,String paperId,int type,String teachSystemId,String linkId){
        Intent intent = new Intent(context, CancelHWActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(CancelHWContract.View.KEY_PAPER_NAME,name);
        bundle.putString(CancelHWContract.View.KEY_PAPER_ID,paperId);
        bundle.putInt(CancelHWContract.View.KEY_TYPE,type);
        bundle.putString(CancelHWContract.View.KEY_SYSTEM_ID,teachSystemId);
        bundle.putString(CancelHWContract.View.KEY_LINK_ID,linkId);
        intent.putExtras(bundle);

        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCancelHWComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_cancel_hw; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initBaseView();
        initRecyclerView();
        mPresenter.classList();
    }

    private void initBaseView(){
        tvPut.setOnClickListener(this);

        tvName.setText(mPaperName);
    }

    private void initRecyclerView(){
//        int padding = ArmsUtils.dip2px(this,10);
//        recyclerView.setPadding(padding,0,padding,0);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter.setOnItemClickListener(this);

        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_put:
                mPresenter.clickCancel();
                break;
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ((CancelHWClassAdapter)adapter).clickItem(position);
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
}
