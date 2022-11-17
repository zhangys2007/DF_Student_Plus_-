package com.example.df.zhiyun.app.utils;

import android.text.TextUtils;

import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;

import com.example.df.zhiyun.mvp.ui.widget.HtmlHttpBase64ImageGetter;

public class RichTextViewHelper {

    public static void setContent(HtmlTextView htmlTextView,String content){
        if(content == null){
            return;
        }
        htmlTextView.setHtml(content.replace("\r\n","")
//                .replaceAll("<p(?:\\s[^>]*)?>[\\s]*(?:<span(\\s[^>]*)?>[\\s]*</span>)*[\\s]*</p>","")
                , new HtmlHttpBase64ImageGetter(htmlTextView,null,false));
    }

    /**
     * 去除p标签
     * @param htmlTextView
     * @param content
     */
    public static void setContentNoP(HtmlTextView htmlTextView,String content){
        if(content == null){
            return;
        }
        htmlTextView.setHtml(content
                , new HtmlHttpBase64ImageGetter(htmlTextView,null,false));
    }

    //设置steam 半角转换为全角，避免无故换行
    public static String ToDBC(String str) {
        if(str == null){
            return null;
        }
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)

                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    //science word 的json里有TopicNumber标签，可以把题号替换进去
    public static String makeQuestionTitle(String strNumb, String content){
        StringBuilder stringBuilder = new StringBuilder();
        if(!TextUtils.isEmpty(content)){
            if(content.contains("<TopicNumber>")){
                if(!TextUtils.isEmpty(strNumb)){
                    stringBuilder.append(content.replace("<TopicNumber>",strNumb));
                }else{
                    stringBuilder.append(content);
                }
            }else{
                if(!TextUtils.isEmpty(strNumb)){
                    stringBuilder.append(strNumb);
                }
                stringBuilder.append(removePTag(content));
            }
        }else{
            if(!TextUtils.isEmpty(strNumb)){
                stringBuilder.append(strNumb);
            }
        }
        return stringBuilder.toString();
    }

    public static String removePTag(String content){
        if(content == null){
            return null;
        }
        if(content.startsWith("<p>")){
            return content.replaceFirst("<p>","");
        }else{
            return content;
        }
    }
}
