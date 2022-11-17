package com.example.df.zhiyun.app.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

public class CompoundDrawableUtil {
    public static void setCompoundDrawable(Context context, TextView view, int left,int top,int right,int bottom){
        Drawable drawableLeft = null;
        Drawable drawableTop = null;
        Drawable drawableRight = null;
        Drawable drawableBottom = null;

        if(left > 0){
            drawableLeft = ContextCompat.getDrawable(context, left);
            drawableLeft.setBounds(0,0,drawableLeft.getIntrinsicWidth(),drawableLeft.getIntrinsicHeight());
        }

        if(top > 0){
            drawableTop = ContextCompat.getDrawable(context, top);
            drawableTop.setBounds(0,0,drawableTop.getIntrinsicWidth(),drawableTop.getIntrinsicHeight());
        }

        if(right > 0){
            drawableRight = ContextCompat.getDrawable(context, right);
            drawableRight.setBounds(0,0,drawableRight.getIntrinsicWidth(),drawableRight.getIntrinsicHeight());
        }

        if(bottom > 0){
            drawableBottom = ContextCompat.getDrawable(context, bottom);
            drawableBottom.setBounds(0,0,drawableBottom.getIntrinsicWidth(),drawableBottom.getIntrinsicHeight());
        }

        view.setCompoundDrawables(drawableLeft,drawableTop, drawableRight,drawableBottom);
    }
}
