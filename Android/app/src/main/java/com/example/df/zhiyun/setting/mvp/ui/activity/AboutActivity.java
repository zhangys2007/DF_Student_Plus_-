package com.example.df.zhiyun.setting.mvp.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.mvp.ui.activity.FocusActivity;
import com.example.df.zhiyun.setting.di.component.DaggerAboutComponent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.setting.mvp.contract.AboutContract;
import com.example.df.zhiyun.setting.mvp.presenter.AboutPresenter;

import butterknife.BindView;

import com.example.df.zhiyun.R;
import com.jess.arms.utils.DeviceUtils;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 10:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AboutActivity extends BaseStatusActivity<AboutPresenter> implements AboutContract.View
    , View.OnClickListener{
    @BindView(R.id.tv_version_name)
    TextView tvVersion;
    @BindView(R.id.tv_function)
    TextView tvfunction;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_hot_line)
    TextView tvHotLine;
    @BindView(R.id.tv_focus)
    TextView tvFocus;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAboutComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_about; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) { }

    public void initAll(Bundle savedInstanceState) {
        tvfunction.setOnClickListener(this);
        tvCompany.setOnClickListener(this);
        tvHotLine.setOnClickListener(this);
        tvFocus.setOnClickListener(this);

        tvVersion.setText("版本"+getPackageInfo());
    }

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_function:
                ArmsUtils.startActivity(FunctionIntroduceActivity.class);
                break;
            case R.id.tv_company:
                ArmsUtils.startActivity(CompanyActivity.class);
                break;
            case R.id.tv_hot_line:
                DeviceUtils.openDial(this,"02161172408");
                break;
            case R.id.tv_focus:
                ArmsUtils.startActivity(FocusActivity.class);
                break;
        }
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
