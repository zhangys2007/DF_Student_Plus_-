package com.example.df.zhiyun.preview.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.mvp.contract.INavigation;
import com.example.df.zhiyun.mvp.contract.IQuestionLookup;
import com.example.df.zhiyun.preview.di.component.DaggerPrevHWsepComponent;
import com.example.df.zhiyun.preview.mvp.contract.PrevHWsepContract;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.preview.mvp.presenter.PrevHWsepPresenter;
import com.example.df.zhiyun.preview.mvp.ui.adapter.PreviewHomeworkAdapter;
import com.example.df.zhiyun.mvp.ui.widget.EmbedBottomSheetBehavior;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindString;
import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * ================================================
 * Description: 分屏大题
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 16:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PrevHWsepFragment extends BaseFragment<PrevHWsepPresenter> implements PrevHWsepContract.View,View.OnClickListener
    , INavigation , IQuestionLookup {
    @BindString(R.string.sub_title_question)
    String strSubTitle;
    @BindView(R.id.tv_sep_content)
    HtmlTextView tvContent;
    @BindView(R.id.ll_content_bottom_sheet)
    NestedScrollView llBottomSheet;
    @BindView(R.id.ib_close)
    ImageButton ibClose;
    @BindView(R.id.tv_sub_title)
    TextView tvSubTitle;
    @BindView(R.id.tv_steam)
    TextView tvSteam;
    @BindView(R.id.fl_expand)
    FrameLayout flExpand;

    @BindView(R.id.vp_sep)
    ViewPager vpContainer;

    EmbedBottomSheetBehavior bottomSheetBehavior;
    PreviewHomeworkAdapter mAdapter;


    @Override
    public int getQuestionIndex() {
        return getArguments().getInt("index");
    }

    public static PrevHWsepFragment newInstance(int index, int total, int nvType) {
        PrevHWsepFragment fragment = new PrevHWsepFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        bundle.putInt("total",total);
        bundle.putInt(ViewLastNextInitHelper.KEY_NAVIGATION,nvType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPrevHWsepComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hwsep, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Question data = QuestionAnswerHolder.getInstance().getQuestion(this);
        if(data == null){
            return;
        }
        bindData(data);
        if(getParentFragment() == null) {   //限制分屏题只能套一层
            initViewPager(data);
        }

        bottomSheetBehavior = EmbedBottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);
        bottomSheetBehavior.setState(EmbedBottomSheetBehavior.STATE_EXPANDED);
        ibClose.setVisibility(View.VISIBLE);
        ibClose.setOnClickListener(this);
        flExpand.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_expand:
                bottomSheetBehavior.setState(EmbedBottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.ib_close:
                bottomSheetBehavior.setState(EmbedBottomSheetBehavior.STATE_COLLAPSED);
                break;
        }
    }

    @Override
    public void nextQuestion() {
        if(vpContainer.getCurrentItem() < vpContainer.getAdapter().getCount()-1){
            vpContainer.setCurrentItem(vpContainer.getCurrentItem()+1);
        }else{
            ((INavigation)getActivity()).nextQuestion();
        }
    }

    @Override
    public void lastQuestion() {
        if(0 < vpContainer.getCurrentItem()){
            vpContainer.setCurrentItem(vpContainer.getCurrentItem()-1);
        }else{
            ((INavigation)getActivity()).lastQuestion();
        }
    }

    @Override
    public void submitPaper() {
        ((INavigation)getActivity()).submitPaper();
    }

    public void bindData(Question data) {
        TypefaceHolder.getInstance().setHt(tvContent);
        RichTextViewHelper.setContent(tvContent,RichTextViewHelper.makeQuestionTitle(data.getQuestionNum(),data.getContent()));
        updateSubTitle(0);
        if(TextUtils.isEmpty(data.getQuestionStem())){
            tvSteam.setVisibility(View.GONE);
        }else{
            tvSteam.setVisibility(View.VISIBLE);
            tvSteam.setText(RichTextViewHelper.ToDBC(data.getQuestionStem()));
        }
    }

    public void initViewPager(Question data) {
//        if (mAdapter == null && data.getSubQuestion() != null && data.getSubQuestion().size()>0) {
        if (mAdapter == null ) {
            mAdapter = new PreviewHomeworkAdapter(getChildFragmentManager(), data.getSubQuestion());
            mAdapter.setParentNavigationType(getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION,
                    ViewLastNextInitHelper.TYPE_NONE));
            vpContainer.setAdapter(mAdapter);
            vpContainer.setOffscreenPageLimit(0);
            vpContainer.addOnPageChangeListener(vpListener);
            updateSubTitle(1);
        }
    }

    private ViewPager.OnPageChangeListener vpListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            updateSubTitle(i+1);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private EmbedBottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new EmbedBottomSheetBehavior.BottomSheetCallback(){
        @Override
        public void onStateChanged(@NonNull View view, int i) {
            switch (i) {
                case BottomSheetBehavior.STATE_COLLAPSED:
                    ibClose.setVisibility(View.INVISIBLE);
                    break;
                case BottomSheetBehavior.STATE_DRAGGING:
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    ibClose.setVisibility(View.VISIBLE);
                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                    break;
                case BottomSheetBehavior.STATE_SETTLING:
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View view, float v) {

        }
    };

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

    private void updateSubTitle(int index){
        int total = vpContainer.getAdapter() ==null?0:vpContainer.getAdapter().getCount();
        tvSubTitle.setText(String.format(strSubTitle,index,total));
    }

}
