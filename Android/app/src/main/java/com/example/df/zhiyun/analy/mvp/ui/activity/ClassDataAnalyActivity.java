package com.example.df.zhiyun.analy.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.di.component.DaggerClassDataAnalyComponent;
import com.example.df.zhiyun.analy.mvp.contract.ClassDataAnalyContract;
import com.example.df.zhiyun.analy.mvp.presenter.ClassDataAnalyPresenter;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.analy.mvp.ui.fragment.CompearClassFragment;
import com.example.df.zhiyun.analy.mvp.ui.fragment.KnowledgeGraspFragment;
import com.example.df.zhiyun.analy.mvp.ui.fragment.MeasureSumaryFragment;
import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import me.jessyan.autosize.internal.CustomAdapt;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:班级数据分析
 * <p>
 * Created by MVPArmsTemplate on 10/29/2019 14:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ClassDataAnalyActivity extends BaseStatusActivity<ClassDataAnalyPresenter> implements ClassDataAnalyContract.View, CustomAdapt {
    @BindView(R.id.tl_main)
    TabLayout mTabLayout;
    @BindView(R.id.vp_main)
    ViewPager viewPager;

    List<Fragment> fragmentList = new ArrayList<>();
    private String [] mTitles = {"评测概览", "知识点掌握情况", "班级成绩对比"};
    AdapterViewPager adapterViewPager;

    @Inject
    @Named(ClassDataAnalyContract.View.KEY_FZ)
    Integer mFz;
    @Inject
    @Named(ClassDataAnalyContract.View.KEY_TYPE)
    Integer mType;
    @Inject
    @Named(ClassDataAnalyContract.View.KEY_SCHOOLID)
    Integer mSchoolId;
    @Inject
    @Named(ClassDataAnalyContract.View.KEY_GRADEID)
    Integer mGradeId;
    @Inject
    @Named(ClassDataAnalyContract.View.KEY_HOMEWORK_ID)
    String mHomeworkId;

    @Inject
    @Named(ClassDataAnalyContract.View.KEY_SUBJID)
    Integer mSubjId;

    public static void launcheActivity(Context context,String homeworkId, int fzPaperId, int type, int schoolId, int gradeId, int subjId){
        Intent intent = new Intent(context, ClassDataAnalyActivity.class);
        intent.putExtra(ClassDataAnalyContract.View.KEY_HOMEWORK_ID,homeworkId);
        intent.putExtra(ClassDataAnalyContract.View.KEY_FZ,fzPaperId);
        intent.putExtra(ClassDataAnalyContract.View.KEY_TYPE,type);
        intent.putExtra(ClassDataAnalyContract.View.KEY_SCHOOLID,schoolId);
        intent.putExtra(ClassDataAnalyContract.View.KEY_GRADEID,gradeId);
        intent.putExtra(ClassDataAnalyContract.View.KEY_SUBJID,subjId);
        ArmsUtils.startActivity(intent);
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerClassDataAnalyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_class_data_analy; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) { }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        addTabToTabLayout();
        setupWithViewPager();
    }

    private void addTabToTabLayout() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitles[i]));
        }
    }

    private void setupWithViewPager() {
        fragmentList.clear();
        fragmentList.add(MeasureSumaryFragment.newInstance(mFz,mGradeId,mHomeworkId,mSchoolId,mSubjId,mType));
        fragmentList.add(KnowledgeGraspFragment.newInstance(mFz,mGradeId,mHomeworkId,mSchoolId,mSubjId,mType));
        fragmentList.add(CompearClassFragment.newInstance(mFz,mGradeId,mHomeworkId,mSchoolId,mSubjId,mType));

        if (adapterViewPager == null) {
            adapterViewPager = new AdapterViewPager(getSupportFragmentManager(), fragmentList, mTitles);
        }
        viewPager.setAdapter(adapterViewPager);
        viewPager.setOffscreenPageLimit(3);

        mTabLayout.setupWithViewPager(viewPager); //关联TabLayout和ViewPager
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }


    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }
}
