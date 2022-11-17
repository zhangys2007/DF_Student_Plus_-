package com.example.df.zhiyun.analy.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.analy.di.component.DaggerKnowledgeStatisticsComponent;
import com.example.df.zhiyun.mvp.model.entity.ComSubjCla;
import com.example.df.zhiyun.mvp.model.entity.Tag;
import com.example.df.zhiyun.mvp.ui.adapter.TagAdapter;
import com.example.df.zhiyun.mvp.ui.widget.ChartMarkerView;
import com.example.df.zhiyun.mvp.ui.widget.PopupWindowHelper;
import com.example.df.zhiyun.mvp.ui.widget.smartPopupWindow.HorizontalPosition;
import com.example.df.zhiyun.mvp.ui.widget.smartPopupWindow.SmartPopupWindow;
import com.example.df.zhiyun.mvp.ui.widget.smartPopupWindow.VerticalPosition;
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

import com.example.df.zhiyun.analy.mvp.contract.KnowledgeStatisticsContract;
import com.example.df.zhiyun.analy.mvp.presenter.KnowledgeStatisticsPresenter;

import com.example.df.zhiyun.R;
import com.kaopiz.kprogresshud.KProgressHUD;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 知识点统计
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 10:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class KnowledgeStatisticsActivity extends BaseActivity<KnowledgeStatisticsPresenter> implements KnowledgeStatisticsContract.View
        , View.OnClickListener, OnChartValueSelectedListener {
    @BindView(R.id.fl_class)
    FrameLayout flClass;
    @BindView(R.id.fl_subject)
    FrameLayout flSubject;

    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.tv_subject)
    TextView tvSubject;

    @BindView(R.id.chart)
    LineChart chart;

    SmartPopupWindow popupWindowClass;
    SmartPopupWindow popupWindowSubject;

    @Inject
    KProgressHUD progressHUD;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerKnowledgeStatisticsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_knowledge_statistics; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        flClass.setOnClickListener(this);
        flSubject.setOnClickListener(this);
        initChart();

        mPresenter.findFilterData();
    }

    @Override
    public void initFilterData(ComSubjCla data) {
        if(data.getClassList() != null && data.getClassList().size() > 0){
            tvClass.setText(data.getClassList().get(0).getTitle());
            TagAdapter claAdapter = new TagAdapter(data.getClassList());
            popupWindowClass = PopupWindowHelper.getFilterContentPopupWindow(flClass,claAdapter,itemClickListenerClass);
        }

        if(data.getSubjectList() != null && data.getSubjectList().size() > 0){
            tvSubject.setText(data.getSubjectList().get(0).getTitle());
            TagAdapter subjAdapter = new TagAdapter(data.getSubjectList());
            popupWindowSubject = PopupWindowHelper.getFilterContentPopupWindow(flSubject,subjAdapter,itemClickListenerSubj);
        }
    }

    private BaseQuickAdapter.OnItemClickListener itemClickListenerClass = new BaseQuickAdapter.OnItemClickListener(){
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Tag data = (Tag)adapter.getData().get(position);
            tvClass.setText(data.getTitle());
            if(popupWindowClass != null && popupWindowClass.isShowing()){
                popupWindowClass.dismiss();
            }
            mPresenter.changeClass(data.getValue());
            mPresenter.getKnowledgePoints();
        }
    };

    private BaseQuickAdapter.OnItemClickListener itemClickListenerSubj = new BaseQuickAdapter.OnItemClickListener(){
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Tag data = (Tag)adapter.getData().get(position);
            tvSubject.setText(data.getTitle());
            if(popupWindowSubject != null && popupWindowSubject.isShowing()){
                popupWindowSubject.dismiss();
            }
            mPresenter.changeSubject(data.getValue());
            mPresenter.getKnowledgePoints();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_class:
                showPopupWindow(flClass,popupWindowClass);
                break;
            case R.id.fl_subject:
                showPopupWindow(flSubject,popupWindowSubject);
                break;
            default:
                break;
        }
    }

    private void showPopupWindow(View anchor,SmartPopupWindow window){
        if(window == null){
            return;
        }
        if(window.isShowing()){
            window.dismiss();
        }
        window.showAtAnchorView(anchor
                , VerticalPosition.BELOW, HorizontalPosition.CENTER);
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
        ChartMarkerView mv = new ChartMarkerView(this, R.layout.custom_marker_view);

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
//            xAxis.enableGridDashedLine(10f, 10f, 0f);
//            xAxis.setValueFormatter(new ValueFormatter() {
//                @Override
//                public String getFormattedValue(float value) {
//                    int index = (int)value;
//                    if (chart.getData() != null &&
//                            chart.getData().getDataSetCount() > 0) {
//                        LineDataSet set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
//                        List<Entry> entries = set1.getValues();
//                        if(entries != null && entries.size()> index){
//                            return ((KnowledgePoint)entries.get(index).getData()).getName();
//                        }
//                    }
//                    return "";
//                }
//            });
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
        l.setFormSize(ArmsUtils.dip2px(this,10));
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setTextColor(color);
        l.setTextSize(12);
    }

    public void setData(List<Entry> entries) {
        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(entries, "得分率");
            set1.setDrawIcons(false);

            // draw dashed line
//            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.RED);
            set1.setCircleColor(Color.RED);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
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

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
            chart.notifyDataSetChanged();
            chart.invalidate();
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
