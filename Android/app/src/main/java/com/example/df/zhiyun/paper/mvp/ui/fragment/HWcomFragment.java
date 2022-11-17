package com.example.df.zhiyun.paper.mvp.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.mvp.contract.INavigation;
import com.example.df.zhiyun.mvp.contract.IQuestionLookup;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.example.df.zhiyun.paper.di.component.DaggerHWcomComponent;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zhihu.matisse.Matisse;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import com.example.df.zhiyun.app.utils.MediaPickerHelper;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.app.utils.FileUriUtil;
import com.example.df.zhiyun.mvp.contract.DoHomeworkContract;
import com.example.df.zhiyun.paper.mvp.contract.HWcomContract;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.paper.mvp.presenter.HWcomPresenter;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.paper.mvp.ui.adapter.SquareImageAdapter;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 作文题
 * <p>
 * Created by MVPArmsTemplate on 08/16/2019 11:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HWcomFragment extends BaseFragment<HWcomPresenter> implements HWcomContract.View , DoHomeworkContract
    ,BaseQuickAdapter.OnItemClickListener,MediaPickerHelper.IMediaPicker , IQuestionLookup {
    public static final int MAX_PIC_MUN = 3;    //最大图片数
    public static final int RQ_CAPTURE = 87;    //请求拍照
    public static final int RQ_ALBUM = 89;    //请求相册
    @BindView(R.id.tv_com_content)
    HtmlTextView tvContent;
    @BindView(R.id.recyclerView_img)
    RecyclerView recyclerView;

    @BindView(R.id.tv_steam_com)
    HtmlTextView tvSteam;

    @Inject
    BaseQuickAdapter mAdapter;
    //    @Inject
//    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    private File mCameraFile;
    Dialog mDialog;

    public static HWcomFragment newInstance(int index, int total, int nvType) {
        HWcomFragment fragment = new HWcomFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        bundle.putInt("total",total);
        bundle.putInt(ViewLastNextInitHelper.KEY_NAVIGATION,nvType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(recyclerView != null){
            recyclerView.setAdapter(null);
        }
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHWcomComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int getQuestionIndex() {
        return getArguments().getInt("index");
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hwcom, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        Question data = QuestionAnswerHolder.getInstance().getQuestion(this);
        if(data == null){
            return;
        }
        bindData(data);
        initRecyclerView();
        if(getParentFragment() == null){  //没被嵌套，不是分屏里的小题
            new ViewLastNextInitHelper().init(this,(INavigation)getActivity(),
                    getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION),false);
        }else{
            new ViewLastNextInitHelper().init(this,(INavigation)getParentFragment()
                    ,getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION),false);
        }
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
        RichTextViewHelper.setContent(tvContent,
                RichTextViewHelper.makeQuestionTitle(data.getQuestionNum(),data.getContent()));
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(itemChildClickListener);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Timber.tag(TAG).d("click item");
        String strItem = (String)adapter.getData().get(position);
        if(TextUtils.isEmpty(strItem)){
            mDialog = MediaPickerHelper.getPicSelDialog(getContext(),"图片",this);
            mDialog.show();
        }
    }

    private BaseQuickAdapter.OnItemChildClickListener itemChildClickListener =
            new BaseQuickAdapter.OnItemChildClickListener(){

                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()){
                        case R.id.ib_img_del:
                            Timber.tag(TAG).d("click cross");
                            ((SquareImageAdapter)mAdapter).removeImageItem(position);
                            break;
                    }
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
        ((SquareImageAdapter)mAdapter).initeWhiteAnswer(answer);
    }

    @Override
    public Answer getAnswer() {
        Question question = QuestionAnswerHolder.getInstance().getQuestion(this);
        Answer answer = ((SquareImageAdapter)mAdapter).getAnswer();
        answer.setQuestionId(question.getQuestionId());
        return answer;
    }

    @Override
    public void selectCamera() {
        mCameraFile = FileUriUtil.getTempPicFile(getContext());
        MediaPickerHelper.lunchCamera(this,RQ_CAPTURE,FileUriUtil.getUriFromFile(getContext(),mCameraFile));
    }

    @Override
    public void selectAlbum() {
        MediaPickerHelper.lunchAlbum(this,RQ_ALBUM,
                MAX_PIC_MUN-((SquareImageAdapter)mAdapter).getImageCount());
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
