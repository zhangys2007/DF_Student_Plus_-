package com.example.df.zhiyun.login.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.login.di.component.DaggerLoginComponent;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.jaeger.library.StatusBarUtil;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.login.mvp.contract.LoginContract;
import com.example.df.zhiyun.login.mvp.presenter.LoginPresenter;
import com.jess.arms.utils.DeviceUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import javax.inject.Inject;

import butterknife.BindView;

import com.example.df.zhiyun.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/19/2019 12:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends BaseStatusActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_psd)
    EditText etPsw;
    @BindView(R.id.fl_login)
    FrameLayout flLogin;
    @BindView(R.id.ll_account)
    LinearLayout llAccount;
    @BindView(R.id.ll_psw)
    LinearLayout llPsw;

    @Inject
    AppManager mAppManager;

    @Inject
    KProgressHUD progressHUD;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        StatusBarUtil.setTranslucentForImageViewInFragment(this, Color.TRANSPARENT ,null);  //透明状态栏
//
//        flLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                ArmsUtils.startActivity(ProfileActivity.class);
//                hideSoftInputMethod();
//                mPresenter.login(etAccount.getEditableText().toString(),
//                        etPsw.getEditableText().toString());
//            }
//        });
//        llAccount.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                etAccount.requestFocus();
//                DeviceUtils.showSoftKeyboard(view.getContext(),etAccount);
//            }
//        });
//        llPsw.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                etPsw.requestFocus();
//                DeviceUtils.showSoftKeyboard(view.getContext(),etPsw);
//            }
//        });
//
//        etAccount.setText(AccountManager.getInstance().getLastAccount(this));
//        etPsw.setImeOptions(EditorInfo.IME_ACTION_GO);
//        etPsw.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            public boolean onEditorAction(TextView v, int actionId,                   KeyEvent event)  {
//                if (actionId==EditorInfo.IME_ACTION_GO ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
//                {
//                    hideSoftInputMethod();
//                    mPresenter.login(etAccount.getEditableText().toString(),
//                            etPsw.getEditableText().toString());
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, Color.TRANSPARENT ,null);  //透明状态栏

        flLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ArmsUtils.startActivity(ProfileActivity.class);
                hideSoftInputMethod();
                mPresenter.login(etAccount.getEditableText().toString(),
                        etPsw.getEditableText().toString());
            }
        });
        llAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                etAccount.requestFocus();
                DeviceUtils.showSoftKeyboard(view.getContext(),etAccount);
            }
        });
        llPsw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                etPsw.requestFocus();
                DeviceUtils.showSoftKeyboard(view.getContext(),etPsw);
            }
        });

        etAccount.setText(AccountManager.getInstance().getLastAccount(this));
        etPsw.setImeOptions(EditorInfo.IME_ACTION_GO);
        etPsw.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,                   KeyEvent event)  {
                if (actionId==EditorInfo.IME_ACTION_GO ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
                    hideSoftInputMethod();
                    mPresenter.login(etAccount.getEditableText().toString(),
                            etPsw.getEditableText().toString());
                    return true;
                }
                return false;
            }
        });
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
    public void loginOk() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressHUD != null){
            progressHUD.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            mPresenter.clickBack();
            return true;
        }
        return false;
    }

    private void hideSoftInputMethod(){
        DeviceUtils.hideSoftKeyboard(this,etAccount);
        DeviceUtils.hideSoftKeyboard(this,etPsw);
    }
}
