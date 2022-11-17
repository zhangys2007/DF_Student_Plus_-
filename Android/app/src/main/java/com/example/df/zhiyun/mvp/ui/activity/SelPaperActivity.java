package com.example.df.zhiyun.mvp.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.di.component.DaggerSelPaperComponent;
import com.example.df.zhiyun.mvp.contract.DoHomeworkContract;
import com.example.df.zhiyun.mvp.contract.INavigation;
import com.example.df.zhiyun.mvp.contract.SelPaperContract;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.presenter.SelPaperPresenter;
import com.example.df.zhiyun.paper.mvp.ui.adapter.SetHomeworkAdapter;
import com.example.df.zhiyun.mvp.ui.widget.CommonDialog.CommonDialogs;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;
import com.example.df.zhiyun.mvp.ui.widget.ViewPagerSlide;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 学生做作业集
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SelPaperActivity extends BaseStatusActivity<SelPaperPresenter> implements SelPaperContract.View
    ,View.OnClickListener, INavigation {
    public static final String KEY = "homeworkId";
    public static final String KEY_TYPE = "type";
    public static final String KEY_SUBJECTID = "subjectId";
    public static final String KEY_IS_HISTORY = "isHistory";
    @BindView(R.id.toolbar_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.vp)
    ViewPagerSlide vpContainer;

    @Inject
    KProgressHUD progressHUD;
    @Inject
    RxPermissions mRxPermissions;

    SetHomeworkAdapter mAdapter;
    Dialog mDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSelPaperComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        QuestionAnswerHolder.getInstance().onDestory(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sel_paper; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        setTitle("0/0");
//        tvCard.setOnClickListener(this);
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        setTitle("0/0");
        tvCard.setOnClickListener(this);
        mPresenter.requestData();
    }

    @Subscriber(tag = EventBusTags.UPDATE_QUESTION_VIEWPAGER)
    public void recvCmdSwitchViewPager(Integer position){
        changeViewpagerSelect(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_card:
                mPresenter.clickCard(vpContainer.getCurrentItem());
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

    public RxPermissions getRxPermissions(){
        return mRxPermissions;
    }

    @Override
    public void hideLoading() {
        if(progressHUD!=null){
            progressHUD.dismiss();
        }
    }

    @Override
    public void nextQuestion() {
        mPresenter.requestNextPage(vpContainer.getCurrentItem()+1);
    }

    @Override
    public void lastQuestion() {
        mPresenter.requestNextPage(vpContainer.getCurrentItem()-1);
    }


    public void changeViewpagerSelect(int index){
        if(index >= 0 && index<vpContainer.getAdapter().getCount()){
            vpContainer.setCurrentItem(index);
        }
    }

    @Override
    public void submitPaper() {
//        showSubmitDialog();
        mPresenter.clickCard(vpContainer.getCurrentItem());
    }

    @Override
    public int getCurrentQuestionIndex() {
        return vpContainer.getCurrentItem();
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

    /***
     *
     * @param context
     * @param id
     * @param type  Api.STUDUNTEN_ANSWER_TYPE_HOMEWORK Api.STUDUNTEN_ANSWER_TYPE_SYNC  作业或者同步练习
     */
    public static void launchActivity(Context context, String id, int type){
        launchActivity(context,id, type,0);
    }

    /***
     *
     * @param context
     * @param id
     * @param type
     * @param subjectId   同步练习的时候才需要
     */
    public static void launchActivity(Context context, String id, int type, int  subjectId){
        Intent intent = new Intent(context, SelPaperActivity.class);
        intent.putExtra(SelPaperActivity.KEY,id);
        intent.putExtra(SelPaperActivity.KEY_TYPE,type);
        intent.putExtra(SelPaperActivity.KEY_SUBJECTID,subjectId);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public void updateCountTime(long value) {
        long second = value % 60;
        long minite = value / 60;
        Formatter formatter = new Formatter();
        tvTime.setText(formatter.format("%02d:%02d",minite,second).toString());
    }

    @Override
    public void initViewPager(List<Question> questionList) {
        if (mAdapter == null) {
            mAdapter = new SetHomeworkAdapter(getSupportFragmentManager(), questionList);
        }
        ((SetHomeworkAdapter)mAdapter).setParentNavigationType(ViewLastNextInitHelper.TYPE_NONE);
        vpContainer.setAdapter(mAdapter);
        vpContainer.setOffscreenPageLimit(1);
        vpContainer.addOnPageChangeListener(vpListener);
        updatePageIndexText(0);
    }

    //更新页标文字
    private void updatePageIndexText(int position){
        tvTitle.setText(""+(position+1)+"/"+vpContainer.getAdapter().getCount());
    }



    private ViewPager.OnPageChangeListener vpListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            mPresenter.requestInitWithAnswer(i);
            updatePageIndexText(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    //提取答案
    public Answer getAnswerFromQuestion(int index){
        if(index < 0){
            index = vpContainer.getCurrentItem();
        }
        ArrayList<Fragment> fragments = mAdapter.getFragmentList();
        Fragment fragment = fragments.get(index);
        if(fragment != null){
           return  ((DoHomeworkContract)fragment).getAnswer();
        }else{
            return null;
        }
    }

    //恢复问题的所选所填
    public void initQuestionWithAnswer(int index, Answer answer){
        if(answer == null){
            return;
        }

        ArrayList<Fragment> fragments = mAdapter.getFragmentList();
        Fragment fragment = fragments.get(index);
        if(fragment != null){
            ((DoHomeworkContract)fragment).initeWhiteAnswer(answer);
        }
    }

    @Override
    public void onBackPressed() {
        showQuitDialog();
    }

    //退出答卷对话框
    public void showQuitDialog(){
        mDialog = new CommonDialogs(this)
                .setTitle(R.string.notice)
                .setMessage(R.string.notice_quit_homework)
                .setNegativeButtonColor(R.color.blue)
                .setPositiveButton(getString(R.string.sure),quiteListener)
                .setNegativeButton(getString(R.string.cancel),null)
                .builder();
        mDialog.show();
    }

    //提交答卷对话框
    public void showSubmitDialog(){
        mDialog = new CommonDialogs(this)
                .setTitle(R.string.notice)
                .setMessage(R.string.notice_submit_homework)
                .setNegativeButtonColor(R.color.blue)
                .setPositiveButton(getString(R.string.sure),submitListener)
                .setNegativeButton(getString(R.string.cancel),null)
                .builder();
        mDialog.show();
    }

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
            mPresenter.forceSubmit(vpContainer.getCurrentItem());
        }
    };


    private View.OnClickListener quiteListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            killMyself();
        }
    };

    private View.OnClickListener submitListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            mPresenter.forceSubmit(vpContainer.getCurrentItem());
        }
    };
}
