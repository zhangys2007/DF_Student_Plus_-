package com.example.df.zhiyun.correct.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.correct.di.component.DaggerCorrectHWComponent;
import com.example.df.zhiyun.correct.mvp.presenter.CorrectHWPresenter;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWContract;
import com.example.df.zhiyun.mvp.contract.INavigation;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.correct.mvp.ui.adapter.CorrectHWAdapter;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 批改作业集
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CorrectHWActivity extends BaseStatusActivity<CorrectHWPresenter> implements CorrectHWContract.View
    ,INavigation {
    @BindView(R.id.toolbar_title)
    TextView tvTitle;
    @BindView(R.id.vp)
    ViewPager vpContainer;
    @BindView(R.id.toolbar_right_title)
    TextView tvToolbarRight;

    @Inject
    KProgressHUD progressHUD;

    @Inject
    Boolean mCorrect;

    CorrectHWAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCorrectHWComponent //如找不到该类,请编译一下项目
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
        if(progressHUD != null){
            progressHUD.dismiss();
        }
        QuestionAnswerHolder.getInstance().onDestory(this);
    }

    @Override
    public boolean isCorrected() {
        return mCorrect;
    }

    @Override
    public String getListenerBaseUrl() {
        return mPresenter.getListenerBaseUrl();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_correct_homework; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        setTitle("0/0");
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        setTitle("0/0");
        mPresenter.requestData();


        Drawable drawable = ContextCompat.getDrawable(this,R.mipmap.card);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        tvToolbarRight.setCompoundDrawables(null,null, drawable,null);

        tvToolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPaper();
            }
        });
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
    public void nextQuestion() {
        int currentIndex = vpContainer.getCurrentItem();
        int nextIndex = vpContainer.getCurrentItem() +1;
        if(nextIndex < vpContainer.getAdapter().getCount()){
            mPresenter.changePage(currentIndex,nextIndex);
        }
    }

    @Override
    public void lastQuestion() {
        int currentIndex = vpContainer.getCurrentItem();
        int nextIndex = vpContainer.getCurrentItem() -1;
        if(nextIndex >= 0){
            mPresenter.changePage(currentIndex,nextIndex);
        }
    }

    @Override
    public void submitPaper() {
        int index = vpContainer.getCurrentItem();
        mPresenter.submitAllCorrections(index);
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
     * @param type 0云作业 1 自有作业
     * @param studentHomeworkId   同步练习的时候才需要
     */
    public static void launchActivity(Context context,int type, String  studentHomeworkId, int studentCount,boolean correct){
        Intent intent = new Intent(context, CorrectHWActivity.class);
        intent.putExtra(CorrectHWContract.View.KEY_TYPE,type);
        intent.putExtra(CorrectHWContract.View.KEY_STUDENT_COUNT,studentCount);
        intent.putExtra(CorrectHWContract.View.KEY_ID,studentHomeworkId);
        intent.putExtra(CorrectHWContract.View.KEY_CORRECT,correct);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public void initViewPager(List<Question> questionList) {
        if (mAdapter == null) {
            mAdapter = new CorrectHWAdapter(getSupportFragmentManager(), questionList);
        }
        mAdapter.setParentNavigationType(ViewLastNextInitHelper.TYPE_NONE);
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
            updatePageIndexText(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    public void changeViewpagerSelect(int index){
        if(index >= 0 && index<vpContainer.getAdapter().getCount()){
            vpContainer.setCurrentItem(index);
        }
    }

    @Subscriber(tag = EventBusTags.UPDATE_QUESTION_VIEWPAGER)
    public void recvCmdSwitchViewPager(Integer position){
        changeViewpagerSelect(position);
    }
}
