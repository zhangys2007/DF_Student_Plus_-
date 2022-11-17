package com.example.df.zhiyun.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.contract.EQD_selContract;
import com.example.df.zhiyun.mvp.model.entity.ErrorDetail;
import com.example.df.zhiyun.mvp.presenter.EQD_selPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import com.example.df.zhiyun.di.component.DaggerEQD_selComponent;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.ui.adapter.EQD_selAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:错题详情里的选择题
 * <p>
 * Created by MVPArmsTemplate on 08/02/2019 13:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class EQD_selFragment extends BaseFragment<EQD_selPresenter> implements EQD_selContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_name)
    HtmlTextView tvName;
    @BindView(R.id.tv_check_point)
    HtmlTextView tvCheckPoint;
    @BindView(R.id.tv_answer)
    HtmlTextView tvAnswer;
    @BindView(R.id.tv_analy)
    HtmlTextView tvAnaly;
    @BindView(R.id.tv_title_myanswer)
    TextView tvTitleMy;
    @BindView(R.id.tv_my_answer)
    HtmlTextView tvMyAnswer;
    @BindView(R.id.v_my_line)
    View vLine;

    @Inject
    BaseQuickAdapter mAdapter;
//    @Inject
//    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    public static EQD_selFragment newInstance(ErrorDetail data,boolean hideMyAnswer) {
        EQD_selFragment fragment = new EQD_selFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data",data);
        bundle.putBoolean(EQD_selContract.View.KEY_HIDE,hideMyAnswer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerEQD_selComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_eqd_sel, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        bindData(getArguments().getParcelable("data"),
                getArguments().getBoolean(EQD_selContract.View.KEY_HIDE,false));
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);
    }


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
    public void bindData(ErrorDetail data,boolean hide) {
        RichTextViewHelper.setContent(tvName,data.getContent());
        RichTextViewHelper.setContent(tvCheckPoint,data.getExaminationPoint());
        RichTextViewHelper.setContent(tvAnswer,data.getAnswer());
        RichTextViewHelper.setContent(tvAnaly,data.getAnalysis());
        RichTextViewHelper.setContent(tvMyAnswer, TextUtils.isEmpty(data.getStudentAnswer())?
                "未作答":data.getStudentAnswer());  // TODO: 2019-09-21

        ((EQD_selAdapter)mAdapter).setSelect(data.getAnswer(),data.getStudentAnswer());
        mAdapter.setNewData(data.getOptionList());

        if(hide){
            tvTitleMy.setVisibility(View.GONE);
            tvMyAnswer.setVisibility(View.GONE);
            vLine.setVisibility(View.GONE);
        }
    }
}
