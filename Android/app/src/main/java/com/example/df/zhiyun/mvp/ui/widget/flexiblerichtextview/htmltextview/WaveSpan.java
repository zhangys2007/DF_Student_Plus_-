package com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/**
 * 波浪线下划线效果
 */
public class WaveSpan extends MetricAffectingSpan {
    private int mLineColor;
    private int mWidth;

    public WaveSpan(int lineColor){
        mLineColor = lineColor;
    }

    @Override
    public void updateMeasureState(@NonNull TextPaint textPaint) {

    }

    @Override
    public void updateDrawState(TextPaint tp) {

    }

    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        mWidth = (int) paint.measureText(text.subSequence(start,end).toString());
        return mWidth;
    }

//
//    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
//        canvas.drawLine(x,y+10,x+mWidth,y+10,paint);
//        canvas.drawText(text.subSequence(start,end).toString(),x,y,paint);
//    }
}
