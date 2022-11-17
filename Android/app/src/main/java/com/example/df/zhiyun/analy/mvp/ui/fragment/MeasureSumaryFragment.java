package com.example.df.zhiyun.analy.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.analy.di.component.DaggerMeasureSumaryComponent;
import com.example.df.zhiyun.analy.mvp.contract.MeasureSumaryContract;
import com.example.df.zhiyun.mvp.model.entity.LevelDivid;
import com.example.df.zhiyun.mvp.model.entity.ScoreSumary;
import com.example.df.zhiyun.analy.mvp.presenter.MeasureSumaryPresenter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;

import butterknife.BindView;
import me.jessyan.autosize.internal.CustomAdapt;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 评测概括
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MeasureSumaryFragment extends BaseLazyLoadFragment<MeasureSumaryPresenter> implements MeasureSumaryContract.View, CustomAdapt {
    @BindView(R.id.tv_person_count)
    TextView tvPersonCount;
    @BindView(R.id.tv_max)
    TextView tvMax;
    @BindView(R.id.tv_min)
    TextView tvMin;
    @BindView(R.id.tv_avg)
    TextView tvAvg;
    @BindView(R.id.tv_middle_level)
    TextView tvMiddle;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_std)
    TextView tvStd;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_correct)
    TextView tvCorrect;

    @BindView(R.id.tv_perfect_area)
    TextView tvPerfectArea;
    @BindView(R.id.tv_perfect_numb)
    TextView tvPerfectNumb;
    @BindView(R.id.tv_perfect_percent)
    TextView tvPerfectPercent;

    @BindView(R.id.tv_good_area)
    TextView tvGoodArea;
    @BindView(R.id.tv_good_numb)
    TextView tvGoodNumb;
    @BindView(R.id.tv_good_percent)
    TextView tvGoodPercent;

    @BindView(R.id.tv_middle_area)
    TextView tvMiddleArea;
    @BindView(R.id.tv_middle_numb)
    TextView tvMiddleNumb;
    @BindView(R.id.tv_middle_percent)
    TextView tvMiddlePercent;

    @BindView(R.id.tv_pass_area)
    TextView tvPassArea;
    @BindView(R.id.tv_pass_numb)
    TextView tvPassNumb;
    @BindView(R.id.tv_pass_percent)
    TextView tvPassPercent;

    @BindView(R.id.tv_fail_area)
    TextView tvFailArea;
    @BindView(R.id.tv_fail_numb)
    TextView tvFailNumb;
    @BindView(R.id.tv_fail_percent)
    TextView tvFailPercent;

    @BindView(R.id.chart)
    PieChart chart;

    private String[] pieTitles = new String[]{"优秀","良好","中等","及格","不及格"};
    private int[] pieColors = new int[]{Color.parseColor("#f09859"),Color.parseColor("#7abaf5"),
            Color.parseColor("#ed5075"),Color.parseColor("#0acc8b"),
            Color.parseColor("#7977ed")};

    public static MeasureSumaryFragment newInstance(int fzId,int gradeId,String hwId,int schoolId,int subjId,int type) {
        MeasureSumaryFragment fragment = new MeasureSumaryFragment();
        Bundle data = new Bundle();
        data.putInt(MeasureSumaryContract.View.KEY_FZ,fzId);
        data.putInt(MeasureSumaryContract.View.KEY_GRADEID,gradeId);
        data.putString(MeasureSumaryContract.View.KEY_HOMEWORK_ID,hwId);
        data.putInt(MeasureSumaryContract.View.KEY_SCHOOLID,schoolId);
        data.putInt(MeasureSumaryContract.View.KEY_SUBJID,subjId);
        data.putInt(MeasureSumaryContract.View.KEY_TYPE,type);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMeasureSumaryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_measure_sumary, container, false);
    }

    @Override
    public void bindSumaryData(ScoreSumary data) {
        tvPersonCount.setText(Integer.toString(data.getCount()));
        tvMax.setText(String.format("%.1f",data.getHighestScore()));
        tvMin.setText(String.format("%.1f",data.getLowestScore()));
        tvAvg.setText(String.format("%.1f",data.getAverage()));
        tvMiddle.setText(String.format("%.1f",data.getMedian()));
        tvAll.setText(String.format("%.1f",data.getMode()));
        tvStd.setText(String.format("%.1f", data.getStandardDeviation()));
        tvSend.setText(String.format("%d/%d",data.getAssignCount(),data.getUnAssignCount()));
        tvSubmit.setText(String.format("%d/%d",data.getSubmitCount(),data.getUnSubmitCount()));
        tvCorrect.setText(String.format("%d/%d",data.getCorrectCount(),data.getUnCorrectCount()));
    }

    @Override
    public void bindLevelData(LevelDivid data) {
        tvPerfectNumb.setText(Integer.toString(data.getExcellentCount()));
        tvPerfectPercent.setText(String.format("%.1f%%",data.getExcellentRate()));
        tvPerfectArea.setText(String.format("%s (90%%-100%%)",data.getExcellent()));

        tvGoodNumb.setText(Integer.toString(data.getGoodCount()));
        tvGoodPercent.setText(String.format("%.1f%%",data.getGoodRate()));
        tvGoodArea.setText(String.format("%s (80%%-90%%)",data.getGood()));

        tvMiddleNumb.setText(Integer.toString(data.getMediumCount()));
        tvMiddlePercent.setText(String.format("%.1f%%",data.getMediumRate()));
        tvMiddleArea.setText(String.format("%s (70%%-80%%)",data.getMedium()));


        tvPassNumb.setText(Integer.toString(data.getPassCount()));
        tvPassPercent.setText(String.format("%.1f%%",data.getPassRate()));
        tvPassArea.setText(String.format("%s (60%%-70%%)",data.getPass()));

        tvFailNumb.setText(Integer.toString(data.getUnPassCount()));
        tvFailPercent.setText(String.format("%.1f%%",data.getUnPassRate()));
        tvFailArea.setText(String.format("%s (0%%-60%%)",data.getUnPass()));

        initPieChart(data);
        setPieData(data);
    }

    /**
     * 初始化饼状图
     * @param data
     */

    private void initPieChart(LevelDivid data){
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDrawCenterText(false);
        chart.setDragDecelerationFrictionCoef(0.95f);

//        chart.setCenterTextTypeface(tfLight);
//        chart.setCenterText("等级分布");

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
//        chart.setOnChartValueSelectedListener(this);

        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(10f);
        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(Color.BLACK);
//        chart.setEntryLabelTypeface(tfRegular);
        chart.setEntryLabelTextSize(12f);
        chart.setDrawHoleEnabled(false);
        chart.setDrawEntryLabels(false);
    }

    private void setPieData(LevelDivid levelDivid) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        if(levelDivid.getExcellentRate() > 0){
            entries.add(new PieEntry(levelDivid.getExcellentRate(),pieTitles[0]));
            colors.add(pieColors[0]);
        }
        if(levelDivid.getGoodRate() > 0){
            entries.add(new PieEntry(levelDivid.getGoodRate(),pieTitles[1]));
            colors.add(pieColors[1]);
        }

        if(levelDivid.getMediumRate() > 0){
            entries.add(new PieEntry(levelDivid.getMediumRate(),pieTitles[2]));
            colors.add(pieColors[2]);
        }

        if(levelDivid.getPassRate() > 0){
            entries.add(new PieEntry(levelDivid.getPassRate(),pieTitles[3]));
            colors.add(pieColors[3]);
        }

        if(levelDivid.getUnPassRate() > 0){
            entries.add(new PieEntry(levelDivid.getUnPassRate(),pieTitles[4]));
            colors.add(pieColors[4]);
        }

//        entries.add(new PieEntry(10.0f,pieTitles[0]));
//        colors.add(pieColors[0]);
//        entries.add(new PieEntry(20.0f,pieTitles[1]));
//        colors.add(pieColors[1]);
//        entries.add(new PieEntry(20.0f,pieTitles[2]));
//        colors.add(pieColors[2]);
//        entries.add(new PieEntry(30.0f,pieTitles[3]));
//        colors.add(pieColors[3]);
//        entries.add(new PieEntry(20.0f,pieTitles[4]));
//        colors.add(pieColors[4]);


        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);
        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(0.5f);
//        dataSet.setUsingSliceColorAsValueLineColor(true);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
//        data.setValueTypeface(tfLight);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getMeasureSumary();
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
