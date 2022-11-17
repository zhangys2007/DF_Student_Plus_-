package com.example.df.zhiyun.analy.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.di.component.DaggerKnowledgeScoreAnalyComponent;
import com.example.df.zhiyun.analy.mvp.contract.KnowledgeScoreAnalyContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.QuestionCompearMultipleItem;
import com.example.df.zhiyun.analy.mvp.presenter.KnowledgeScoreAnalyPresenter;
import com.example.df.zhiyun.mvp.ui.widget.QuestionCompearSpanLookup;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.autosize.internal.CustomAdapt;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 班级成绩对比--知识点得分分析
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class KnowledgeScoreAnalyFragment extends BaseFragment<KnowledgeScoreAnalyPresenter> implements KnowledgeScoreAnalyContract.View, CustomAdapt {
    @BindView(R.id.recyclerView_compear)
    RecyclerView recyclerView;

    @Inject
    BaseMultiItemQuickAdapter mAdapter;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;


    private LayoutInflater mLayoutInflater;

    public static KnowledgeScoreAnalyFragment newInstance(int fzId, int gradeId, int schoolId, int subjId, int type) {
        KnowledgeScoreAnalyFragment fragment = new KnowledgeScoreAnalyFragment();
        Bundle data = new Bundle();
        data.putInt(KnowledgeScoreAnalyContract.View.KEY_FZ,fzId);
        data.putInt(KnowledgeScoreAnalyContract.View.KEY_GRADEID,gradeId);
//        data.putString(KnowledgeScoreAnalyContract.View.KEY_HOMEWORK_ID,hwId);
        data.putInt(KnowledgeScoreAnalyContract.View.KEY_SCHOOLID,schoolId);
        data.putInt(KnowledgeScoreAnalyContract.View.KEY_SUBJID,subjId);
        data.putInt(KnowledgeScoreAnalyContract.View.KEY_TYPE,type);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerKnowledgeScoreAnalyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_compear, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getData();
        mLayoutInflater = LayoutInflater.from(getContext());
    }


    @Override
    public void bindCompearData(List<QuestionCompearMultipleItem> list, int collumCount) {
        int width1 = ArmsUtils.dip2px(getContext(),120);

        mAdapter.setSpanSizeLookup(new QuestionCompearSpanLookup(list));

        RecyclerView.LayoutManager  manager = new GridLayoutManager(getContext(), collumCount);
        recyclerView.setLayoutManager(manager);
//        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setNewData(list);

        HorizontalScrollView.LayoutParams params = (HorizontalScrollView.LayoutParams)recyclerView.getLayoutParams();
        params.width = width1*collumCount;
        recyclerView.setLayoutParams(params);
    }

    @Override
    public void setData(@Nullable Object data) {

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
