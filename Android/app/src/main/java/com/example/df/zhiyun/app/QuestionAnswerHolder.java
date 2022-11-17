package com.example.df.zhiyun.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.example.df.zhiyun.app.utils.FileUtils;
import com.example.df.zhiyun.app.utils.SerializableUtil;
import com.example.df.zhiyun.mvp.contract.IQuestionLookup;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.HomeworkSet;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;

import timber.log.Timber;

/***
 * 在SetHomeworkActivity中保存的答案和问题太大，没办法传到CardActivity中，或者传到fragment中 ，用这个类来保存答案和题目
 */
public class QuestionAnswerHolder {
    private static final String KEY_CACHE = "answer_cache";
    private static final String CACHE_NAME = "cache.txt";
    private  Map<Integer, Answer> mAnswers = new HashMap<>();
    private HomeworkSet mHomework;
    private static QuestionAnswerHolder mInstance;

    private QuestionAnswerHolder(){}

    public static QuestionAnswerHolder getInstance(){
        if(mInstance == null){
            synchronized (QuestionAnswerHolder.class){
                if(mInstance == null){
                    mInstance = new QuestionAnswerHolder();
                }
            }
        }
        return mInstance;
    }


    /**
     * 去除列表里的空问题，防止崩溃
     * @param hw
     */
    public void setHomework(HomeworkSet hw){
        mHomework = hw;
        List<Question> questions = mHomework.getList();
        if(questions != null){
            Iterator<Question> iterator = questions.iterator();
            while (iterator.hasNext()){
                Question temp = iterator.next();
                if(temp == null){
                    iterator.remove();
                }
            }
        }
    }

    public HomeworkSet getHomework(){
        return mHomework;
    }

    public Answer getAnswerByIndex(int index){
        return mAnswers.get(index);
    }

    public void saveAnswerByIndex(int index,Answer answer){
        mAnswers.put(index,answer);
    }

    public void setAnswers(Map<Integer, Answer> newData){
        this.mAnswers = newData;
    }


