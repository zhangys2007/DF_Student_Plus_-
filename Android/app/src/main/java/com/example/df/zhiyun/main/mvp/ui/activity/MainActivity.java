package com.example.df.zhiyun.main.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.jaeger.library.StatusBarUtil;
import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.main.mvp.contract.MainContract;
import com.example.df.zhiyun.main.mvp.presenter.MainPresenter;

import butterknife.BindView;
import com.example.df.zhiyun.main.di.component.DaggerMainComponent;

import java.util.ArrayList;
import java.util.List;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.ui.fragment.ErrorExerFragment;
import com.example.df.zhiyun.mvp.ui.fragment.HomeworkFragment;
import com.example.df.zhiyun.main.mvp.ui.fragment.MainFragmentFragment;
import com.example.df.zhiyun.my.mvp.ui.fragment.MyFragment;

import org.simple.eventbus.Subscriber;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:主页
 * <p>
 * Created by MVPArmsTemplate on 07/17/2019 10:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends BaseStatusActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.main_content)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationViewEx navigationBar;
    @BindView(R.id.fab_ban)
    CardView fabBan;

    QBadgeView qBadgeView;

    List<Fragment> fragmentList = new ArrayList<>();

    String[] titles = {"首页", "作业", " ","错题本","我的"};
    AdapterViewPager adapterViewPager;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {}

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initNavigationBar();
        initViewPager();
        initOtherViews();
//        BarTextColorUtil.StatusBarLightMode(this);  //状态栏文字颜色
//        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this,
//                R.color.red));
        StatusBarUtil.setTranslucentForImageView(this, Color.TRANSPARENT ,null);
        mPresenter.requestData();
    }

    @Override
    public void switchTab(int table) {
        if(table>=0 && table <fragmentList.size()){
            viewPager.setCurrentItem(table);
        }

    }

    private void addBadgeAt(int position, int number) {
        // add badge
        if(qBadgeView == null){
            qBadgeView =  new QBadgeView(this);
            qBadgeView.setBadgeNumber(number)
                    .setGravityOffset(12, 2, true)
                    .bindTarget(navigationBar.getBottomNavigationItemView(position))
                    .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                        @Override
                        public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                            if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState){

                            }
                        }
                    });
        }else{
            qBadgeView.setBadgeNumber(number);
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

    /***
     * 初始化底部导航栏
     */
    void initNavigationBar(){
        navigationBar.enableItemShiftingMode(false);
        navigationBar.enableShiftingMode(false);
        navigationBar.enableAnimation(false);
        navigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = 0;
                switch (item.getItemId()) {
                    case R.id.i_main:
                        position = 0;
                        break;
                    case R.id.i_homework:
                        position = 1;
                        break;
                    case R.id.i_excer:
                        position = 2;
                        break;
                    case R.id.i_my:
                        position = 3;
                        break;
                    case R.id.i_empty: {
                        return false;
                    }
                }
                if(previousPosition != position) {
                    viewPager.setCurrentItem(position, false);
                    previousPosition = position;
                }

                return true;
            }
        });
    }

    @Override
    public void setMsgCount(int count) {
        addBadgeAt(4,count);
    }

    /**
     * 中间内容区，滚动控件的初始化
     */
    void initViewPager(){
        fragmentList.clear();
        fragmentList.add(MainFragmentFragment.newInstance());
        fragmentList.add(HomeworkFragment.newInstance());
        fragmentList.add(ErrorExerFragment.newInstance());
        fragmentList.add(MyFragment.newInstance());

        if (adapterViewPager == null) {
            adapterViewPager = new AdapterViewPager(getSupportFragmentManager(), fragmentList, titles);
        }
        viewPager.setAdapter(adapterViewPager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(i >= 2){
                    navigationBar.setCurrentItem(i+1);
                }else{
                    navigationBar.setCurrentItem(i);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    /**
     * 界面里其它控件的初始化
     */
    private void initOtherViews() {
        fabBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.clickRefresh(viewPager.getCurrentItem());
            }
        });
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

    @Subscriber(tag = EventBusTags.REQUEST_PERSON_CENTER)
    private void requestPersonCenterInfo(Integer value){
        mPresenter.clickRefresh(viewPager.getCurrentItem());
    }

}
