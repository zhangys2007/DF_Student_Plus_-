package com.example.df.zhiyun.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.contract.HomeworkListContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.presenter.HomeworkListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

import com.example.df.zhiyun.di.component.DaggerHomeworkListComponent;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.ui.fragment.HomeworkNewFragment;
import com.example.df.zhiyun.mvp.ui.fragment.HomeworkOldFragment;
import com.example.df.zhiyun.mvp.ui.widget.SegmentControlView;
import com.example.df.zhiyun.mvp.ui.widget.ViewSubjDateRecyclerHelper;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 作业列表页面 内有最新和历次作业子页面
 * <p>
 * Created by MVPArmsTemplate on 07/20/2019 19:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeworkListActivity extends BaseStatusActivity<HomeworkListPresenter> implements HomeworkListContract.View
    , View.OnClickListener, ViewSubjDateRecyclerHelper.SubjDateInterface{
    public static final String TYPE_KEY = "type";
    public static final String TYPE_NAME = "name";

    @BindArray(R.array.subject_colors)
    int[] subjectColors;

    @BindView(R.id.segmentView)
    SegmentControlView segmentcontrolview;
    @BindView(R.id.vp_homework)
    ViewPager viewPager;

    //标题栏
    @BindView(R.id.tv_search_homework)
    TextView tvSearch;
    @BindView(R.id.title)
    TextView tvTitle;


    List<Fragment> fragmentList = new ArrayList<>();
    String[] titles = {"最新作业", "历次作业"};
    AdapterViewPager adapterViewPager;
    boolean fragmentNewRefresh;  //
    boolean fragmentOldRefresh;  //

    private String mSubjectId = "";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeworkListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_homework_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        int subjectType = getIntent().getIntExtra(TYPE_KEY,0);
//        String subjectName = getIntent().getStringExtra(TYPE_NAME);
//        initSubjectTheme(subjectType,subjectName);
//        initViewPager(subjectType);
//
//        tvSearch.setOnClickListener(this);
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        int subjectType = getIntent().getIntExtra(TYPE_KEY,0);
        mSubjectId = Integer.toString(subjectType);
        String subjectName = getIntent().getStringExtra(TYPE_NAME);
        initSubjectTheme(subjectType,subjectName);
        initViewPager(subjectType);

        tvSearch.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_search_homework:
                SearchActivity.startSearchPage(this, Api.SEARCH_HOMEWORK,mSubjectId);
                break;
            default:
                break;
        }
    }

    /***
     * 根据科目不同，初始化不同的颜色，标题
     */
    /***
     * 根据科目不同，初始化不同的颜色，标题风格
     */
    void initSubjectTheme(int type, String subjName){
        tvTitle.setText(subjName);
        tvSearch.setText(subjName);
        int colorWhite = ContextCompat.getColor(this,R.color.white);

        int colorIndex = ViewSubjDateRecyclerHelper.getPreSetSubjResourceIndex(type);
        segmentcontrolview.setFrameColor(subjectColors[colorIndex]);
        segmentcontrolview.setTextColor(subjectColors[colorIndex],colorWhite);
        segmentcontrolview.setBackgroundColor(colorWhite,subjectColors[colorIndex]);
    }

    void initViewPager(int subjectId){
        fragmentList.clear();
        fragmentList.add(HomeworkNewFragment.newInstance(subjectId));
        fragmentList.add(HomeworkOldFragment.newInstance(subjectId));

        if (adapterViewPager == null) {
            adapterViewPager = new AdapterViewPager(getSupportFragmentManager(), fragmentList, titles);
        }
        viewPager.setAdapter(adapterViewPager);
        viewPager.setOffscreenPageLimit(3);

        segmentcontrolview.setOnSegmentChangedListener(new SegmentControlView.OnSegmentChangedListener() {
            @Override
            public void onSegmentChanged(int newSelectedIndex) {
                if(viewPager != null){
                    viewPager.setCurrentItem(newSelectedIndex, false);
                    refreshFragmentPending();
                }
            }
        });
        segmentcontrolview.setViewPager(viewPager);
        segmentcontrolview.setSelectedIndex(0);
        segmentcontrolview.setGradient(true);
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

    public static void launchActivity(Context context,int type){
        Intent intent = new Intent(context,HomeworkListActivity.class);
        intent.putExtra(HomeworkListActivity.TYPE_KEY, type);
        String[] title = context.getResources().getStringArray(R.array.array_homework_tags);
        intent.putExtra(HomeworkListActivity.TYPE_NAME,
                title[ViewSubjDateRecyclerHelper.getPreSetSubjResourceIndex(type)]);
        ArmsUtils.startActivity(intent);
    }


    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public Context getPageContext() {
        return this;
    }


    //对两个fragment状态进行同步
    @Override
    public void onSubjectChange(String subjId, String subName) {
        mSubjectId = subjId;
        initSubjectTheme(Integer.parseInt(subjId),subName);
        ((ViewSubjDateRecyclerHelper.SubjDateSyncInterface)fragmentList.get(0)).syncSubject(subjId,subName);
        ((ViewSubjDateRecyclerHelper.SubjDateSyncInterface)fragmentList.get(1)).syncSubject(subjId,subName);
        refreshFragmentData();
    }

    @Override
    public void onTimeChange(String start, String end) {
        ((ViewSubjDateRecyclerHelper.SubjDateSyncInterface)fragmentList.get(0)).syncTime(start,end);
        ((ViewSubjDateRecyclerHelper.SubjDateSyncInterface)fragmentList.get(1)).syncTime(start,end);
        refreshFragmentData();

    }

    //只刷新看得见的fragment，另一个到切换选项卡的时候再刷新,
    private void refreshFragmentData(){
        fragmentNewRefresh = true;
        fragmentOldRefresh = true;
        if(viewPager.getCurrentItem() == 0){
            fragmentNewRefresh = false;
            ((HomeworkNewFragment)fragmentList.get(0)).onRefresh();
        }
        if(viewPager.getCurrentItem() == 1 ){
            fragmentOldRefresh = false;
            ((HomeworkOldFragment)fragmentList.get(1)).onRefresh();
        }
    }

    //
    private void refreshFragmentPending(){
        if(fragmentNewRefresh && viewPager.getCurrentItem() == 0){
            fragmentNewRefresh = false;
            ((HomeworkNewFragment)fragmentList.get(0)).onRefresh();
        }
        if(fragmentOldRefresh && viewPager.getCurrentItem() == 1 ){
            fragmentOldRefresh = false;
            ((HomeworkOldFragment)fragmentList.get(1)).onRefresh();
        }
    }
}
