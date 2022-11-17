package com.example.df.zhiyun.setting.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.df.zhiyun.mvp.model.entity.Edition;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.mvp.ui.widget.CommonDialog.CommonDialogs;
import com.example.df.zhiyun.setting.di.component.DaggerSettingComponent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.setting.mvp.contract.SettingContract;
import com.example.df.zhiyun.setting.mvp.presenter.SettingPresenter;

import butterknife.BindView;
import com.jess.arms.utils.DeviceUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import javax.inject.Inject;

import com.example.df.zhiyun.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/21/2019 13:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SettingActivity extends BaseStatusActivity<SettingPresenter> implements SettingContract.View,
        View.OnClickListener{
    @BindView(R.id.toolbar_left_title)
    TextView tvParent;
    @BindView(R.id.tv_local_store)
    TextView tvLocalStore;
    @BindView(R.id.ll_clean_cache)
    LinearLayout llCleanCache;
    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;
//    @BindView(R.id.tv_help)
//    TextView tvHelp;
    @BindView(R.id.tv_feedback_item)
    TextView tvFeedback;
    @BindView(R.id.ll_version)
    LinearLayout llVersion;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_logout)
    TextView tvLogout;


    @Inject
    KProgressHUD progressHUD;
    Dialog mDialog;
    Dialog mUpdateDialog;
    private Edition mEdition;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }



    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        tvParent.setText(getString(R.string.my));
//        tvLogout.setOnClickListener(this);
//        tvAbout.setOnClickListener(this);
//        llCleanCache.setOnClickListener(this);
//        tvFeedback.setOnClickListener(this);
//        tvVersion.setText("V"+getPackageInfo());

    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        tvParent.setText(getString(R.string.my));
        tvLogout.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        llCleanCache.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
        tvVersion.setText("V"+getPackageInfo());
        mPresenter.calculateCacheSize();
    }

    @Override
    public void bindEditionData(Edition data) {
        if(data == null){
            return;
        }
        mEdition = data;
        int currentCode = DeviceUtils.getVersionCode(this);
        if(data.getVersion() > currentCode){
            tvVersion.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
            tvVersion.setText("有新版本");
            llVersion.setOnClickListener(this);
        }
    }



    //获得版本号
    private String getPackageInfo(){
        String version = "";
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_logout:
                mPresenter.logout();
                break;
            case R.id.tv_feedback_item:
                ArmsUtils.startActivity(FeedbackActivity.class);
                break;
            case R.id.tv_about:
                ArmsUtils.startActivity(AboutActivity.class);
                break;
            case R.id.ll_clean_cache:
                showCleanDialog();
                break;
            case R.id.ll_version:
                showUpdateDialog();
                break;
        }
    }

    /**
     *
     */
    private void showUpdateDialog(){
        if(mUpdateDialog == null){
            View view = getLayoutInflater().inflate(R.layout.view_update, null);
            TextView tvContent = view.findViewById(R.id.tv_content);
            tvContent.setText(mEdition.getContent());
            mUpdateDialog = new CommonDialogs(this)
                    .setView(view)
                    .setTitle("升级提示")
                    .setNegativeButtonColor(R.color.blue)
                    .setPositiveButton(getString(R.string.sure),updateListener)
                    .setNegativeButton(getString(R.string.cancel),null)
                    .builder();
        }
        mUpdateDialog.show();
    }

    private View.OnClickListener updateListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            if(mEdition != null && !TextUtils.isEmpty(mEdition.getUrl())){
                Uri uri = Uri.parse(mEdition.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }
    };

    @Override
    public void setStorageSize(String size) {
        tvLocalStore.setText(size);
    }

    @Override
    public void setCacheSize(long size) {
        float sizeM = size/1024.f/1024.f;
        tvCacheSize.setText(String.format("%.2fM",sizeM));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if(mUpdateDialog != null && mUpdateDialog.isShowing()){
            mUpdateDialog.dismiss();
        }
        if(progressHUD != null){
            progressHUD.dismiss();
        }
    }

    //退出答卷对话框
    public void showCleanDialog(){
        mDialog = new CommonDialogs(this)
                .setTitle(R.string.notice)
                .setMessage(R.string.notice_clean_cache)
                .setNegativeButtonColor(R.color.blue)
                .setPositiveButton(getString(R.string.sure),cleanListener)
                .setNegativeButton(getString(R.string.cancel),null)
                .builder();
        mDialog.show();
    }

    private View.OnClickListener cleanListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            mPresenter.cleanCachSize();
        }
    };
}
