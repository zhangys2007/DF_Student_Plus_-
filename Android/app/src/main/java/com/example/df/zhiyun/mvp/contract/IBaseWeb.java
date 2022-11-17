package com.example.df.zhiyun.mvp.contract;

import android.os.Bundle;
import android.webkit.WebView;

public interface IBaseWeb {
    String getUrl();
    void setWebView(WebView webView);
    void costumSetting(Bundle savedInstanceState);
}
