package com.example.df.zhiyun.app;

import android.content.Context;
import android.text.TextUtils;

import com.example.df.zhiyun.app.utils.FileUtils;
import com.example.df.zhiyun.app.utils.SerializableUtil;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.CorrectContent;
import com.example.df.zhiyun.mvp.model.entity.HomeworkSet;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.jess.arms.utils.DataHelper;

import java.io.File;

/**
 * 用于老师的批改内容的缓存
 */
public class CorrectionStorageManager {

    /**
     * 从存储文件中恢复老师曾做的批改
     * @param context
     * @param homeworkSet
     */
    public static void restoreCorrection(Context context, HomeworkSet homeworkSet){
        if(homeworkSet == null || homeworkSet.getList() == null || homeworkSet.getList().size() == 0){
            return;
        }
        File cachDir = new File(context.getCacheDir(),"correct");
        if(!cachDir.exists()){
            return;
        }

        File userDir = new File(cachDir,AccountManager.getInstance().getUserInfo().getUserId());
        if(!userDir.exists()){
            return;
        }

        File homeworkDir = new File(userDir,homeworkSet.getStudentHomeWorkId());
        if(!homeworkDir.exists()){
            return;
        }

        for(Question question : homeworkSet.getList()){
            recursionRestoreCorrection(homeworkDir,question);
        }
    }

    /**
     * 递归恢复叶子节点问题的批改内容
     * @param fileHomework
     * @param question
     */
    private static void recursionRestoreCorrection(File fileHomework,Question question){
        if(question.getSubQuestion() == null || question.getSubQuestion().size() == 0){
            restoreQuestionItem(fileHomework,question);
        }else{
            for(Question subQuestion:question.getSubQuestion()){
                recursionRestoreCorrection(fileHomework,subQuestion);
            }
        }
    }

    /**
     * 这是对具体某个问题批改内容的恢复, 如果是已批改的,没对应questionId文件的不用恢复
     * @param fileHomework
     * @param question
     */
    private static void restoreQuestionItem(File fileHomework,Question question){
        File correctFile = new File(fileHomework,question.getQuestionId());

        if(question == null || question.getStudentAnswerStatus() == Api.STUDENT_ANSWER_STATUS_READED
            || !correctFile.exists()){
            return;
        }

        try {
            CorrectContent correctContent = (CorrectContent) SerializableUtil.read(correctFile);
            if(correctContent != null){
                question.setComment(correctContent.getComment());
                question.setStudentScore(correctContent.getStudentScore());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存老师对某道题的批改  cache/correct/16（用户id）/12345（studenthomeworkid id）/2 （questionId）
     * @param context
     * @param question
     */
    public static void saveQuestionCorrection(Context context, String studentHomeworkId, Question question){
        if(TextUtils.isEmpty(studentHomeworkId) || question == null){
            return;
        }

        File cachDir = new File(context.getCacheDir(),"correct");
        if(!cachDir.exists()){
            DataHelper.makeDirs(cachDir);
        }

        File userDir = new File(cachDir,AccountManager.getInstance().getUserInfo().getUserId());
        if(!userDir.exists()){
            DataHelper.makeDirs(userDir);
        }

        File homeworkDir = new File(userDir,studentHomeworkId);
        if(!homeworkDir.exists()){
            DataHelper.makeDirs(homeworkDir);
        }

        recursionSaveQuestion(homeworkDir,question);
    }

    /**
     * 递归保存叶子节点问题的批改内容
     * @param fileHomework
     * @param question
     */
    private static void recursionSaveQuestion(File fileHomework,Question question){
        if(question.getSubQuestion() == null || question.getSubQuestion().size() == 0){
            saveQuestionItem(fileHomework,question);
        }else{
            for(Question subQuestion:question.getSubQuestion()){
                recursionSaveQuestion(fileHomework,subQuestion);
            }
        }
    }

    /**
     * 这是对具体某个问题批改的保存, 如果是已批改的不用保存
     * @param fileHomework
     * @param question
     */
    private static void saveQuestionItem(File fileHomework,Question question){
        if(question == null || question.getStudentAnswerStatus() == Api.STUDENT_ANSWER_STATUS_READED){
            return;
        }

        File correctFile = new File(fileHomework,question.getQuestionId());
        try {
            CorrectContent content = new CorrectContent();
            content.setComment(question.getComment());
            content.setQuestionId(question.getQuestionId());
            content.setStudentScore(question.getStudentScore());
            SerializableUtil.write(content,correctFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除当前老师的所有批改缓存
     * @param context
     */
    public static void removeAllCorrections(Context context){
        File cachDir = new File(context.getCacheDir(),"correct");
        if(!cachDir.exists()){
            return;
        }

        File userDir = new File(cachDir,AccountManager.getInstance().getUserInfo().getUserId());
        if(!userDir.exists()){
            return;
        }

        FileUtils.deleteDir(userDir);
    }

    /**
     * 清除当前老师的对应的某个学生作业的批改
     * @param context
     * @param studentHomeworkId
     */
    public static void removeCorrection(Context context, String studentHomeworkId){
        if(TextUtils.isEmpty(studentHomeworkId)){
            return;
        }

        File cachDir = new File(context.getCacheDir(),"correct");
        if(!cachDir.exists()){
            return;
        }

        File userDir = new File(cachDir,AccountManager.getInstance().getUserInfo().getUserId());
        if(!userDir.exists()){
            return;
        }

        File homeworkDir = new File(userDir,studentHomeworkId);
        if(!homeworkDir.exists()){
            return;
        }
        FileUtils.deleteDir(homeworkDir);
    }
}
