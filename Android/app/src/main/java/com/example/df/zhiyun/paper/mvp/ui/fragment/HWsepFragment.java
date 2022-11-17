package com.example.df.zhiyun.paper.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.mvp.contract.INavigation;
import com.example.df.zhiyun.mvp.contract.IQuestionLookup;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.example.df.zhiyun.paper.di.component.DaggerHWsepComponent;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.mvp.contract.DoHomeworkContract;
import com.example.df.zhiyun.paper.mvp.contract.HWsepContract;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.paper.mvp.presenter.HWsepPresenter;
import com.example.df.zhiyun.paper.mvp.ui.adapter.SetHomeworkAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import timber.log.Timber;


import com.example.df.zhiyun.mvp.model.entity.Answer;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.ui.widget.EmbedBottomSheetBehavior;

import org.simple.eventbus.Subscriber;

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
public class HWsepFragment extends BaseFragment<HWsepPresenter> implements HWsepContract.View
    ,View.OnClickListener, DoHomeworkContract , INavigation , IQuestionLookup {
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
    HtmlTextView tvSteam;
    @BindView(R.id.fl_expand)
    FrameLayout flExpand;

    @BindView(R.id.tv_last)
    TextView tvLast ;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @BindView(R.id.vp_sep)
    ViewPager vpContainer;

    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.nsv_title)
    NestedScrollView nestedScrollView;
    @BindView(R.id.pb)
    ProgressBar pbWaite;

//    @Inject
//    KProgressHUD progressHUD;

    EmbedBottomSheetBehavior bottomSheetBehavior;
    SetHomeworkAdapter mAdapter;
    private int lastViewpagerIndex = -1;
