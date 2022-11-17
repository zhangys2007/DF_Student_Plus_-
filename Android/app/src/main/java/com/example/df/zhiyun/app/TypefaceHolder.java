package com.example.df.zhiyun.app;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

public class TypefaceHolder {
    private Context mContext;
    private Typeface tfFS;  //仿宋
    private Typeface tfKT;  //楷体
    private Typeface tfHT;  //黑体
    private Typeface tfSS;  //书宋
    private Typeface tfTNR;  //times new roma

    private TypefaceHolder(){}

    private static TypefaceHolder instance;

    public static TypefaceHolder getInstance(){
        if(instance == null){
            synchronized (TypefaceHolder.class){
                if(instance == null){
                    instance = new TypefaceHolder();
                }
            }
        }
        return instance;
    }

    /**
     * 获取字体，并设置全局字体为楷体
     * @param context
     */
    public void init(Context context){
        mContext = context.getApplicationContext();

        try{
            Field field = Typeface.class.getDeclaredField("MONOSPACE");
            field.setAccessible(true);
            field.set(null,getTfHT());
        }catch (NoSuchFieldException e){

        }catch (IllegalAccessException e){

        }
    }

    private Typeface getTfTNR(){
        if(tfTNR == null){
            tfTNR = Typeface.createFromAsset(mContext.getAssets(), "fonts/TNR.TTF");
        }
        return tfTNR;
    }

    private Typeface getTfFS(){
        if(tfFS == null){
            tfFS = Typeface.createFromAsset(mContext.getAssets(), "fonts/FZFSJW.TTF");
        }
        return tfFS;
    }

    private Typeface getTfHT(){
        if(tfHT == null){
            tfHT = Typeface.createFromAsset(mContext.getAssets(), "fonts/FZHTJW.TTF");
        }
        return tfHT;
    }

    private Typeface getTfKT(){
        if(tfKT == null){
            tfKT = Typeface.createFromAsset(mContext.getAssets(), "fonts/FZKTJW.TTF");
        }
        return tfKT;
    }

    private Typeface getTfSS(){
        if(tfSS == null){
            tfSS = Typeface.createFromAsset(mContext.getAssets(), "fonts/FZSSJW.TTF");
        }
        return tfSS;
    }


    //根据名字获得特定的字体
    public Typeface getSpecialTypeface(String fontFamily){
        if(fontFamily != null && fontFamily.contains("黑体")){
            return getTfHT();
        }else if(fontFamily != null && fontFamily.contains("楷体")){
            return getTfKT();
        }else if(fontFamily != null && fontFamily.contains("仿宋")){
            return getTfFS();
        }else if(fontFamily != null && (fontFamily.contains("书宋") || fontFamily.contains("宋体"))){
            return getTfSS();
        }else if(fontFamily != null && fontFamily.contains("Times")){
            return getTfTNR();
        }else{
            return null;
        }
    }

    /**
     * 设置字体为黑体
     * @param textView
     */
    public void setHt(TextView textView){
        if(textView == null){
            return;
        }

        textView.setTypeface(getTfHT());
    }

    public void setEditTextHt(EditText editText){
        if(editText == null){
            return;
        }

        editText.setTypeface(getTfHT());
    }

    /**
     * 设置字体为仿宋
     * @param textView
     */
    public void setFs(TextView textView){
        if(textView == null){
            return;
        }

        textView.setTypeface(getTfFS());
    }

    public void setEditTextFs(EditText editText){
        if(editText == null){
            return;
        }

        editText.setTypeface(getTfFS());
    }

    /**
     * 设置字体为楷体
     * @param textView
     */
    public void setKt(TextView textView){
        if(textView == null){
            return;
        }

        textView.setTypeface(getTfKT());
    }

    public void setKt(EditText editText){
        if(editText == null){
            return;
        }

        editText.setTypeface(getTfKT());
    }

    /**
     * 设置字体为书宋
     * @param textView
     */
    public void setSS(TextView textView){
        if(textView == null){
            return;
        }

        textView.setTypeface(getTfSS());
    }

    public void setSS(EditText editText){
        if(editText == null){
            return;
        }

        editText.setTypeface(getTfSS());
    }


    /**
     * 设置字体为times new roma
     * @param textView
     */
    public void setTNR(TextView textView){
        if(textView == null){
            return;
        }

        textView.setTypeface(getTfTNR());
    }

    public void setTNR(EditText editText){
        if(editText == null){
            return;
        }

        editText.setTypeface(getTfTNR());
    }
}
