package com.example.df.zhiyun.paper.mvp.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.df.zhiyun.mvp.contract.INavigation;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;
import com.example.df.zhiyun.paper.di.component.DaggerSetHomeworkComponent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.mvp.contract.DoHomeworkContract;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.paper.mvp.ui.adapter.SetHomeworkAdapter;
import com.example.df.zhiyun.mvp.ui.widget.ViewPagerSlide;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import com.example.df.zhiyun.app.EventBusTags;

import com.example.df.zhiyun.paper.mvp.contract.SetHomeworkContract;
import com.example.df.zhiyun.paper.mvp.presenter.SetHomeworkPresenter;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.ui.widget.CommonDialog.CommonDialogs;

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
public class SetHomeworkActivity extends BaseStatusActivity<SetHomeworkPresenter> implements SetHomeworkContract.View
    ,View.OnClickListener, INavigation {
    @BindView(R.id.toolbar_title)
    TextView tvTitle;
    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.pb_index)
    ProgressBar pbIndex;
    @BindView(R.id.toolbar_left_title)
    TextView tvTime;
    @BindView(R.id.iv_store)
    ImageView ivStore;
    @BindView(R.id.iv_card)
    ImageView ivCard;
    @BindView(R.id.vp)
    ViewPager vpContainer;

    @Inject
    KProgressHUD progressHUD;
    @Inject
    RxPermissions mRxPermissions;

    SetHomeworkAdapter mAdapter;
    Dialog mDialog;
    private int mLastPageIndex;  //跳转到下一页之前的页面下标

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSetHomeworkComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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

        if(mAdapter != null){
            mAdapter = null;
        }
        QuestionAnswerHolder.getInstance().onDestory(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_set_homework; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {}

    @Override
    public void initAll(Bundle savedInstanceState) {
        QuestionAnswerHolder.getInstance().onCreate(this,savedInstanceState);
        ivCard.setOnClickListener(this);
        ivStore.setOnClickListener(this);
        mPresenter.requestData();
    }

    @Subscriber(tag = EventBusTags.UPDATE_QUESTION_VIEWPAGER)
    public void recvCmdSwitchViewPager(String position){   //position 类似于 1-1  5-2，大题-小题
        if(TextUtils.isEmpty(position)){
            return;
        }

        String[] indexArray = position.split("-");
        if(indexArray == null || indexArray.length == 0){
            return;
        }

        //主页面跳转
        if(indexArray.length > 0 && TextUtils.isDigitsOnly(indexArray[0])){
            changeViewpagerSelect(Integer.parseInt(indexArray[0])-1);   //因为从1开始，要减一
        }

        //子页面的跳转
        if(indexArray.length > 1 && TextUtils.isDigitsOnly(indexArray[1])){
            vpContainer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(Integer.parseInt(indexArray[1])-1
                            , EventBusTags.UPDATE_SUB_QUESTION_VIEWPAGER);
                }
            },1000);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_card:
                mPresenter.clickCard(vpContainer.getCurrentItem());
                break;
            case R.id.iv_store:
//                mPresenter.clickCard(vpContainer.getCurrentItem());
                break;
        }
    }

    @Override
    public void updateTitle(String title) {
        tvTitle.setText(title);
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
        changeViewpagerSelect(vpContainer.getCurrentItem()+1);
    }

    @Override
    public void lastQuestion() {
        changeViewpagerSelect(vpContainer.getCurrentItem()-1);
    }


    public void changeViewpagerSelect(int index){
        if(index >= 0 && index<vpContainer.getAdapter().getCount()){
            vpContainer.setCurrentItem(index);
        }
    }

    /**
     * 数据为空时的一些禁止操作设置
     */
    @Override
    public void disableOption() {
        ivStore.setClickable(false);
        ivCard.setClickable(false);
    }

    @Override
    public String getListenerBaseUrl() {
        return mPresenter.getListenerBaseUrl();
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
        if(message == null){
            return;
        }
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }


    public static void launchActivity(Context context, String studentHomeWorkId, String uuid, int  subjectId,
                                      String schoolId,int jobType){
        Intent intent = new Intent(context,SetHomeworkActivity.class);
        intent.putExtra(SetHomeworkContract.View.KEY_STD_HW_ID,studentHomeWorkId);
        intent.putExtra(SetHomeworkContract.View.KEY_UUID,uuid);
        intent.putExtra(SetHomeworkContract.View.KEY_SUBJECTID,subjectId);
        intent.putExtra(SetHomeworkContract.View.KEY_SCHOOLID,schoolId);
        intent.putExtra(SetHomeworkContract.View.KEY_JOB_TYPE,jobType);
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
        pbIndex.setMax(questionList.size());
        mAdapter = new SetHomeworkAdapter(getSupportFragmentManager(), questionList);
        mAdapter.setParentNavigationType(ViewLastNextInitHelper.TYPE_NONE);
        vpContainer.setAdapter(mAdapter);
        vpContainer.setOffscreenPageLimit(1);
        vpContainer.addOnPageChangeListener(vpListener);
        updatePageIndexText(0);
        vpContainer.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.requestInitWithAnswer(0);
            }
        });
    }

    //更新页标文字
    private void updatePageIndexText(int position){
//        tvTitle.setText(""+(position+1)+"/"+vpContainer.getAdapter().getCount());
        tvIndex.setText(""+(position+1)+"/"+vpContainer.getAdapter().getCount());
        pbIndex.setProgress(position+1);
        mLastPageIndex = position;
    }

    private ViewPager.OnPageChangeListener vpListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            mPresenter.saveAndSubmitQuestion(mLastPageIndex);
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
        if(mAdapter == null){
            return null;
        }
        ArrayList<Fragment> fragments = mAdapter.getFragmentList();
        if(fragments == null){
            return null;
        }
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

    private View.OnClickListener quiteListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            mPresenter.clickQuit();
        }
    };


}
