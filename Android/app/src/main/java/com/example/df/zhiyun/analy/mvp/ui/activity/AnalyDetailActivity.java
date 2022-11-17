package com.example.df.zhiyun.analy.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.webkit.WebView;

import com.example.df.zhiyun.analy.di.component.DaggerAnalyDetailComponent;
import com.example.df.zhiyun.mvp.ui.activity.BaseWebActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.analy.mvp.contract.AnalyDetailContract;
import com.example.df.zhiyun.analy.mvp.presenter.AnalyDetailPresenter;


import javax.inject.Inject;
import javax.inject.Named;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 14:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AnalyDetailActivity extends BaseWebActivity<AnalyDetailPresenter> implements AnalyDetailContract.View {
    @Inject
    @Named(AnalyDetailContract.View.KEY_NAME)
    String mName;

    @Inject
    @Named(AnalyDetailContract.View.KEY_URL)
    String mUrl;

    public static void launcheDetailActivity(Context context, String name, String url){
        Intent intent = new Intent(context, AnalyDetailActivity.class);
        intent.putExtra(AnalyDetailContract.View.KEY_NAME,name);
        intent.putExtra(AnalyDetailContract.View.KEY_URL,url);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAnalyDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public void setWebView(WebView webView) {

    }

    @Override
    public void costumSetting(Bundle savedInstanceState) {
        setTitle(mName);
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
