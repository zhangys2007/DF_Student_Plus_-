package com.example.df.zhiyun.mvp.ui.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 用于转换mathml为latex
 */
public class MathMlTransformer {
    private static MathMlTransformer mInstance;
    private Transformer trans;

    private MathMlTransformer(){}

    public static MathMlTransformer getInstance(){
        if (mInstance == null){
            synchronized (MathMlTransformer.class){
                if(mInstance == null){
                    mInstance = new MathMlTransformer();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context){
        try {
            AssetManager am = context.getResources().getAssets();
            Source xsltSource = new StreamSource(am.open("mmltex.xsl"));

            TransformerFactory transFact = TransformerFactory.newInstance();
            trans = transFact.newTransformer(xsltSource);
        } catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TransformerFactoryConfigurationError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String translate(Context context,String value){
        if(trans == null){
            init(context);
        }

        try {
            if(TextUtils.isEmpty(value)){
                return "";
            }
            Source xmlSource = new StreamSource(new ByteArrayInputStream(value.getBytes()));

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(output);
            trans.transform(xmlSource, result);
            return output.toString();
        } catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        } catch (TransformerFactoryConfigurationError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }
}
