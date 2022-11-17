package com.example.df.zhiyun.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.app.StatusHolder;
import com.example.df.zhiyun.login.mvp.ui.activity.GuideActivity;
import com.example.df.zhiyun.login.mvp.ui.activity.IndexActivity;
import com.example.df.zhiyun.login.mvp.ui.activity.LoginActivity;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;

import timber.log.Timber;

/**
 * 查看是否被回收过，回收过就跳转到indexpage
 * @param <P>
 */
public abstract class BaseStatusActivity<P extends IPresenter> extends BaseActivity<P> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = this;
        if (activity instanceof IndexActivity
                || activity instanceof LoginActivity
                || activity instanceof GuideActivity) {
            StatusHolder.getInstance().setKill(false);
            initAll(savedInstanceState);
        }else {
            if(AccountManager.getInstance().getUserInfo() == null) {
//                Timber.tag(TAG).e("app was kill");
//                Intent intent = new Intent(this, IndexActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
                return;
            }else {
                Timber.tag(TAG).e("app was normal");
                initAll(savedInstanceState);
            }
        }
    }

    public abstract void initAll(Bundle savedInstanceState);
}
