package com.example.df.zhiyun.analy.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.di.component.DaggerCompearClassComponent;
import com.example.df.zhiyun.analy.mvp.contract.CompearClassContract;
import com.example.df.zhiyun.analy.mvp.presenter.CompearClassPresenter;
import com.example.df.zhiyun.mvp.ui.fragment.QuestionCompearFragment;
import com.example.df.zhiyun.mvp.ui.fragment.WholeCompearFragment;
import com.example.df.zhiyun.mvp.ui.widget.SegmentControlView;
import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * ================================================
 * Description: 班级成绩对比
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 16:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CompearClassFragment extends BaseFragment<CompearClassPresenter> implements CompearClassContract.View{
    @Inject
    @Named(CompearClassContract.View.KEY_FZ)
    Integer mFz;
    @Inject
    @Named(CompearClassContract.View.KEY_TYPE)
    Integer mType;
    @Inject
    @Named(CompearClassContract.View.KEY_SCHOOLID)
    Integer mSchoolId;
    @Inject
    @Named(CompearClassContract.View.KEY_GRADEID)
    Integer mGradeId;
    @Inject
    @Named(CompearClassContract.View.KEY_HOMEWORK_ID)
    String mHomeworkId;
    @Inject
    @Named(CompearClassContract.View.KEY_SUBJID)
    Integer mSubjId;

    @BindView(R.id.vp_main)
    ViewPager viewPager;
    @BindView(R.id.segmentView)
    SegmentControlView segmentcontrolview;
    @BindArray(R.array.compear_class)
    String[] titles;

    AdapterViewPager adapterViewPager;
    List<Fragment> fragmentList = new ArrayList<>();

    public static CompearClassFragment newInstance(int fzId, int gradeId, String hwId, int schoolId, int subjId, int type) {
        CompearClassFragment fragment = new CompearClassFragment();
        Bundle data = new Bundle();
        data.putInt(CompearClassContract.View.KEY_FZ,fzId);
        data.putInt(CompearClassContract.View.KEY_GRADEID,gradeId);
        data.putString(CompearClassContract.View.KEY_HOMEWORK_ID,hwId);
        data.putInt(CompearClassContract.View.KEY_SCHOOLID,schoolId);
        data.putInt(CompearClassContract.View.KEY_SUBJID,subjId);
        data.putInt(CompearClassContract.View.KEY_TYPE,type);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCompearClassComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compear_class, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViewPager();
    }

    public void initViewPager() {
        fragmentList.clear();
        fragmentList.add(WholeCompearFragment.newInstance(mFz,mGradeId,mHomeworkId,mSchoolId,mSubjId,mType));
        fragmentList.add(QuestionCompearFragment.newInstance(mFz,mGradeId,mHomeworkId,mSchoolId,mSubjId,mType));
        fragmentList.add(KnowledgeCompearFragment.newInstance(mFz,mGradeId,mHomeworkId,mSchoolId,mSubjId,mType));

        if (adapterViewPager == null) {
            adapterViewPager = new AdapterViewPager(getChildFragmentManager(), fragmentList, titles);
        }
        viewPager.setAdapter(adapterViewPager);
        viewPager.setOffscreenPageLimit(2);

        segmentcontrolview.setOnSegmentChangedListener(new SegmentControlView.OnSegmentChangedListener() {
            @Override
            public void onSegmentChanged(int newSelectedIndex) {
                if(viewPager != null){
                    viewPager.setCurrentItem(newSelectedIndex, false);
                }
            }
        });
        segmentcontrolview.setViewPager(viewPager);
        segmentcontrolview.setSelectedIndex(0);
        segmentcontrolview.setGradient(true);
    }

    @Override
    public void setData(@Nullable Object data) {

    }
}
