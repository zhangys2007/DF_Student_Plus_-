package com.example.df.zhiyun.analy.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.analy.mvp.ui.widget.MainChartConfig;
import com.example.df.zhiyun.analy.mvp.ui.widget.ScrollBlockView;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.mvp.model.entity.ClassImprove;
import com.example.df.zhiyun.mvp.ui.widget.MarkerViewGrowthClass;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.analy.di.component.DaggerLevelReportDetailComponent;
import com.example.df.zhiyun.analy.mvp.contract.LevelReportDetailContract;
import com.example.df.zhiyun.analy.mvp.presenter.LevelReportDetailPresenter;

import com.example.df.zhiyun.R;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 学业水平报告
 * <p>
 * Created by MVPArmsTemplate on 05/15/2020 13:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LevelReportDetailActivity extends BaseActivity<LevelReportDetailPresenter> implements LevelReportDetailContract.View
        , OnChartValueSelectedListener {
    @BindView(R.id.recyclerView_status)
    RecyclerView recyclerViewStatus;
    @BindView(R.id.recyclerView_point)
    RecyclerView recyclerViewPoint;
    @BindView(R.id.recyclerView_hw)
    RecyclerView recyclerViewHw;

    @BindView(R.id.chart)
    LineChart chart;
    @BindView(R.id.sub_scroll)
    ScrollBlockView mSubScroll;

    @Inject
    @Named(LevelReportDetailContract.View.KEY_STATUS)
    public BaseQuickAdapter mAdapterStatus;
    @Inject
    @Named(LevelReportDetailContract.View.KEY_STATUS)
    public RecyclerView.LayoutManager layoutManagerStatus;
    @Inject
    @Named(LevelReportDetailContract.View.KEY_STATUS)
    public RecyclerView.ItemDecoration itemDecorationStatus;

    @Inject
    @Named(LevelReportDetailContract.View.KEY_POINT)
    public BaseQuickAdapter mAdapterPoint;
    @Inject
    @Named(LevelReportDetailContract.View.KEY_POINT)
    public RecyclerView.LayoutManager layoutManagerPoint;
    @Inject
    @Named(LevelReportDetailContract.View.KEY_POINT)
    public RecyclerView.ItemDecoration itemDecorationPoint;

    @Inject
    @Named(LevelReportDetailContract.View.KEY_HW)
    public BaseMultiItemQuickAdapter mAdapterHW;
    @Inject
    @Named(LevelReportDetailContract.View.KEY_HW)
    public RecyclerView.LayoutManager layoutManagerHW;

    MainChartConfig mChartConfig;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLevelReportDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_level_report_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        recyclerViewStatus.addItemDecoration(itemDecorationStatus);
        recyclerViewStatus.setLayoutManager(layoutManagerStatus);
        recyclerViewStatus.setAdapter(mAdapterStatus);

        recyclerViewPoint.addItemDecoration(itemDecorationPoint);
        recyclerViewPoint.setLayoutManager(layoutManagerPoint);
        recyclerViewPoint.setAdapter(mAdapterPoint);
        mAdapterPoint.setOnItemClickListener(pointClickListener);

        recyclerViewHw.setLayoutManager(layoutManagerHW);
        recyclerViewHw.setAdapter(mAdapterHW);
        mAdapterHW.setOnItemChildClickListener(hwChildClickListener);

        initChart();
        mChartConfig = new MainChartConfig(chart, mSubScroll, this);
        processChartData();
//        chartConfig.setData(getDefaultData());
    }

    BaseMultiItemQuickAdapter.OnItemClickListener pointClickListener = new BaseMultiItemQuickAdapter.OnItemClickListener(){
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            ArmsUtils.startActivity(KnowledgeHoldActivity.class);
        }
    };

    BaseMultiItemQuickAdapter.OnItemChildClickListener hwChildClickListener = new BaseMultiItemQuickAdapter.OnItemChildClickListener(){
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            ArmsUtils.startActivity(StaticDetailActivity.class);
        }
    };

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
        finish();
    }

    private void initChart(){
        int color = ContextCompat.getColor(this,R.color.text_999);
        chart.setNoDataText("暂无数据");
        chart.setBackgroundColor(Color.WHITE);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setOnChartValueSelectedListener(this);
        chart.setDrawGridBackground(false);
//        chart.setHorizontalScrollBarEnabled(true);
        MarkerViewGrowthClass mv = new MarkerViewGrowthClass(this, R.layout.marker_grade);
        // Set the marker to the chart
        mv.setChartView(chart);
        chart.setMarker(mv);

        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        // chart.setScaleXEnabled(true);
         chart.setScaleYEnabled(false);
        chart.setPinchZoom(true);

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setLabelCount(7, false);
            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(false);
            xAxis.setTextColor(color);
            xAxis.setAxisLineColor(color);
            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    int index = (int)value;
                    if (chart.getData() != null &&
                            chart.getData().getDataSetCount() > 0) {
                        LineDataSet set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
                        List<Entry> entries = set1.getValues();
                        if(entries != null && entries.size()> index){
                            ClassImprove grade = (ClassImprove)entries.get(index).getData();
                            return TimeUtils.getMd(grade.getOpenTime());
                        }
                    }
                    return "";
                }
            });
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
//            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setTextColor(color);
            yAxis.setGridColor(color);
            yAxis.setSpaceTop(10);
            yAxis.setDrawAxisLine(false);
            yAxis.setAxisMaximum(110f);
            yAxis.setAxisMinimum(0f);
            yAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return Integer.toString((int)value)+"%";
                }
            });
        }
        chart.animateX(1500);
        Legend l = chart.getLegend();
        l.setEnabled(false);
        // draw legend entries as lines
