package com.example.df.zhiyun.mvp.ui.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.df.zhiyun.BuildConfig;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.contract.IBaseWeb;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;

import butterknife.BindView;
import timber.log.Timber;

public abstract class BaseWebActivity<P extends IPresenter> extends BaseStatusActivity<P> implements
        SwipeRefreshLayout.OnRefreshListener, IBaseWeb {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pb)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.base_activity_web;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initRefreshLayout();
        initWebView();
        costumSetting(savedInstanceState);
    }


    private void initRefreshLayout(){
        swipeRefreshLayout.setEnabled(false);
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setEnabled(true);
//        swipeRefreshLayout.setColorSchemeResources(
//                android.R.color.holo_blue_light,
//                android.R.color.holo_red_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_green_light);
    }

    @Override
    public void onRefresh() {
        Timber.tag("baseweb").d(getUrl());
        webView.clearHistory();
        webView.loadUrl(getUrl());
        webView.loadUrl( "javascript:window.location.reload( true )");
    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d("web","progress:" + newProgress);
                if(progressBar == null){
                    return;
                }
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(R.string.notice);
                builder.setPositiveButton(R.string.sure,null);
                builder.setMessage(message);
                builder.create().show();
                result.confirm();
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(Build.VERSION.SDK_INT<26) {
                    view.loadUrl(url);
                    return true;
                }
                return false;
            }
        });
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        setWebViewDebug(BuildConfig.DEBUG);

        setWebView(webView);
    }

    @TargetApi(19)
    private void setWebViewDebug(boolean enable){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            webView.setWebContentsDebuggingEnabled(enable);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            Timber.tag("baseweb").d("can go back");
            webView.goBack();
        } else {
            Timber.tag("baseweb").d("can not go back");
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();
        }
    }
}
