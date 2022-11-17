package com.example.df.zhiyun.paper.mvp.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.mvp.contract.INavigation;
import com.example.df.zhiyun.mvp.contract.IQuestionLookup;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.example.df.zhiyun.paper.di.component.DaggerHWcpComponent;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import com.example.df.zhiyun.app.utils.MediaPickerHelper;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.app.utils.FileUriUtil;
import com.example.df.zhiyun.mvp.contract.DoHomeworkContract;
import com.example.df.zhiyun.paper.mvp.contract.HWcpContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.CpQuestionMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.paper.mvp.presenter.HWcpPresenter;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.paper.mvp.ui.adapter.HWcpAdapter;
import com.example.df.zhiyun.mvp.ui.widget.CanvasDialog;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;
import timber.log.Timber;

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
public class HWcpFragment extends BaseFragment<HWcpPresenter> implements HWcpContract.View
    ,View.OnClickListener , DoHomeworkContract,MediaPickerHelper.IMediaPicker ,BaseQuickAdapter.OnItemChildClickListener
    , IQuestionLookup , CanvasDialog.ICanvasEnvent{
    public static final int RQ_CAPTURE = 71;    //请求拍照
    public static final int RQ_ALBUM = 77;    //请求相册
    @BindView(R.id.tv_input_content)
    HtmlTextView tvContent;
    @BindView(R.id.recyclerView_cp)
    RecyclerView recyclerView;

    @BindView(R.id.tv_steam)
    HtmlTextView tvSteam;

    @Inject
    BaseMultiItemQuickAdapter mAdapter;
    //    @Inject
//    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    private int optionIndex = -1;  // 操作的是第几个item
    private File mCameraFile;
    Dialog mDialog;

    public static HWcpFragment newInstance(int index, int total, int nvType) {
        HWcpFragment fragment = new HWcpFragment();
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
        DaggerHWcpComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(recyclerView != null){
            recyclerView.setAdapter(null);
        }
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hwcp, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Question data = QuestionAnswerHolder.getInstance().getQuestion(this);
        if(data == null){
            return;
        }
        bindData(data);
        if(getParentFragment() == null){  //没被嵌套，不是分屏里的小题
            new ViewLastNextInitHelper().init(this,(INavigation)getActivity(),
                    getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION),false);
        }else{
            new ViewLastNextInitHelper().init(this,(INavigation)getParentFragment(),
                    getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION),false);
        }
        initRecyclerView(data);
    }

    public void bindData(Question data) {
        if(TextUtils.isEmpty(data.getQuestionStem())){
            tvSteam.setVisibility(View.GONE);
        }else{
            tvSteam.setVisibility(View.VISIBLE);
//            tvSteam.setText(RichTextViewHelper.ToDBC(data.getQuestionStem()));
            RichTextViewHelper.setContent(tvSteam,data.getQuestionStem());
        }
        TypefaceHolder.getInstance().setHt(tvContent);
        RichTextViewHelper.setContent(tvContent,RichTextViewHelper.makeQuestionTitle(data.getQuestionNum(),data.getContent()));
    }

    private void initRecyclerView(Question data){
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(transformData(data));
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemClickListener(itemClickListener);
    }


    private BaseQuickAdapter.OnItemClickListener itemClickListener = new BaseQuickAdapter.OnItemClickListener(){
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            ((HWcpAdapter)mAdapter).setSelectIndex(position);
        }
    };

    //把大题的各个小题拆分，题目头，选项
    private List<CpQuestionMultipleItem> transformData(Question data){
        List<CpQuestionMultipleItem> items = new ArrayList<>();

        List<Question> subQuestions = data.getSubQuestion();
        if(subQuestions != null){
            CpQuestionMultipleItem tempItem ;
            for(Question question:subQuestions){
                tempItem = new CpQuestionMultipleItem(CpQuestionMultipleItem.TYPE_TITLE_INPUT,question,null);
                items.add(tempItem);

                if(question.getOptionList() != null && question.getOptionList().size()>0){
                    int optIndex = 0;
                    for(QuestionOption option:question.getOptionList()){
                        tempItem = new CpQuestionMultipleItem(transformQuestionType(question.getQuestionType()),question,option);
                        tempItem.setOptIndex(optIndex);
                        items.add(tempItem);
                        optIndex++;
                    }
                }else{
                    tempItem = new CpQuestionMultipleItem(transformQuestionType(question.getQuestionType()),question,new QuestionOption());
                    tempItem.setOptIndex(0);
                    items.add(tempItem);
                }
            }
        }

        return items;
    }


    private int transformQuestionType(String type){
        try{
            int qType = Integer.parseInt(type);
            if(qType == Api.QUESTION_SELECT){
                return CpQuestionMultipleItem.TYPE_OPTION_SELECT;
            }else if(qType == Api.QUESTION_MULTIPLE_SELECT){
                return CpQuestionMultipleItem.TYPE_OPTION_MULTIPLE;
            }else if(qType == Api.QUESTION_INPUT){
                return CpQuestionMultipleItem.TYPE_OPTION_INPUT;
            }else if(qType == Api.QUESTION_BIG){
                return CpQuestionMultipleItem.TYPE_TITLE_INPUT;
            }else if(qType == Api.QUESTION_SIMPLE){
                return CpQuestionMultipleItem.TYPE_OPTION_SIMPLE;
            }else {
                return CpQuestionMultipleItem.TYPE_OPTION_INPUT;
            }
        }catch (NumberFormatException e){
            return CpQuestionMultipleItem.TYPE_OPTION_INPUT;
        }

    }



    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        optionIndex = position;
        CpQuestionMultipleItem item = (CpQuestionMultipleItem)adapter.getData().get(position);

        switch (view.getId()){
            case R.id.tv_canvas:
                if(item.getItemType() == CpQuestionMultipleItem.TYPE_OPTION_SIMPLE){
                    mDialog = CanvasDialog.getCanvasDialog(getContext(),this);
                    mDialog.show();
                }else{
                    if(((HWcpAdapter)mAdapter).isCanvasMethod(position)){
                        ((HWcpAdapter)mAdapter).setCanvasMethod(position,false);
                    }else{
                        mDialog = CanvasDialog.getCanvasDialog(getContext(),this);
                        mDialog.show();
                        ((HWcpAdapter)mAdapter).setCanvasMethod(position,true);
                    }
                }

                break;
//            case R.id.tv_rechoice:
//                Timber.tag(TAG).d("click rechoice");
//                mDialog = CanvasDialog.getCanvasDialog(getContext(),this);
//                mDialog.show();
//                break;
            case R.id.tv_camera:
                mDialog = MediaPickerHelper.getPicSelDialog(getContext(),"图片",this);
                mDialog.show();
                ((HWcpAdapter)mAdapter).setCanvasMethod(position,true);
                break;
        }
    }

    @Override
    public void selectCamera() {
        mCameraFile = FileUriUtil.getTempPicFile(getContext());
        MediaPickerHelper.lunchCamera(this,RQ_CAPTURE,FileUriUtil.getUriFromFile(getContext(),mCameraFile));
    }

    @Override
    public void selectAlbum() {
        MediaPickerHelper.lunchAlbum(this,RQ_ALBUM);
    }

    @Override
    public void onBitmapCreate(Bitmap bitmap) {
        if(bitmap != null){
            ((HWcpAdapter)mAdapter).insertBitmap(bitmap,optionIndex);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_last:
                ((INavigation)getActivity()).lastQuestion();
                break;
            case R.id.tv_next:
                ((INavigation)getActivity()).nextQuestion();
                break;
        }
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

    @Override
    public void initeWhiteAnswer(Answer answer) {
        if(answer == null){
            return;
        }
        ((HWcpAdapter)mAdapter).setInitAnsers(answer.getSubAnswer());
    }

    @Override
    public Answer getAnswer() {
        Question question = QuestionAnswerHolder.getInstance().getQuestion(this);
        Answer answer = new Answer();
        answer.setQuestionId(question.getQuestionId());
        answer.setSubAnswer(((HWcpAdapter)mAdapter).getAnswers());
        return answer;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_CAPTURE && resultCode == Activity.RESULT_OK) {  //拍照返回
            MediaPickerHelper.updateAlbum(getContext(),mCameraFile.getPath());
            mPresenter.onCameraBack(mCameraFile);
        } else if(resultCode == Activity.RESULT_OK && requestCode == RQ_ALBUM){
            mPresenter.onAlbumBack(Matisse.obtainResult(data));
        }
    }
}
