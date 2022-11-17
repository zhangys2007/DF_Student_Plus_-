package com.example.df.zhiyun.comment.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.comment.di.component.DaggerCommentAnalyDetailComponent;
import com.example.df.zhiyun.mvp.ui.activity.BaseWebActivity;
import com.example.df.zhiyun.mvp.ui.activity.ClassSelActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.comment.mvp.contract.CommentAnalyDetailContract;
import com.example.df.zhiyun.comment.mvp.presenter.CommentAnalyDetailPresenter;


import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:  教师评论详情
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 15:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CommentAnalyDetailActivity extends BaseWebActivity<CommentAnalyDetailPresenter> implements CommentAnalyDetailContract.View {
    private static final int REQ_SEL = 89;
    @BindView(R.id.toolbar_right_title)
    TextView tvClassName;

    @Inject
    @Named(CommentAnalyDetailContract.View.KEY_NAME)
    String mName;

    @Inject
    @Named(CommentAnalyDetailContract.View.KEY_URL)
    String mUrl;

    @Inject
    @Named(CommentAnalyDetailContract.View.KEY_ID)
    String mId;

    @Inject
    @Named(CommentAnalyDetailContract.View.KEY_HOMEWORK_ID)
    String mHomeworkId;

    @Inject
    @Named(CommentAnalyDetailContract.View.KEY_CLASS_ID)
    String mClassId;

    @Inject
    @Named(CommentAnalyDetailContract.View.KEY_CLASS_NAME)
    String mClassName;

    @Inject
    @Named(CommentAnalyDetailContract.View.KEY_SUB_NAME)
    String mSubName;


    public static void launcheDetailActivity(Context context, String name, String url,String subName ,String id,String homeworkId ,String classId,String className){
        Intent intent = new Intent(context, CommentAnalyDetailActivity.class);
        intent.putExtra(CommentAnalyDetailContract.View.KEY_NAME,name);
        intent.putExtra(CommentAnalyDetailContract.View.KEY_URL,url);
        intent.putExtra(CommentAnalyDetailContract.View.KEY_ID,id);
        intent.putExtra(CommentAnalyDetailContract.View.KEY_HOMEWORK_ID,homeworkId);
        intent.putExtra(CommentAnalyDetailContract.View.KEY_CLASS_ID,classId);
        intent.putExtra(CommentAnalyDetailContract.View.KEY_CLASS_NAME,className);
        intent.putExtra(CommentAnalyDetailContract.View.KEY_SUB_NAME,subName);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_SEL && resultCode == Activity.RESULT_OK){
            onClassChange(data.getStringExtra(ClassSelActivity.KEY_NAME),
                    data.getStringExtra(ClassSelActivity.KEY_ID));
        }
    }

    private void onClassChange(String name,String id){
        mClassName = name;
        tvClassName.setText(mClassName);
        mClassId = id;
        onRefresh();
    }


    private View.OnClickListener classClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ClassSelActivity.class);
            startActivityForResult(intent,REQ_SEL);
        }
    };


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommentAnalyDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public String getUrl() {
        return String.format("%s&commentonId=%s&classId=%s&homeworkId=%s&subValue=%s",mUrl,mId,mClassId,mHomeworkId,mSubName);
    }

    @Override
    public void setWebView(WebView webView) {

    }

    @Override
    public void costumSetting(Bundle savedInstanceState) {
        setTitle(mName);
        Drawable arrow = ContextCompat.getDrawable(this,R.mipmap.arrow_down);
        arrow.setBounds(0,0,arrow.getIntrinsicWidth(),arrow.getIntrinsicHeight());
        tvClassName.setCompoundDrawables(null,null,arrow,null);
        tvClassName.setText(mClassName);
        tvClassName.setOnClickListener(classClickListener);
        onRefresh();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
