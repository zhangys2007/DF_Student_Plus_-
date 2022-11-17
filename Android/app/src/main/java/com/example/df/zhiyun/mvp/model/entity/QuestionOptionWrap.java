package com.example.df.zhiyun.mvp.model.entity;

public class QuestionOptionWrap {
    private String selectionHTML;
    private String selectionText;

    public static QuestionOption conver2QuestionOption(QuestionOptionWrap source){
        if(source == null){
            return null;
        }
        QuestionOption target = new QuestionOption();
        target.setOptionContent(source.getSelectionHTML());
        target.setOption(source.getSelectionText());
        return target;
    }

    public String getSelectionHTML() {
        return selectionHTML;
    }

    public void setSelectionHTML(String selectionHTML) {
        this.selectionHTML = selectionHTML;
    }

    public String getSelectionText() {
        return selectionText;
    }

    public void setSelectionText(String selectionText) {
        this.selectionText = selectionText;
    }
}
