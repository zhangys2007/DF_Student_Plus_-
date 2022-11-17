package com.example.df.zhiyun.preview.mvp.ui.fragment;

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
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.mvp.contract.INavigation;
import com.example.df.zhiyun.mvp.contract.IQuestionLookup;
import com.example.df.zhiyun.preview.di.component.DaggerPrevHWselComponent;
import com.example.df.zhiyun.preview.mvp.contract.PrevHWselContract;
import com.example.df.zhiyun.preview.mvp.contract.PreviewHomeworkContract;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.preview.mvp.presenter.PrevHWselPresenter;
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
 * Description: 作业选择题预览
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 15:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PrevHWselFragment extends BaseFragment<PrevHWselPresenter> implements PrevHWselContract.View, IQuestionLookup {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_name)
    HtmlTextView tvName;
    @BindView(R.id.tv_steam)
    TextView tvSteam;

    @Inject
    BaseQuickAdapter mAdapter;
//    @Inject
//    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    public static PrevHWselFragment newInstance(int index, int total, int nvType) {
        PrevHWselFragment fragment = new PrevHWselFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        bundle.putInt("total",total);
        bundle.putInt(ViewLastNextInitHelper.KEY_NAVIGATION,nvType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getQuestionIndex() {
        return getArguments().getInt("index");
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPrevHWselComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hwsel, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        Question data = QuestionAnswerHolder.getInstance().getQuestion(this);
        if(data == null){
            return;
        }
        initRecyclerView();
        bindData(data);
        initAnalyData(data);

        if(getParentFragment() == null){  //没被嵌套，不是分屏里的小题
            new ViewLastNextInitHelper().init(this,(INavigation)getActivity(),
                    getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION),true);
        }else{
            new ViewLastNextInitHelper().init(this,(INavigation)getParentFragment(),
                    getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION),true);
        }
    }

    private void initAnalyData(Question data){
        View llAnaly = getView().findViewById(R.id.ll_pre_hw);
        llAnaly.setVisibility(View.VISIBLE);
        HtmlTextView tvAnswer = llAnaly.findViewById(R.id.tv_answer);
        HtmlTextView tvAnaly = llAnaly.findViewById(R.id.tv_analy);
        RichTextViewHelper.setContent(tvAnswer,data.getAnswer());
        RichTextViewHelper.setContent(tvAnaly,data.getAnalysis());

        Activity activity = getActivity();
        if(activity != null && ((PreviewHomeworkContract.View)activity).isHistoryPaper()){
            TextView tvMyTitle = llAnaly.findViewById(R.id.tv_my_title);
            HtmlTextView tvMyAnswer = llAnaly.findViewById(R.id.tv_my_answer);
            tvMyTitle.setVisibility(View.VISIBLE);
            tvMyAnswer.setVisibility(View.VISIBLE);
            if(TextUtils.isEmpty(data.getStudentAnswer())){
                RichTextViewHelper.setContent(tvMyAnswer,"未作答");
            }else{
                RichTextViewHelper.setContent(tvMyAnswer,data.getStudentAnswer());
            }
        }
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(itemClickListener);
    }

    private BaseQuickAdapter.OnItemClickListener itemClickListener = new BaseQuickAdapter.OnItemClickListener(){
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//            ((HWselAdapter)mAdapter).setSelect(position);
//            ((SetHomeworkContract.View)getActivity()).nextQuestion();
        }
    };


    @Override
    public void bindData(Question data) {
        if(TextUtils.isEmpty(data.getQuestionStem())){
            tvSteam.setVisibility(View.GONE);
        }else{
            tvSteam.setVisibility(View.VISIBLE);
            tvSteam.setText(RichTextViewHelper.ToDBC(data.getQuestionStem()));
        }
        TypefaceHolder.getInstance().setHt(tvName);
        RichTextViewHelper.setContent(tvName,RichTextViewHelper.makeQuestionTitle(data.getQuestionNum(),data.getContent()));
        mAdapter.setNewData(data.getOptionList());
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
