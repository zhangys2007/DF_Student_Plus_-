package com.example.df.zhiyun.analy.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.di.component.DaggerGrowthTraceComponent;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.analy.mvp.contract.GrowthTraceContract;
import com.example.df.zhiyun.mvp.model.entity.GrowthTraceItem;
import com.example.df.zhiyun.mvp.model.entity.StudentImprove;
import com.example.df.zhiyun.analy.mvp.presenter.GrowthTracePresenter;
import com.example.df.zhiyun.mvp.ui.widget.MarkerViewGrowth;
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
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:教师端学生成长轨迹
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 10:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GrowthTraceActivity extends BaseActivity<GrowthTracePresenter> implements GrowthTraceContract.View
        , View.OnClickListener, OnChartValueSelectedListener {
    @BindView(R.id.tv_student_info)
    TextView tvInfo;

    @BindView(R.id.chart)
    LineChart chart;

    @Inject
    GrowthTraceItem data;
    @Inject
    @Named(GrowthTraceContract.View.KEY_SUBJ_NAME)
    String subjName;

    @Inject
    KProgressHUD progressHUD;

    public static void launchActivity(Context context, GrowthTraceItem data, String subjectId,String subjName){
        Intent intent = new Intent(context, GrowthTraceActivity.class);
        intent.putExtra(GrowthTraceContract.View.KEY_DATA,data);
        intent.putExtra(GrowthTraceContract.View.KEY_SUBJ,subjectId);
        intent.putExtra(GrowthTraceContract.View.KEY_SUBJ_NAME,subjName);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGrowthTraceComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_growth_trace; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initChart();
        bindInfo();
        mPresenter.getData();
    }

    private void bindInfo(){
        if(data != null){
            setTitle(StringCocatUtil.concat(data.getRealName(),"成长轨迹"));
            tvInfo.setText(StringCocatUtil.concat("年级：",data.getGradeName(),"   班级：",data.getClassName(),"   学科：",subjName));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
        }
    }

    @Override
    public void showLoading() {
        if(progressHUD!=null){
            if(progressHUD.isShowing()){
                progressHUD.dismiss();
            }
            progressHUD.show();
        }
    }

    @Override
    public void hideLoading() {
        if(progressHUD!=null){
            progressHUD.dismiss();
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
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(progressHUD != null){
            progressHUD.dismiss();
        }
    }

    private void initChart(){
        int color = ContextCompat.getColor(this,R.color.text_999);
        chart.setNoDataText("暂无数据");
        chart.setBackgroundColor(Color.WHITE);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setOnChartValueSelectedListener(this);
        chart.setDrawGridBackground(false);

        MarkerViewGrowth mv = new MarkerViewGrowth(this, R.layout.marker_grade);
        // Set the marker to the chart
        mv.setChartView(chart);
        chart.setMarker(mv);

        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        // chart.setScaleXEnabled(true);
        // chart.setScaleYEnabled(true);
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
                            StudentImprove grade = (StudentImprove)entries.get(index).getData();
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

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setTextColor(color);
        l.setTextSize(12);
    }

    public void setData(List<Entry> listMy,List<Entry> listClass) {
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

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
