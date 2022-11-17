package com.example.df.zhiyun.correct.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.correct.di.component.DaggerCorrectSomeOneComponent;
import com.example.df.zhiyun.mvp.model.entity.CorrectDetail;
import com.example.df.zhiyun.mvp.ui.widget.RoundBackgroundColorSpan;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.correct.mvp.contract.CorrectSomeOneContract;
import com.example.df.zhiyun.correct.mvp.presenter.CorrectSomeOnePresenter;

import com.example.df.zhiyun.R;
import com.kaopiz.kprogresshud.KProgressHUD;


import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 批改某人的试卷进去前的信息介绍页面
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 14:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CorrectSomeOneActivity extends BaseActivity<CorrectSomeOnePresenter> implements CorrectSomeOneContract.View {
    @BindView(R.id.tv_paper_name)
    TextView tvPaperName;
    @BindView(R.id.tv_submit_number)
    TextView tvSubNumber;
    @BindView(R.id.tv_check_paper)
    TextView tvCheck;
    @BindView(R.id.tv_full_credit)
    TextView tvFull;
    @BindView(R.id.tv_my_credit)
    TextView tvMyCredit;
    @BindView(R.id.tv_avg_credit)
    TextView tvAvgCredit;
    @BindView(R.id.tv_submit_name)
    TextView tvSubName;
    @BindView(R.id.tv_submit_class)
    TextView tvSubClass;
    @BindView(R.id.tv_submit_time)
    TextView tvSubTime;

    @Inject
    KProgressHUD progressHUD;

    @Inject
    Boolean mCorrect;

    public static void startPage(Context context, String studentHomeworkId, boolean isCorrect){
        Intent intent = new Intent(context, CorrectSomeOneActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(CorrectSomeOneContract.View.KEY,studentHomeworkId);
        bundle.putBoolean(CorrectSomeOneContract.View.KEY_CORRECT,isCorrect);
        intent.putExtras(bundle);

        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCorrectSomeOneComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_correct_some_one; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(mCorrect?R.string.corrected:R.string.uncorrected);
        tvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clickCheck();
            }
        });
    }

    @Override
    public void bindData(CorrectDetail data) {
        if(data == null){
            return;
        }

        tvSubTime.setText(TimeUtils.getYmdhms(data.getTime()));
        tvSubName.setText(data.getRealName());
        tvSubClass.setText(data.getClassName());
        tvAvgCredit.setText(String.format("%.1f",data.getAverage()));
        tvFull.setText(String.format("%.1f",data.getScore()));

        if(mCorrect){
            tvMyCredit.setText(String.format("%.1f",data.getStudentHomeworkScore()));
        }

        int bgColor = ContextCompat.getColor(this,R.color.colorPrimary);
        int textColor = ContextCompat.getColor(this,R.color.colorPrimary);

        if(!TextUtils.isEmpty(data.getHomeworkName())){
            StringBuilder builder = new StringBuilder(getString(mCorrect?R.string.corrected:R.string.uncorrected));
            builder.append(data.getHomeworkName());
            SpannableString spannableString = new SpannableString(builder.toString());
            int radius = 4;
            RoundBackgroundColorSpan span = new RoundBackgroundColorSpan(bgColor, textColor, radius);
            spannableString.setSpan(span, 0, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            tvPaperName.setText(spannableString);
        }

        StringBuilder builderNumb = new StringBuilder();
        builderNumb.append(data.getNoStudentCount()).append("/").append(data.getStudentCount());
        ForegroundColorSpan fsp = new ForegroundColorSpan(textColor);
        SpannableString spNumber = new SpannableString(builderNumb.toString());
        spNumber.setSpan(fsp,0,Integer.toString(data.getNoStudentCount()).length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvSubNumber.setText(spNumber);
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
    protected void onDestroy() {
        super.onDestroy();
        if(progressHUD != null){
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
}
