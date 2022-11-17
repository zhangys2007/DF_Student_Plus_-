package com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview;

import android.app.Dialog;
import android.text.style.ClickableSpan;
import android.view.View;


import com.example.df.zhiyun.mvp.ui.widget.GallaryDialog;

import java.util.ArrayList;
import java.util.List;

public class ClickableImageSpan extends ClickableSpan {
    private String url;

    public ClickableImageSpan(String url) {
        this.url = url;
    }

    @Override
    public void onClick(View widget) {
        // 进行图片点击之后的处理
        List<String> list = new ArrayList<>();
        list.add(url);
        Dialog mDialog = new GallaryDialog(widget.getContext(),0,list);
        mDialog.show();
    }
}
