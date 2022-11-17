package com.example.df.zhiyun.preview.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.example.df.zhiyun.preview.mvp.ui.widget.SelMemberSpanLookup;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.preview.di.component.DaggerResolveTchOthComponent;
import com.example.df.zhiyun.preview.mvp.contract.ResolveTchOthContract;
import com.example.df.zhiyun.preview.mvp.presenter.ResolveTchOthPresenter;

import com.example.df.zhiyun.R;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:教师端班级除了选择题的其它题型解析
 * <p>
 * Created by MVPArmsTemplate on 04/29/2020 11:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ResolveTchOthFragment extends BaseFragment<ResolveTchOthPresenter> implements ResolveTchOthContract.View {
    @BindView(R.id.tv_numb)
    TextView tvNumber;
    @BindView(R.id.tv_content)
    HtmlTextView tvContent;
    @BindView(R.id.tv_check_point)
    HtmlTextView tvCheckPoint;
    @BindView(R.id.tv_answer_detail)
    HtmlTextView tvDetail;
    @BindView(R.id.recyclerViewSel)
    RecyclerView recyclerView;

    @Inject
    BaseQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    BaseQuickAdapter.SpanSizeLookup spanSizeLookup;

    @BindView(R.id.tv_paper_name)
    TextView tvPaperName;
    @BindView(R.id.v_div)
    View vDiv;
    @Inject
    Boolean isStaticPage;


    /**
     *
     * @param isStaticPage 是否嵌入错题统计详情页
     * @return
     */
    public static ResolveTchOthFragment newInstance(boolean isStaticPage) {
        ResolveTchOthFragment fragment = new ResolveTchOthFragment();
        Bundle data = new Bundle();
        data.putBoolean(ResolveTchOthContract.View.KEY_PAGE_STATIC,isStaticPage);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerResolveTchOthComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resolve_tch_oth, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvPaperName.setVisibility(isStaticPage?View.VISIBLE:View.GONE);
        vDiv.setVisibility(isStaticPage?View.VISIBLE:View.GONE);
        RichTextViewHelper.setContent(tvContent,"3. 一个直角三角形的两条直角边长分别为1和2，将该三角形分别绕其两个直角边旋转得到的两个圆锥的体积之比为（    ）");
        RichTextViewHelper.setContent(tvDetail,"\n" +
                "                                此题的答案详解此题的答案详解此题的答案详解此题的答案详解\n" +
                "此题的答案详解此题的答案详解\n" +
                "                            ");
        RichTextViewHelper.setContent(tvCheckPoint,"本题涉及到的知识点，可能有多个");

        ((SelMemberSpanLookup)spanSizeLookup).setData(mAdapter.getData());
        mAdapter.setSpanSizeLookup(spanSizeLookup);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
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
