package com.example.df.zhiyun.mvp.ui.widget;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.mvp.model.entity.Grade;
import com.example.df.zhiyun.mvp.model.entity.StudentImprove;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class MarkerViewGrowth extends MarkerView {

    private final TextView tvPaperName;
    private final TextView tvScoreMy;
    private final TextView tvScoreAvg;
    private final TextView tvFullScore;
    private final TextView tvStartTime;
    private final ImageView imageView;

    public MarkerViewGrowth(Context context, int layoutResource) {
        super(context, layoutResource);

        tvPaperName = findViewById(R.id.tv_paper_name);
        tvScoreMy = findViewById(R.id.tv_my_score);
        tvScoreAvg = findViewById(R.id.tv_avg_score);
        tvStartTime = findViewById(R.id.tv_start_time);
        tvFullScore = findViewById(R.id.tv_full_score);
        imageView = findViewById(R.id.iv_bg);
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        StudentImprove point = (StudentImprove)e.getData();
        tvPaperName.setText(point.getHomework_name());
        tvScoreMy.setText(StringCocatUtil.concat("学生得分：",String.format("%.1f",point.getStudentScore()),"分"));
        tvScoreAvg.setText(StringCocatUtil.concat("平均分：",String.format("%.1f",point.getAvg()),"分"));
        tvFullScore.setText(StringCocatUtil.concat("满分：",String.format("%.0f",point.getScore()),"分"));
        tvStartTime.setText(StringCocatUtil.concat("开始时间：", TimeUtils.getYmdhms(point.getOpenTime())));
        imageView.setImageResource(R.mipmap.mark_blue);
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight()-40);
    }
}
