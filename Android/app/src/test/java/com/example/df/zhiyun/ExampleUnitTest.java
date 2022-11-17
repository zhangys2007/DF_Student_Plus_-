package com.example.df.zhiyun;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.ui.widget.CanvasDialog;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;
import com.google.gson.Gson;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void navigationtype_isCorrect() throws Exception {
        int index = 0 , total = 1;
        assertEquals(ViewLastNextInitHelper.TYPE_FIRST, ViewLastNextInitHelper.parseNavigatioType(0,3,ViewLastNextInitHelper.TYPE_NONE));
        assertEquals(ViewLastNextInitHelper.TYPE_LAST, ViewLastNextInitHelper.parseNavigatioType(2,3,ViewLastNextInitHelper.TYPE_NONE));
        assertEquals(ViewLastNextInitHelper.TYPE_ONLY, ViewLastNextInitHelper.parseNavigatioType(0,1,ViewLastNextInitHelper.TYPE_NONE));
        assertEquals(ViewLastNextInitHelper.TYPE_MIDDLE, ViewLastNextInitHelper.parseNavigatioType(1,3,ViewLastNextInitHelper.TYPE_NONE));

        assertEquals(ViewLastNextInitHelper.TYPE_FIRST, ViewLastNextInitHelper.parseNavigatioType(0,3,ViewLastNextInitHelper.TYPE_FIRST));
        assertEquals(ViewLastNextInitHelper.TYPE_MIDDLE, ViewLastNextInitHelper.parseNavigatioType(2,3,ViewLastNextInitHelper.TYPE_FIRST));
        assertEquals(ViewLastNextInitHelper.TYPE_FIRST, ViewLastNextInitHelper.parseNavigatioType(0,1,ViewLastNextInitHelper.TYPE_FIRST));
        assertEquals(ViewLastNextInitHelper.TYPE_MIDDLE, ViewLastNextInitHelper.parseNavigatioType(1,3,ViewLastNextInitHelper.TYPE_FIRST));

        assertEquals(ViewLastNextInitHelper.TYPE_MIDDLE, ViewLastNextInitHelper.parseNavigatioType(0,3,ViewLastNextInitHelper.TYPE_LAST));
        assertEquals(ViewLastNextInitHelper.TYPE_LAST, ViewLastNextInitHelper.parseNavigatioType(2,3,ViewLastNextInitHelper.TYPE_LAST));
        assertEquals(ViewLastNextInitHelper.TYPE_LAST, ViewLastNextInitHelper.parseNavigatioType(0,1,ViewLastNextInitHelper.TYPE_LAST));
        assertEquals(ViewLastNextInitHelper.TYPE_MIDDLE, ViewLastNextInitHelper.parseNavigatioType(1,3,ViewLastNextInitHelper.TYPE_LAST));

        assertEquals(ViewLastNextInitHelper.TYPE_MIDDLE, ViewLastNextInitHelper.parseNavigatioType(0,3,ViewLastNextInitHelper.TYPE_MIDDLE));
        assertEquals(ViewLastNextInitHelper.TYPE_MIDDLE, ViewLastNextInitHelper.parseNavigatioType(2,3,ViewLastNextInitHelper.TYPE_MIDDLE));
        assertEquals(ViewLastNextInitHelper.TYPE_MIDDLE, ViewLastNextInitHelper.parseNavigatioType(0,1,ViewLastNextInitHelper.TYPE_MIDDLE));
        assertEquals(ViewLastNextInitHelper.TYPE_MIDDLE, ViewLastNextInitHelper.parseNavigatioType(1,3,ViewLastNextInitHelper.TYPE_MIDDLE));

        assertEquals(ViewLastNextInitHelper.TYPE_FIRST, ViewLastNextInitHelper.parseNavigatioType(0,3,ViewLastNextInitHelper.TYPE_ONLY));
        assertEquals(ViewLastNextInitHelper.TYPE_LAST, ViewLastNextInitHelper.parseNavigatioType(2,3,ViewLastNextInitHelper.TYPE_ONLY));
        assertEquals(ViewLastNextInitHelper.TYPE_ONLY, ViewLastNextInitHelper.parseNavigatioType(0,1,ViewLastNextInitHelper.TYPE_ONLY));
        assertEquals(ViewLastNextInitHelper.TYPE_MIDDLE, ViewLastNextInitHelper.parseNavigatioType(1,3,ViewLastNextInitHelper.TYPE_ONLY));
    }


    @Test
    public void base64Decode_isCorrect() throws Exception {
        String BASE64_HEAD = "data:image/png;base64,";
        String data ="";
        Bitmap thumb = null;
        try {
            data = data.replace(BASE64_HEAD,"");
            byte[] decodedString = Base64.decode(data, Base64.DEFAULT);
            thumb = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }catch (Exception e){

        }

        assertEquals(thumb,null);
    }

    @Test
    public void url_format() throws Exception {
        String url = String.format("%s?id=%s&&classId=%s","http://baidu.com","1","2");
        assertEquals(url,"http://baidu.com?id=1&&classId=2");
    }


    @Test
    public void replace_p() throws Exception {
        String url = "<p class=\"ql-align-justify\">下列各项中，标点符号使用规范的一项是</p>";

        assertEquals(url.replace("<p class=\"ql-align-justify\">","")
                .replace("</p>",""),"下列各项中，标点符号使用规范的一项是");
    }

    @Test
    public void scale_test() throws Exception {
        float scale = CanvasDialog.calculateScale(640,480);
        System.out.println("scale0:"+scale);


        scale = CanvasDialog.calculateScale(720,480);
        System.out.println("scale1:"+scale);

        scale = CanvasDialog.calculateScale(0,0);
        System.out.println("scale2:"+scale);

        scale = CanvasDialog.calculateScale(840,480);
        System.out.println("scale3:"+scale);

        scale = CanvasDialog.calculateScale(640,560);
        System.out.println("scale4:"+scale);

        scale = CanvasDialog.calculateScale(1280,960);
        System.out.println("scale5:"+scale);

        scale = CanvasDialog.calculateScale(1280,1080);
        System.out.println("scale6:"+scale);

        scale = CanvasDialog.calculateScale(480,320);
        System.out.println("scale7:"+scale);

        assertEquals(1,1);
    }

    @Test
    public void answer_parse_test() throws Exception {
        Answer answer = new Answer();
        answer.setQuestionId("1");

        List<Answer> subAnserList = new ArrayList<>();
        answer.setSubAnswer(subAnserList);

        Answer subanswer = new Answer();
        subanswer.setQuestionId("1-1");
        List<String> a = new ArrayList<>();
        a.add("A");
        a.add("b");
        subanswer.setAnswer(a);
        subAnserList.add(subanswer);

        subanswer = new Answer();
        subanswer.setQuestionId("1-2");
        a = new ArrayList<>();
        a.add("C");
        subanswer.setAnswer(a);
        subAnserList.add(subanswer);

        subanswer = new Answer();
        subAnserList.add(subanswer);
        subanswer.setQuestionId("1-3");
        List<Answer> subsubAnserList = new ArrayList<>();
        subanswer.setSubAnswer(subsubAnserList);

        Answer subsubanswer = new Answer();
        subsubanswer.setQuestionId("1-3-1");
        a = new ArrayList<>();
        a.add("C");
        subsubanswer.setAnswer(a);
        subsubAnserList.add(subsubanswer);

        subsubanswer = new Answer();
        subsubanswer.setQuestionId("1-3-2");
        a = new ArrayList<>();
        a.add("D");
        subsubanswer.setAnswer(a);
        subsubAnserList.add(subsubanswer);


        Map<String,Object> params = new HashMap<>();
        params.put("questionId", answer.getQuestionId());
        parseAnswer(params,answer);

        Gson gson = new Gson();
        String gsonStr = gson.toJson(params);
        System.out.println(gsonStr);
        assertEquals(1,1);
    }

    private void parseAnswer(Map<String,Object> params,  Answer answer){
        if(answer.getSubAnswer() != null){
            List<Map<String,Object>> subList = new ArrayList<>();
            Map<String,Object> tempSub;
            for(Answer subAnswer: answer.getSubAnswer()){
                tempSub = new HashMap<>();
                tempSub.put("questionId",subAnswer.getQuestionId());
                parseAnswer(tempSub,subAnswer);
                subList.add(tempSub);
            }
            params.put("answer",subList);
        }else{
            params.put("answer",answer.getAnswer());
        }
    }


    @Test
    public void testCorrectParam() throws Exception{
        Question q = new Question();
        q.setScorreChanged(true);

        Question q1 = new Question();
        q1.setScorreChanged(true);

        Question q2 = new Question();
        q2.setScorreChanged(true);

        Question q11 = new Question();
        q11.setScorreChanged(true);

        Question q12 = new Question();
        q12.setScorreChanged(true);

        Question q21 = new Question();
        q21.setScorreChanged(true);

        List<Question> sub0 = new ArrayList<>();
        sub0.add(q1);
        sub0.add(q2);
        q.setSubQuestion(sub0);

        List<Question> sub1 = new ArrayList<>();
        sub1.add(q11);
        sub1.add(q12);
        q1.setSubQuestion(sub1);

        List<Question> sub2 = new ArrayList<>();
        sub2.add(q21);
        q2.setSubQuestion(sub2);

        assertEquals(2,parseCorrectStatus(q));
    }

    @Test
    public void testFormat() throws Exception{
        float score = 7.643f;
        String strScore = String.format("%.1f",score);
        assertEquals("7.6",strScore);

        strScore = String.format("%.2f",score);
        assertEquals("7.64",strScore);
    }

    @Test
    public void testCorrectStatus() throws Exception{
        Question q = new Question();
        q.setScorreChanged(true);

        Question q1 = new Question();
        q1.setScorreChanged(true);

        Question q2 = new Question();
        q2.setScorreChanged(true);

        Question q11 = new Question();
        q11.setScorreChanged(true);

        Question q12 = new Question();
        q12.setScorreChanged(true);

        Question q21 = new Question();
        q21.setScorreChanged(true);

        List<Question> sub0 = new ArrayList<>();
        sub0.add(q1);
        sub0.add(q2);
        q.setSubQuestion(sub0);

        List<Question> sub1 = new ArrayList<>();
        sub1.add(q11);
        sub1.add(q12);
        q1.setSubQuestion(sub1);

        List<Question> sub2 = new ArrayList<>();
        sub2.add(q21);
        q2.setSubQuestion(sub2);

        assertEquals(2,parseCorrectStatus(q));

        q11.setScorreChanged(false);
        assertEquals(1,parseCorrectStatus(q));

        q12.setScorreChanged(false);
        q21.setScorreChanged(false);
        assertEquals(0,parseCorrectStatus(q));

        q1.setSubQuestion(null);
        assertEquals(1,parseCorrectStatus(q));

        q.setSubQuestion(null);
        assertEquals(2,parseCorrectStatus(q));

        q.setScorreChanged(false);
        assertEquals(0,parseCorrectStatus(q));
    }

    private void markeCorrectStatus(Question question, List<Boolean> value){
        if(question.getSubQuestion() != null && question.getSubQuestion().size() > 0){
            for(Question subq: question.getSubQuestion()){
                markeCorrectStatus(subq,value);
            }
        }else{
            value.add(question.isScorreChanged());
        }
    }

    private int parseCorrectStatus(Question question){
        if(question == null){
            return 0;
        }

        List<Boolean> listStatus = new ArrayList<>();
        markeCorrectStatus(question,listStatus);

        boolean hasCorrectItem = false;
        boolean hasUnCorrectItem = false;

        for(Boolean value: listStatus){
            if(value){
                hasCorrectItem = true;
            }else{
                hasUnCorrectItem = true;
            }
        }

        if(hasCorrectItem && !hasUnCorrectItem){
            return 2;
        }else if(hasCorrectItem && hasUnCorrectItem){
            return 1;
        }else{
            return 0;
        }
    }
}