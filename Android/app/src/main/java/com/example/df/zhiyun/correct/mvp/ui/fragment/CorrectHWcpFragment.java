package com.example.df.zhiyun.correct.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.correct.di.component.DaggerCorrectHWcpComponent;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWContract;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWcpContract;
import com.example.df.zhiyun.mvp.contract.INavigation;
import com.example.df.zhiyun.mvp.contract.IQuestionLookup;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.correct.mvp.presenter.CorrectHWcpPresenter;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:  作业里的包含选择，填空题型
 * <p>
 * Created by MVPArmsTemplate on 08/10/2019 13:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CorrectHWcpFragment extends BaseFragment<CorrectHWcpPresenter> implements CorrectHWcpContract.View , IQuestionLookup {
    @BindView(R.id.tv_input_content)
    HtmlTextView tvContent;
    @BindView(R.id.recyclerView_cp)
    RecyclerView recyclerView;

    @BindView(R.id.tv_steam)
    TextView tvSteam;

    @Inject
    BaseQuickAdapter mAdapter;
//    @Inject
//    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    @Override
    public int getQuestionIndex() {
        return getArguments().getInt("index");
    }

    public static CorrectHWcpFragment newInstance(int index, int total, int nvType) {
        CorrectHWcpFragment fragment = new CorrectHWcpFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        bundle.putInt("total",total);
        bundle.putInt(ViewLastNextInitHelper.KEY_NAVIGATION,nvType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCorrectHWcpComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_correct_cp, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Question data = QuestionAnswerHolder.getInstance().getQuestion(this);
        if(data == null){
            return;
        }
        bindData(data);
        Activity activity = getActivity();
        boolean isPreview = AccountManager.getInstance().getUserInfo().getRoleId() != Api.TYPE_TEACHER ||
                ((CorrectHWContract.View)activity).isCorrected();
        if(getParentFragment() == null){  //没被嵌套，不是分屏里的小题
            new ViewLastNextInitHelper().init(this,(INavigation)getActivity(),getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION),isPreview);
        }else{
            new ViewLastNextInitHelper().init(this,(INavigation)getParentFragment(),getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION),isPreview);
        }
        initRecyclerView(data);
    }

    public void bindData(Question data) {
        if(TextUtils.isEmpty(data.getQuestionStem())){
            tvSteam.setVisibility(View.GONE);
        }else{
            tvSteam.setVisibility(View.VISIBLE);
            tvSteam.setText(RichTextViewHelper.ToDBC(data.getQuestionStem()));
        }
        TypefaceHolder.getInstance().setHt(tvContent);
        RichTextViewHelper.setContent(tvContent,RichTextViewHelper.makeQuestionTitle(data.getQuestionNum(),data.getContent()));
    }

    private void initRecyclerView(Question data){
        if(data.getSubQuestion() == null ){
            return;
        }
        Activity activity = getActivity();

        RecyclerView.LayoutManager  manager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(data.getSubQuestion());
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
}
