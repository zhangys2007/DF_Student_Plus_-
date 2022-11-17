package com.example.df.zhiyun.preview.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.mvp.contract.INavigation;
import com.example.df.zhiyun.preview.di.component.DaggerPreviewHomeworkComponent;
import com.example.df.zhiyun.preview.mvp.contract.PreviewHomeworkContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.preview.mvp.presenter.PreviewHomeworkPresenter;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.preview.mvp.ui.adapter.PreviewHomeworkAdapter;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 预览作业集
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PreviewHomeworkActivity extends BaseStatusActivity<PreviewHomeworkPresenter> implements PreviewHomeworkContract.View
    ,INavigation {
    public static final String KEY = "homeworkId";
    public static final String KEY_TYPE = "type";
    public static final String KEY_SUBJECTID = "subjectId";
    public static final String KEY_IS_HISTORY = "isHistory";
    @BindView(R.id.toolbar_title)
    TextView tvTitle;
    @BindView(R.id.vp)
    ViewPager vpContainer;

    @Inject
    @Named("type")
    Integer mType;

    @Inject
    KProgressHUD progressHUD;

    PreviewHomeworkAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPreviewHomeworkComponent //如找不到该类,请编译一下项目
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
    public boolean isHistoryPaper() {
        return mType == Api.STUDUNTEN_ANSWER_TYPE_HISTORY;
    }

    @Override
    public String getListenerBaseUrl() {
        return mPresenter.getListenerBaseUrl();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_preview_homework; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        setTitle("0/0");
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        setTitle("0/0");
        mPresenter.requestData();
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
        int index = vpContainer.getCurrentItem()+1;
        if(index < vpContainer.getAdapter().getCount()){
            vpContainer.setCurrentItem(index);
        }
    }

    @Override
    public void lastQuestion() {
        int index = vpContainer.getCurrentItem() -1;
        if(index >= 0){
            vpContainer.setCurrentItem(index);
        }
    }

    @Override
    public void submitPaper() {

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
        Intent intent = new Intent(context, PreviewHomeworkActivity.class);
        intent.putExtra(PreviewHomeworkActivity.KEY,id);
        intent.putExtra(PreviewHomeworkActivity.KEY_TYPE,type);
        intent.putExtra(PreviewHomeworkActivity.KEY_SUBJECTID,subjectId);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public void initViewPager(List<Question> questionList) {
        if (mAdapter == null) {
            mAdapter = new PreviewHomeworkAdapter(getSupportFragmentManager(), questionList);
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
}
