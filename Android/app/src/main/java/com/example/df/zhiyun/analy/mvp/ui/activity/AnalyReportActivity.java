package com.example.df.zhiyun.analy.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.analy.di.component.DaggerAnalyReportComponent;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.model.entity.AnalyReport;
import com.example.df.zhiyun.mvp.ui.widget.RoundBackgroundColorSpan;
import com.example.df.zhiyun.mvp.ui.widget.RoundnessProgressBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.analy.mvp.contract.AnalyReportContract;
import com.example.df.zhiyun.analy.mvp.presenter.AnalyReportPresenter;

import com.example.df.zhiyun.R;
import com.kaopiz.kprogresshud.KProgressHUD;


import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 分析报告
 * <p>
 * Created by MVPArmsTemplate on 10/25/2019 15:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AnalyReportActivity extends BaseActivity<AnalyReportPresenter> implements AnalyReportContract.View {
    @BindView(R.id.extension_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.tv_student_score)
    TextView tvStudentScore;
//    @BindView(R.id.progress)
//    ProgressBar progressBar;
    @BindView(R.id.progress)
    RoundnessProgressBar progressBar;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_score_full)
    TextView tvFull;
    @BindView(R.id.tv_class_info)
    TextView tvClassInfo;
    @BindView(R.id.tv_paper_name)
    TextView tvPaperName;

    @Inject
    BaseQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;

    @Inject
    KProgressHUD progressHUD;


    public static void launchActivity(Context context, String hwId){
        Intent intent = new Intent(context, AnalyReportActivity.class);
        intent.putExtra(AnalyReportContract.View.KEY_ID,hwId);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAnalyReportComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_analy_report; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        mPresenter.getData();
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(layoutManager);
//        mAdapter.setOnItemClickListener(this::onItemClick);
        recyclerView.setAdapter(mAdapter);
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
    public void bindData(AnalyReport data) {
        if(data.getQuestionScoreList() != null){
            mAdapter.setNewData(data.getQuestionScoreList());
        }
        tvStudentScore.setText(String.format("%.1f",data.getStudentHomeworkScore()));
        tvTime.setText(String.format("%d'%d''",(data.getTimeLenth()/1000)/60,(data.getTimeLenth()/1000)%60));
        tvFull.setText(String.format("%d分",(int)data.getScore()));


        tvClassInfo.setText(StringCocatUtil.concat("年级：",data.getGradeName(),"   班级：",
                data.getClassName(),"   班级人数：",Integer.toString(data.getCount()),"人"));

        boolean correct = data.getHomeworkStatus() == 1;
        int bgColor = ContextCompat.getColor(this,correct?R.color.green:R.color.yellow);
        int textColor = ContextCompat.getColor(this,correct?R.color.green:R.color.yellow);

        StringBuilder builder = new StringBuilder(getString(correct?R.string.corrected:R.string.uncorrected));
        builder.append(data.getHomeworkName());
        SpannableString spannableString = new SpannableString(builder.toString());
        int radius = 4;
        RoundBackgroundColorSpan span = new RoundBackgroundColorSpan(bgColor, textColor, radius);
        spannableString.setSpan(span, 0, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvPaperName.setText(spannableString);


//        progressBar.setMax((int)data.getScore());
//        progressBar.setProgress((int)data.getStudentHomeworkScore());
        progressBar.setMaxValue(data.getScore());
        progressBar.setProgress(data.getStudentHomeworkScore());
    }


}
