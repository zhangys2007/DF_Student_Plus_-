package com.example.df.zhiyun.mvp.ui.widget;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.contract.INavigation;

public class ViewLastNextInitHelper {
    public static final String KEY_NAVIGATION = "navigation";
    public static final int TYPE_NONE = 0;      //未设置
    public static final int TYPE_FIRST = 1;     //多个中的第一个
    public static final int TYPE_MIDDLE = 2;    //多个中的中间个
    public static final int TYPE_LAST = 3;      //多个中的最后一个
    public static final int TYPE_ONLY = 4;   //单独一个

    LinearLayout llBar;
    TextView tvLast;
    TextView tvNext;

    INavigation mNavigator;

    public void init(Fragment fragment,INavigation navigator,int type, boolean isPreview){
        mNavigator = navigator;

        View view = fragment.getView();
        llBar = (LinearLayout) view.findViewById(R.id.ll_bar_last_next);
        tvLast = (TextView) view.findViewById(R.id.tv_last);
        tvNext = (TextView) view.findViewById(R.id.tv_next);
        initLastNext(type, isPreview);
    }

    private void initLastNext(int type, boolean  isPreview){

        if(type == TYPE_FIRST || type == TYPE_ONLY){
            tvLast.setVisibility(View.INVISIBLE);
        }

        if(type == TYPE_ONLY || type == TYPE_LAST){
            if(isPreview){
                tvNext.setVisibility(View.INVISIBLE);
            }
            tvNext.setText(R.string.do_submit);
            tvNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNavigator.submitPaper();
                }
            });
        }else{
            tvNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNavigator.nextQuestion();
                }
            });
        }

        tvLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.lastQuestion();
            }
        });
    }


    public static final int parseNavigatioType(int index, int total, int parentType){
        int type =  parseType(index,total);

        if(type == TYPE_FIRST && parentType != TYPE_FIRST && parentType != TYPE_ONLY && parentType != TYPE_NONE){
            type = TYPE_MIDDLE;
        }else if(type == TYPE_LAST && parentType != TYPE_LAST && parentType != TYPE_ONLY && parentType != TYPE_NONE){
            type = TYPE_MIDDLE;
        }else if(type == TYPE_ONLY && parentType != TYPE_NONE){
            type = parentType;
        }

        return type;
    }

    private static final int parseType(int index, int total){
        if(total == 1){
            return TYPE_ONLY;
        }else if(index == 0){
            return TYPE_FIRST;
        }else if(index == total-1){
            return TYPE_LAST;
        }else {
            return TYPE_MIDDLE;
        }
    }
}