//        l.setForm(Legend.LegendForm.CIRCLE);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setTextColor(color);
//        l.setTextSize(12);


    }

    public void setData(List<Entry> listClass) {
//        LineDataSet set1;
        LineDataSet set2;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 1) {
//            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
//            set1.setValues(listMy);
//            set1.notifyDataSetChanged();

            set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
            set2.setValues(listClass);
            set2.notifyDataSetChanged();
            set2.setDrawHorizontalHighlightIndicator(false);
            set2.setHighLightColor(ContextCompat.getColor(this,R.color.blue_tag));

            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();
        } else {
            // create a dataset and give it a type
//            set1 = getDataSet("学生得分率",Color.RED,listMy);
            set2 = getDataSet("班级得分率",ContextCompat.getColor(this,R.color.blue_tag),listClass);
            set2.setDrawHorizontalHighlightIndicator(false);
            set2.setHighLightColor(ContextCompat.getColor(this,R.color.blue_tag));
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//            dataSets.add(set1); // add the data sets
            dataSets.add(set2); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
            chart.notifyDataSetChanged();
            chart.invalidate();
        }
    }

    private LineDataSet getDataSet(String legent, int color,List<Entry> datas){
        // create a dataset and give it a type
        LineDataSet set1;
        set1 = new LineDataSet(datas, legent);
        set1.setDrawIcons(false);

        // draw dashed line
//            set1.enableDashedLine(10f, 5f, 0f);

        // black lines and points
        set1.setColor(color);
        set1.setCircleColor(color);

        // line thickness and point size
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);

        // draw points as solid circles
        set1.setDrawCircleHole(false);

        // customize legend entry
        set1.setFormLineWidth(1f);
//        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        // text size of values
        set1.setDrawValues(false);
        set1.setValueTextSize(9f);

        // draw selection line as dashed
//            set1.enableDashedHighlightLine(10f, 5f, 0f);

        // set the filled area
//            set1.setDrawFilled(true);
//            set1.setFillFormatter(new IFillFormatter() {
//                @Override
//                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                    return chart.getAxisLeft().getAxisMinimum();
//                }
//            });

        // set color of filled area
//            if (Utils.getSDKInt() >= 18) {
//                // drawables only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.);
//                set1.setFillDrawable(drawable);
//            } else {
//                set1.setFillColor(Color.BLACK);
//            }
        return set1;
    }


    private void processChartData(){
        List<ClassImprove> list = new ArrayList<>();
        for(int i=0;i<20;i++){
            ClassImprove temp = new ClassImprove();
            list.add(temp);

            temp.setAvg((float) (Math.random()*50.f+50));
            temp.setClass_id("111");
            temp.setHomework_name("作业1");
            temp.setScore_rate((float) (Math.random()*50.f+50));
            temp.setOpenTime(1589528022);
        }

        ArrayList<Entry> listClass = new ArrayList<>();
        if(list == null || list.size() == 0){
            setData(listClass);
            return;
        }

        int i=0;
        for(ClassImprove point:list){
            listClass.add(new Entry(i, point.getScore_rate(), point));
            i++;
        }

        setData(listClass);
        mChartConfig.refresh();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
