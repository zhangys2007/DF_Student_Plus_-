package com.example.df.zhiyun.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.contract.EQDetailContract;
import com.example.df.zhiyun.mvp.presenter.EQDetailPresenter;
import com.kaopiz.kprogresshud.KProgressHUD;

import javax.inject.Inject;

import butterknife.BindView;
import com.example.df.zhiyun.di.component.DaggerEQDetailComponent;

import com.example.df.zhiyun.R;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 错题详情
 * <p>
 * Created by MVPArmsTemplate on 08/02/2019 09:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class EQDetailActivity extends BaseStatusActivity<EQDetailPresenter> implements EQDetailContract.View {
    public static final int KEY_BIND_DATA = 88;

    @BindView(R.id.toolbar_left_title)
    TextView tvToolbarLeft;
    @BindView(R.id.toolbar_right_title)
    TextView tvToolbarRight;

    @Inject
    KProgressHUD progressHUD;
    private boolean isCollected;  //是否已经收藏
    Fragment mFragment;

    public static void startErrorDetailPage(Context context, String qeustionId, String subjId){
        Intent intent = new Intent(context, EQDetailActivity.class);
        intent.putExtra(EQDetailContract.View.KEY,qeustionId);
        intent.putExtra(EQDetailContract.View.KEY_SUBJID,subjId);
        ArmsUtils.startActivity(intent);
    }

    public static void startErrorDetailPage(Context context, String qeustionId, String subjId, boolean hideMyAnswer){
        Intent intent = new Intent(context, EQDetailActivity.class);
        intent.putExtra(EQDetailContract.View.KEY,qeustionId);
        intent.putExtra(EQDetailContract.View.KEY_SUBJID,subjId);
        intent.putExtra(EQDetailContract.View.KEY_HIDE_MY,hideMyAnswer);
        ArmsUtils.startActivity(intent);
    }



    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEQDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_eqdetail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        tvToolbarLeft.setText(R.string.error_exer);
//        Drawable drawable = ContextCompat.getDrawable(this,R.mipmap.start_un);
//        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
//        tvToolbarRight.setCompoundDrawables(null,null, drawable,null);
//
//        tvToolbarRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.updateCollection(!isCollected);
//            }
//        });
//        tvToolbarRight.setClickable(false);
//
//        if (savedInstanceState != null) {
//            mFragment = getSupportFragmentManager()
//                    .getFragment(savedInstanceState, "EQDFragment");
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fl_question, mFragment)
//                    .commit();
//        }
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
//        tvToolbarLeft.setText(R.string.error_exer);
        Drawable drawable = ContextCompat.getDrawable(this,R.mipmap.start_un);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        tvToolbarRight.setCompoundDrawables(null,null, drawable,null);

        tvToolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.updateCollection(!isCollected);
            }
        });
        tvToolbarRight.setClickable(false);

        if (savedInstanceState != null) {
            mFragment = getSupportFragmentManager()
                    .getFragment(savedInstanceState, "EQDFragment");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_question, mFragment)
                    .commit();
        }

        mPresenter.getDetail();
    }

    /**
     * 设置是否已经收藏
     * @param store
     */
    @Override
    public void setStore(boolean store) {
        tvToolbarRight.setClickable(true);
        isCollected = store;
        Drawable drawable = ContextCompat.getDrawable(this,store?R.mipmap.start_ok:R.mipmap.start_un);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        tvToolbarRight.setCompoundDrawables(null,null, drawable,null);
    }

    public void setFragment(Fragment fragment){
        Timber.tag(TAG).d("setFrame");
        mFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_question, mFragment)
                .commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "EQDFragment", mFragment);
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
}