//    ViewLastNextInitHelper viewLastNextInitHelper;

    @Override
    public int getQuestionIndex() {
        return getArguments().getInt("index");
    }


    public static HWsepFragment newInstance(int index, int total, int nvType) {
        HWsepFragment fragment = new HWsepFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        bundle.putInt("total",total);
        bundle.putInt(ViewLastNextInitHelper.KEY_NAVIGATION,nvType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHWsepComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(bottomSheetBehavior != null){
            bottomSheetBehavior.destroy();
            bottomSheetBehavior = null;
        }

//        if(progressHUD != null){
//            progressHUD.dismiss();
//        }
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

        if(getParentFragment() == null){   //限制分屏题只能套一层
            Timber.tag(TAG).d("initData:initViewPager");
            initViewPager(data);
        }

        bottomSheetBehavior = EmbedBottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);
        bottomSheetBehavior.setState(EmbedBottomSheetBehavior.STATE_EXPANDED);
        ibClose.setVisibility(View.VISIBLE);
        ibClose.setOnClickListener(this);
        flExpand.setOnClickListener(this);

        tvLast.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);

        updateNavigator();
        initCurrentPage();
    }


    private void updateNavigator(){
        int type = ViewLastNextInitHelper.parseNavigatioType(vpContainer.getCurrentItem(),vpContainer.getAdapter().getCount()
                ,getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION));
        if(type == ViewLastNextInitHelper.TYPE_FIRST || type == ViewLastNextInitHelper.TYPE_ONLY){
            tvLast.setVisibility(View.INVISIBLE);
        }else{
            tvLast.setVisibility(View.VISIBLE);
        }

        if(type == ViewLastNextInitHelper.TYPE_LAST || type == ViewLastNextInitHelper.TYPE_ONLY){
            tvNext.setVisibility(View.INVISIBLE);
        }else{
            tvNext.setVisibility(View.VISIBLE);
        }
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
            case R.id.tv_last:
                lastQuestion();
                break;
            case R.id.tv_next:
                int type = ViewLastNextInitHelper.parseNavigatioType(vpContainer.getCurrentItem(),vpContainer.getAdapter().getCount()
                        ,getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION));
                if(type == ViewLastNextInitHelper.TYPE_LAST || type == ViewLastNextInitHelper.TYPE_ONLY){
                    submitPaper();
                }else{
                    nextQuestion();
                }
                break;
            case R.id.tv_submit:
                submitPaper();
                break;
        }
    }

    @Subscriber(tag = EventBusTags.UPDATE_SUB_QUESTION_VIEWPAGER)
    public void recvCmdSwitchViewPager(Integer position){
        if(position >= 0 && position<vpContainer.getAdapter().getCount()){
            vpContainer.setCurrentItem(position);
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

        updateSubTitle(0);
        if(TextUtils.isEmpty(data.getQuestionStem())){
            tvSteam.setVisibility(View.GONE);
        }else{
            tvSteam.setVisibility(View.VISIBLE);
//            tvSteam.setText(RichTextViewHelper.ToDBC(data.getQuestionStem()));
            RichTextViewHelper.setContent(tvSteam,data.getQuestionStem());
        }

        if(data.getContentType() == 1){
            pdfView.setVisibility(View.VISIBLE);
            nestedScrollView.setVisibility(View.INVISIBLE);
            mPresenter.getPdfFile(data.getContent());
        }else{
            pdfView.setVisibility(View.INVISIBLE);
            nestedScrollView.setVisibility(View.VISIBLE);
            RichTextViewHelper.setContent(tvContent,
                    RichTextViewHelper.makeQuestionTitle(data.getQuestionNum(),data.getContent()));
        }
    }

    public void initViewPager(Question data) {
        if (mAdapter == null ) {
            mAdapter = new SetHomeworkAdapter(getChildFragmentManager(), data.getSubQuestion());
            mAdapter.setParentNavigationType(getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION,
                    ViewLastNextInitHelper.TYPE_NONE));
            vpContainer.setAdapter(mAdapter);
            vpContainer.setOffscreenPageLimit(0);
            vpContainer.addOnPageChangeListener(vpListener);
            updateSubTitle(1);
            lastViewpagerIndex = 0;
        }
    }

    /**
     * 延迟初始化，如果立即初始化，mAdapter.getFragmentList()会等于null
     */
    private void initCurrentPage(){
        vpContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                Answer tempAnswer = QuestionAnswerHolder.getInstance().getAnswer(HWsepFragment.this);
                if (tempAnswer == null) {
                    return;
                }
                List<Answer> listSubAnswer = tempAnswer.getSubAnswer();
                final int index = vpContainer.getCurrentItem();
                if (listSubAnswer != null && listSubAnswer.size() > index) {
                    mPresenter.initeWhiteNewAnswer(listSubAnswer);
                    initQuestionWithAnswer(index, listSubAnswer.get(index));
                }
            }
        },500);
    }

    private ViewPager.OnPageChangeListener vpListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            updateSubTitle(i+1);
            mPresenter.onSubPageSwitch(lastViewpagerIndex,i);
            lastViewpagerIndex = i;
            updateNavigator();
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
    public int getCurrentQuestionIndex() {
        return vpContainer.getCurrentItem();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
//        if(progressHUD!=null){
//            if(progressHUD.isShowing()){
//                progressHUD.dismiss();
//            }
//            progressHUD.show();
//        }
        if(pbWaite != null){
            pbWaite.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
//        if(progressHUD!=null){
//            progressHUD.dismiss();
//        }
        if(pbWaite != null) {
            pbWaite.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        if(message == null){
            return;
        }
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

    @Override
    public void initeWhiteAnswer(Answer answer) {
        updateNavigator();
        if(answer == null){
            return;
        }
        List<Answer> listSubAnswer = answer.getSubAnswer();
        if(listSubAnswer != null && listSubAnswer.size() > 0){
            mPresenter.initeWhiteNewAnswer(listSubAnswer);

            int currentIndex = vpContainer.getCurrentItem();
            if(currentIndex < listSubAnswer.size()){
                initQuestionWithAnswer(currentIndex,listSubAnswer.get(currentIndex));
            }
        }
    }

    @Override
    public Answer getAnswer() {
        mPresenter.saveCurrentPageAnswer();

        Question question = QuestionAnswerHolder.getInstance().getQuestion(this);
        Answer answer = new Answer();
        answer.setQuestionId(question.getQuestionId());
        answer.setSubAnswer(mPresenter.getAnswerList(true,question));

        return answer;
    }

    //从小题里提取答案
    public Answer getAnswerFromQuestion(int index){
        if(mAdapter== null || index >= mAdapter.getCount()){  //mAdapter为null，有可能是分屏嵌套分屏，前面代码拦截不初始化adapter
            return null;
        }
        ArrayList<Fragment> fragments = mAdapter.getFragmentList();
        if(fragments == null || fragments.size() == 0){
            return null;
        }
        Fragment fragment = fragments.get(index);
        if(fragment != null){
            return  ((DoHomeworkContract)fragment).getAnswer();
        }else{
            return null;
        }
    }

    //恢复小题的所选所填
    public void initQuestionWithAnswer(int index, Answer answer){
        if(answer == null){
            return;
        }

        ArrayList<Fragment> fragments = mAdapter.getFragmentList();

        if(fragments != null && index < fragments.size()){
            Fragment fragment = fragments.get(index);
            if(fragment != null){
                ((DoHomeworkContract)fragment).initeWhiteAnswer(answer);
            }
        }
    }

    @Override
    public void showPdf(File file) {
        PDFView.Configurator configurator = null;
        configurator =    pdfView.fromFile(file);

        configurator.enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .onDrawAll(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setStrokeWidth(ArmsUtils.dip2px(getContext(),5));
                        paint.setColor(ContextCompat.getColor(getContext(),R.color.text_999));
                        canvas.drawLine(0, pageHeight, pageWidth, pageHeight, paint);
                    }
                })
                //在加载文档并开始渲染之后调用
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        Log.d(TAG, "loadComplete: " + nbPages);
                    }
                }) // called after document is loaded and starts to be rendered
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        Log.d(TAG, "onPageChanged :" + "page = " + page + "  pageCount = " + pageCount);
                    }
                })
                .onPageScroll(new OnPageScrollListener() {
                    @Override
                    public void onPageScrolled(int page, float positionOffset) {
                        Log.d(TAG, "onPageScrolled :" + "page = " + page + "  positionOffset = " + positionOffset);
                    }
                })
                .onError(new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        Log.d(TAG, "onError: " + t.getMessage());
                    }
                })
                .onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {
                        t.printStackTrace();
                        Log.d(TAG, "onPageError :" + "page = " + page + "  msg = " + t.getMessage());
                    }
                })

                //在第一次呈现文档后调用 called after document is rendered for the first time
                .onRender(new OnRenderListener() {
                    @Override
                    public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                        Log.d(TAG, "onInitiallyRendered :" + "nbPages = " + nbPages + "  pageWidth = " + pageWidth + "  pageHeight = " + pageHeight);
                    }
                })

                .onTap(new OnTapListener() {
                    @Override
                    public boolean onTap(MotionEvent e) {
                        Log.d(TAG, "onTap: " + e.toString());
                        Log.d(TAG, "onTap: " + "X = " + e.getX() + "  Y = " + e.getY());
                        return false;
                    }
                })
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)

                .enableAntialiasing(true)
                .spacing(20)
                .load();
    }
}
