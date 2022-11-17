package com.example.df.zhiyun.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.di.component.DaggerWholeCompearComponent;
import com.example.df.zhiyun.mvp.contract.WholeCompearContract;
import com.example.df.zhiyun.mvp.model.entity.ScoreCompearLayer;
import com.example.df.zhiyun.mvp.model.entity.ScoreCompearLevel;
import com.example.df.zhiyun.mvp.model.entity.ScoreCompearSumary;
import com.example.df.zhiyun.mvp.presenter.WholeCompearPresenter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WholeCompearFragment extends BaseFragment<WholeCompearPresenter> implements WholeCompearContract.View{
    @BindView(R.id.ll_sumary)
    LinearLayout llSumary;
    @BindView(R.id.ll_level)
    LinearLayout llLevel;
    @BindView(R.id.ll_layer)
    LinearLayout llLayer;

    private LayoutInflater mLayoutInflater;

    public static WholeCompearFragment newInstance(int fzId,int gradeId,String hwId,int schoolId,int subjId,int type) {
        WholeCompearFragment fragment = new WholeCompearFragment();
        Bundle data = new Bundle();
        data.putInt(WholeCompearContract.View.KEY_FZ,fzId);
        data.putInt(WholeCompearContract.View.KEY_GRADEID,gradeId);
        data.putString(WholeCompearContract.View.KEY_HOMEWORK_ID,hwId);
        data.putInt(WholeCompearContract.View.KEY_SCHOOLID,schoolId);
        data.putInt(WholeCompearContract.View.KEY_SUBJID,subjId);
        data.putInt(WholeCompearContract.View.KEY_TYPE,type);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWholeCompearComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_whole_compear, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getData();
        mLayoutInflater = LayoutInflater.from(getContext());
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

    public void bindSumary(List<ScoreCompearSumary> list){
        if(list == null){
            return;
        }
        for(int i=0;i<list.size();i++){
            View ll = mLayoutInflater.inflate(R.layout.item_table_score_sumary,llSumary,false);
            ((TextView)ll.findViewById(R.id.tv_dd_0)).setText(list.get(i).getClassName());
            ((TextView)ll.findViewById(R.id.tv_dd_1)).setText(String.format("%.1f",list.get(i).getAverage()));
            ((TextView)ll.findViewById(R.id.tv_dd_2)).setText(String.format("%.1f",list.get(i).getHighestScore()));
            ((TextView)ll.findViewById(R.id.tv_dd_3)).setText(String.format("%.1f",list.get(i).getLowestScore()));
            ((TextView)ll.findViewById(R.id.tv_dd_4)).setText(String.format("%.1f",list.get(i).getMedian()));
            ((TextView)ll.findViewById(R.id.tv_dd_5)).setText(String.format("%.1f",list.get(i).getMode()));
            ((TextView)ll.findViewById(R.id.tv_dd_6)).setText(String.format("%d/%d",list.get(i).getSubmitCount(),list.get(i).getUnSubmitCount()));
            ((TextView)ll.findViewById(R.id.tv_dd_7)).setText(String.format("%d/%d",list.get(i).getCorrectCount(),list.get(i).getUnCorrectCount()));
            llSumary.addView(ll);
        }
    }

    public void bindLevel(List<ScoreCompearLevel> list){
        if(list == null){
            return;
        }
        for(int i=0;i<list.size();i++){
            View ll = mLayoutInflater.inflate(R.layout.item_table_level,llLevel,false);
            ((TextView)ll.findViewById(R.id.tv_dd_0)).setText(list.get(i).getClassName());
            ((TextView)ll.findViewById(R.id.tv_dd_1)).setText(String.format("%d",list.get(i).getExcellentCount()));
            ((TextView)ll.findViewById(R.id.tv_dd_2)).setText(String.format("%d",list.get(i).getGoodCount()));
            ((TextView)ll.findViewById(R.id.tv_dd_3)).setText(String.format("%d",list.get(i).getMediumCount()));
            ((TextView)ll.findViewById(R.id.tv_dd_4)).setText(String.format("%d",list.get(i).getPassCount()));
            ((TextView)ll.findViewById(R.id.tv_dd_5)).setText(String.format("%d",list.get(i).getUnPassCount()));
            ((TextView)ll.findViewById(R.id.tv_dd_6)).setText(String.format("%d",list.get(i).getCount()));
            llLevel.addView(ll);
        }
    }

    public void bindLayout(List<ScoreCompearLayer> list){
        if(list == null){
            return;
        }
        for(int i=0;i<list.size();i++){
            View ll = mLayoutInflater.inflate(R.layout.item_table_layer,llLayer,false);
            ((TextView)ll.findViewById(R.id.tv_dd_0)).setText(list.get(i).getClassName());
            ((TextView)ll.findViewById(R.id.tv_dd_1)).setText(String.format("%d",list.get(i).getFirstArrangement()));
            ((TextView)ll.findViewById(R.id.tv_dd_2)).setText(String.format("%d",list.get(i).getSecondArrangement()));
            ((TextView)ll.findViewById(R.id.tv_dd_3)).setText(String.format("%d",list.get(i).getThirdArrangement()));
            ((TextView)ll.findViewById(R.id.tv_dd_4)).setText(String.format("%d",list.get(i).getFourthArrangement()));
            llLayer.addView(ll);
        }
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
