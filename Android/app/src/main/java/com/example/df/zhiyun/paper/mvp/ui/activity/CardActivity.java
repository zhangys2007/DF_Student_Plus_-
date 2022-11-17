package com.example.df.zhiyun.paper.mvp.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.paper.di.component.DaggerCardComponent;
import com.example.df.zhiyun.paper.mvp.contract.CardContract;
import com.example.df.zhiyun.paper.mvp.ui.adapter.CardAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.model.adapterEntity.CardMultipleItem;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.simple.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.paper.mvp.presenter.CardPresenter;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.ui.widget.CardSpanLookup;
import com.example.df.zhiyun.mvp.ui.widget.CommonDialog.CommonDialogs;
import timber.log.Timber;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2019 12:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CardActivity extends BaseStatusActivity<CardPresenter> implements CardContract.View {
    @BindView(R.id.tv_paper_name)
    TextView tvPaperName;
    @BindView(R.id.recyclerView_answer)
    RecyclerView recyclerView;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.toolbar_left_title)
    TextView tvLeftTitle;

    @Inject
    KProgressHUD progressHUD;
    @Inject
    BaseMultiItemQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;

    Dialog mDialog;


    public static void launchActivity(Context context, String studentHomeWorkId, String homeWorkId,String uuid, int  subjectId,
                                      String schoolId, int jobType,long timeRemain){
        Intent intent = new Intent(context,CardActivity.class);
        intent.putExtra(CardContract.View.KEY_STD_HW_ID,studentHomeWorkId);
        intent.putExtra(CardContract.View.KEY_HW_ID,homeWorkId);
        intent.putExtra(CardContract.View.KEY_UUID,uuid);
        intent.putExtra(CardContract.View.KEY_SUBJECTID,subjectId);
        intent.putExtra(CardContract.View.KEY_SCHOOLID,schoolId);
        intent.putExtra(CardContract.View.KEY_JOB_TYPE,jobType);
        intent.putExtra(CardContract.View.KEY_TIME_REMAIN,timeRemain);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCardComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }

        if(progressHUD != null){
            progressHUD.dismiss();
        }
//        QuestionAnswerHolder.getInstance().onDestory();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_card; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        tvLeftTitle.setText(R.string.paper);
//        tvSubmit.setOnClickListener(viewClickListener);
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        tvLeftTitle.setText(R.string.paper);
        tvSubmit.setOnClickListener(viewClickListener);
        mPresenter.requestData();
        mPresenter.startCountTime();
    }

    private View.OnClickListener viewClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_submit:
                    mPresenter.clickSubmit();
                    break;
            }
        }
    };

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
    public void bindData(String title, List<CardMultipleItem> items) {
        tvPaperName.setText(title);

        if(items != null && items.size() > 0){
            recyclerView.setLayoutManager(layoutManager);
            mAdapter.setOnItemClickListener(itemClickListener);
            mAdapter.setEnableLoadMore(false);
            mAdapter.setSpanSizeLookup(new CardSpanLookup(items));
            recyclerView.setAdapter(mAdapter);
            mAdapter.setNewData(items);
        }
    }

    private BaseMultiItemQuickAdapter.OnItemClickListener itemClickListener
            = new BaseMultiItemQuickAdapter.OnItemClickListener(){
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            CardMultipleItem item = (CardMultipleItem)adapter.getData().get(position);
            if(item.getItemType() == CardMultipleItem.TYPE_TITLE){
                return;
            }

            Timber.tag(TAG).d("click index: " + item.getIndex());
            EventBus.getDefault().post(new Integer(item.getIndex()), EventBusTags.UPDATE_QUESTION_VIEWPAGER);
            killMyself();
        }
    };

    //提交答卷对话框
    public void showSubmitDialog(){
        int count = ((CardAdapter)mAdapter).getUnDoCount();
        StringBuilder builder = new StringBuilder();
        if(count > 0){
            builder.append("你有").append(count).append("道题未做，");
        }
        builder.append(getString(R.string.notice_submit_homework));
        mDialog = new CommonDialogs(this)
                .setTitle(R.string.notice)
                .setMessage(builder.toString())
                .setNegativeButtonColor(R.color.blue)
                .setPositiveButton(getString(R.string.sure),submitListener)
                .setNegativeButton(getString(R.string.cancel),null)
                .builder();
        mDialog.show();
    }

    private View.OnClickListener submitListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            mPresenter.submitAnswer();
        }
    };


    //到时强制交卷
    public void showTimeoutDialog(){
        mDialog = new CommonDialogs(this)
                .setCancelable(false)
                .setTitle(R.string.notice)
                .setMessage(R.string.notice_force_submit)
                .setPositiveButton(getString(R.string.sure),timeoutListener)
                .builder();
        mDialog.show();
    }

    private View.OnClickListener timeoutListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            mPresenter.submitAnswer();
        }
    };
}