    public void onCreate(Context context, Bundle savedInstanceState){
        if(savedInstanceState != null && savedInstanceState.getInt(KEY_CACHE,0) != 0){
            ExecutorService executorService =  ArmsUtils.obtainAppComponentFromContext(context.getApplicationContext()).executorService();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    File cacheDir = DataHelper.getCacheFile(context.getApplicationContext());
                    File cacheFile = new File(cacheDir,CACHE_NAME);
                    if(cacheFile.exists()){
                        try {
                            FileInputStream fileIn = context.getApplicationContext().openFileInput(cacheFile.getPath());
                            ObjectInputStream in = new ObjectInputStream(fileIn);
                            mAnswers = (Map<Integer, Answer>) in.readObject();
                            cacheFile.delete();
                            Timber.tag("holder").d("load answer ok");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public void onSaveInstanceState(Context context,Bundle outState){
        if(mAnswers != null && mAnswers.size()>0){
            ExecutorService executorService =  ArmsUtils.obtainAppComponentFromContext(context.getApplicationContext()).executorService();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    File cacheDir = DataHelper.getCacheFile(context.getApplicationContext());
                    File cacheFile = new File(cacheDir,CACHE_NAME);
                    if(cacheFile.exists()){
                        cacheFile.delete();
                    }
                    try {
                        FileOutputStream outputStream = context.getApplicationContext()
                                .openFileOutput(cacheFile.getPath(),Context.MODE_PRIVATE);
                        ObjectOutputStream out = new ObjectOutputStream(outputStream);
                        out.writeObject(mAnswers);
                        outputStream.close();
                        outState.putString(KEY_CACHE,mHomework.getStudentHomeWorkId());
                        Timber.tag("holder").d("write answer ok");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void onDestory(Context context){
        mAnswers.clear();
        mHomework = null;
    }

    public Answer getAnswer(Fragment fragment){
        int index = ((IQuestionLookup)fragment).getQuestionIndex();
        Fragment parent = fragment.getParentFragment();
        if(parent != null){
            int parentIndex = ((IQuestionLookup)parent).getQuestionIndex();
            Answer parentAnswer = mAnswers.get(parentIndex);
            if(parentAnswer != null){
                List<Answer> subAnswers = parentAnswer.getSubAnswer();
                if(subAnswers != null && subAnswers.size()>index){
                    return subAnswers.get(index);
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }else{
            return mAnswers.get(index);
        }
    }

    public Question getQuestion(Fragment fragment){
        int index = ((IQuestionLookup)fragment).getQuestionIndex();
        Fragment parent = fragment.getParentFragment();
        if(parent != null){
            int parentIndex = ((IQuestionLookup)parent).getQuestionIndex();
            return getQuestionByIndexArray(new int[]{parentIndex,index});
        }else{
            return getQuestionByIndexArray(new int[]{index});
        }
    }

    //现在只有两层
    private Question getQuestionByIndexArray(int[] indexArray){
        Question targetQuestion = null;
        if(indexArray == null || indexArray.length == 0 || mHomework == null || mHomework.getList() == null){
            return null;
        }

        targetQuestion = getQuestionFromList(mHomework.getList(),indexArray[0]);
        if(indexArray.length > 1 && targetQuestion.getSubQuestion() != null){
            return getQuestionFromList(targetQuestion.getSubQuestion(),indexArray[1]);
        }else{
            return targetQuestion;
        }
    }

    private Question getQuestionFromList(List<Question> source,int index){
        if(index >= source.size()){
            return null;
        }else{
            return source.get(index);
        }
    }

    /**
     * 把某道题的答案放入file中  cache/answer/16（用户id）/12345（homework id）/2 （第几题）
     * @param context
     * @param index
     */
    public void saveQuestionAnswertoFile(Context context,int index){
        if(mHomework == null || mAnswers == null || !mAnswers.containsKey(index)){
            return;
        }

        File cachDir = new File(context.getCacheDir(),"answer");
        if(!cachDir.exists()){
            DataHelper.makeDirs(cachDir);
        }

        File userDir = new File(cachDir,AccountManager.getInstance().getUserInfo().getUserId());
        if(!userDir.exists()){
            DataHelper.makeDirs(userDir);
        }

        File homeworkDir = new File(userDir,mHomework.getStudentHomeWorkId());
        if(!homeworkDir.exists()){
            DataHelper.makeDirs(homeworkDir);
        }

        File answerFile = new File(homeworkDir,Integer.toString(index));
        try {
            SerializableUtil.write(mAnswers.get(index),answerFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从缓存的文件中取出之前的答案
     * @param context
     */
    public void restoreAnswersFromFile(Context context){
        if(mHomework == null || mHomework.getList() == null || mAnswers == null ){
            return;
        }
        File cachDir = new File(context.getCacheDir(),"answer");
        if(!cachDir.exists()){
            return;
        }

        File userDir = new File(cachDir,AccountManager.getInstance().getUserInfo().getUserId());
        if(!userDir.exists()){
            return;
        }

        File homeworkDir = new File(userDir,mHomework.getStudentHomeWorkId());
        if(!homeworkDir.exists()){
            return;
        }

        File[] files = homeworkDir.listFiles();
        Answer tempAnswer;
        for (File file : files) {
            int index = Integer.parseInt(file.getName());
            if(index < mHomework.getList().size()){
                try {
                    tempAnswer = (Answer) SerializableUtil.read(file);
                    if(tempAnswer != null){
                        mAnswers.put(index,tempAnswer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 去除当前用户保存某套题答案
     * @param context
     */
    public void removeAnswers(Context context){
        File cachDir = new File(context.getCacheDir(),"answer");
        if(!cachDir.exists()){
            return;
        }

        File userDir = new File(cachDir,AccountManager.getInstance().getUserInfo().getUserId());
        if(!userDir.exists()){
            return;
        }

        if(mHomework == null && TextUtils.isEmpty(mHomework.getStudentHomeWorkId())){
            return;
        }

        File homeworkDir = new File(userDir,mHomework.getStudentHomeWorkId());
        if(!homeworkDir.exists()){
            return;
        }
        FileUtils.deleteDir(homeworkDir);
    }

    /**
     * 去除当前用户保存的各套题答案
     * @param context
     */
    public void removeAllAnswers(Context context){
        File cachDir = new File(context.getCacheDir(),"answer");
        if(!cachDir.exists()){
            return;
        }

        File userDir = new File(cachDir,AccountManager.getInstance().getUserInfo().getUserId());
        if(!userDir.exists()){
            return;
        }

        FileUtils.deleteDir(userDir);
    }


    /***
     *
     * @param hasEmpty  对应的题没答案时是否要填充一个空的占位答案
     * @param source
     * @param questionList
     * @return
     */
    public static  ArrayList<Answer> makeAnswerListFromAnswerMap(boolean hasEmpty, Map<Integer, Answer> source, List<Question> questionList){
        ArrayList<Answer> items = new ArrayList<>();
        Answer temp ;
        for(int i=0;i<questionList.size();i++){
            temp = source.get(i);
            if(temp == null && hasEmpty){
                temp = new Answer();
                temp.setQuestionId(questionList.get(i).getQuestionId());
                items.add(temp);

                List<String> opAnswers = new ArrayList<>();
                List<Integer> types = new ArrayList<>();
                temp.setAnswer(opAnswers);
                temp.setTypes(types);
//                List<QuestionOption> opList = questionList.get(i).getOptionList();
//                if(opList != null){
//                    for(QuestionOption questionOption : opList){
//                        opAnswers.add("");
//                        types.add(0);
//                    }
//                }
            }else if(temp != null){
                items.add(temp);
            }
        }
        return items;
    }


}
