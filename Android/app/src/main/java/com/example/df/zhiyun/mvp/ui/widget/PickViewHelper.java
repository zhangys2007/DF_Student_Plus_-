package com.example.df.zhiyun.mvp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.example.df.zhiyun.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by pc on 2017/7/27.
 */

public class PickViewHelper{
    public static OptionsPickerView getOptionPicker(Context context, List<String> options,OnOptionsSelectListener listener){
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, listener)
                .setSelectOptions(0)//设置选择第一个
                .setOutSideCancelable(true)//点击背的地方不消失
                .build();//创建
        pvOptions.setPicker(options);
        return pvOptions;
    }

    public static TimePickerView showBirthdayPicker(Context context,long initTime ,OnTimeSelectListener listener){
        Calendar selectedDate = Calendar.getInstance();
        if(initTime > 0){
            selectedDate.setTimeInMillis(initTime);
        }
        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.YEAR,1900);
        startDate.set(Calendar.MONTH,0);
        startDate.set(Calendar.DATE,0);
        TimePickerView pvTime = new TimePickerBuilder(context,listener)
                .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                .setSubmitText("完成")//确认按钮文字
                .setContentTextSize(14)//滚轮文字大小
                .setTitleSize(20)//标题文字大小

                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setSubmitColor(ContextCompat.getColor(context, R.color.blue))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(context, R.color.blue))//取消按钮文字颜色
                .setTitleBgColor(ContextCompat.getColor(context, R.color.bg_grey))//标题背景颜色 Night mode
                .setRangDate(startDate,selectedDate)
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 默认是系统时间*/
                .setLabel("年","月","日","时","分","秒")
                .build();
        return pvTime;
    }

    //显示测量时间选择器
    public static TimePickerView getTimePicker(Context context, long initTime , OnTimeSelectListener listener){
        Calendar selectedDate = Calendar.getInstance();
        if(initTime > 0){
            selectedDate.setTimeInMillis(initTime);
        }

        TimePickerView  pvTime = new TimePickerBuilder(context, listener)
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setDate(selectedDate)
                .setCancelText("取消")
                .setSubmitText("确定")
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
        return pvTime;
    }


    public static TimePickerView getDayPicker(Context context, long initTime , OnTimeSelectListener listener){
        Calendar selectedDate = Calendar.getInstance();
        if(initTime > 0){
            selectedDate.setTimeInMillis(initTime);
        }

        TimePickerView  pvTime = new TimePickerBuilder(context, listener)
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setDate(selectedDate)
                .setCancelText("取消")
                .setSubmitText("确定")
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
        return pvTime;
    }

    /**
     * 三级联动的
     * @return
     */
    public static OptionsPickerView getOptionsPickerView3(Context context, OnOptionsSelectListener selectListener, OnOptionsSelectChangeListener changeListener,
                                                          String title, String[] tags, List<String> op1, List<List<String>> op2, List<List<List<String>>> op3){
        OptionsPickerView pvOptions = new  OptionsPickerBuilder(context, selectListener) .setOptionsSelectChangeListener(changeListener)
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText(title)//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(ContextCompat.getColor(context, R.color.blue))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(context, R.color.blue))//取消按钮文字颜色
                .setTitleBgColor(ContextCompat.getColor(context, R.color.bg_grey))//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(14)//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
//                .setLabels(tags[0], tags[1], tags[2])//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

        pvOptions.setPicker(op1, op2, op3);//添加数据源
        return pvOptions;
    }

    public static OptionsPickerView getOptionsPickerView2(Context context, OnOptionsSelectListener selectListener, OnOptionsSelectChangeListener changeListener,
                                                          String title, String[] tags, List<String> op1, List<List<String>> op2){
        OptionsPickerView pvOptions = new  OptionsPickerBuilder(context, selectListener) .setOptionsSelectChangeListener(changeListener)
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText(title)//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(ContextCompat.getColor(context, R.color.blue))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(context, R.color.blue))//取消按钮文字颜色
                .setTitleBgColor(ContextCompat.getColor(context, R.color.bg_grey))//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(14)//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
//                .setLabels(tags[0], tags[1],null)//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

        pvOptions.setPicker(op1, op2);//添加数据源
        return pvOptions;
    }
}
