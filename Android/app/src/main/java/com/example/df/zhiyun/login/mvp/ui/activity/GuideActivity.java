package com.example.df.zhiyun.login.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.df.zhiyun.login.di.component.DaggerGuideComponent;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.jaeger.library.StatusBarUtil;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.example.df.zhiyun.login.mvp.contract.GuideContract;
import com.example.df.zhiyun.login.mvp.presenter.GuidePresenter;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.ui.adapter.ViewPagerAdapter;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 11:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GuideActivity extends BaseStatusActivity<GuidePresenter> implements GuideContract.View {
    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGuideComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_guide; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        StatusBarUtil.setTranslucentForImageViewInFragment(this, Color.TRANSPARENT ,null);  //透明状态栏
//        initPagerView();
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, Color.TRANSPARENT ,null);  //透明状态栏
        initPagerView();
    }

    private void initPagerView(){
        List<View> viewpages = new ArrayList<View>();
        View page_1 = getLayoutInflater().inflate(R.layout.item_guide_normal, null);
        View page_2 = getLayoutInflater().inflate(R.layout.item_guide_normal, null);
        View page_3 = getLayoutInflater().inflate(R.layout.item_guide_withbtn, null);

        ImageView img = (ImageView)page_1.findViewById(R.id.img);
        img.setImageResource(R.mipmap.guide_1);
        img = (ImageView)page_2.findViewById(R.id.img);
        img.setImageResource(R.mipmap.guide_2);
        img = (ImageView)page_3.findViewById(R.id.img);
        img.setImageResource(R.mipmap.guide_3);

        page_3.findViewById(R.id.next).setOnClickListener(listener);

        viewpages.add(page_1);
        viewpages.add(page_2);
        viewpages.add(page_3);
        vp.setAdapter(new ViewPagerAdapter(viewpages));
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArmsUtils.startActivity(LoginActivity.class);
            killMyself();
        }
    };

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
