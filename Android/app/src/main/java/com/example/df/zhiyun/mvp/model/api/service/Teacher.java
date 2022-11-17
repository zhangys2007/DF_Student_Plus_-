package com.example.df.zhiyun.mvp.model.api.service;

import com.example.df.zhiyun.main.mvp.model.entity.HomePageData;
import com.example.df.zhiyun.mvp.model.entity.AnalyReport;
import com.example.df.zhiyun.mvp.model.entity.AnalysisDict;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.ClassCorrectInfo;
import com.example.df.zhiyun.mvp.model.entity.ClassImprove;
import com.example.df.zhiyun.mvp.model.entity.ComSubjCla;
import com.example.df.zhiyun.mvp.model.entity.CompearItem;
import com.example.df.zhiyun.mvp.model.entity.CorrectDetail;
import com.example.df.zhiyun.mvp.model.entity.DetailTable;
import com.example.df.zhiyun.mvp.model.entity.FilterGrade;
import com.example.df.zhiyun.mvp.model.entity.FormedPaper;
import com.example.df.zhiyun.mvp.model.entity.Grade;
import com.example.df.zhiyun.mvp.model.entity.GradeAvg;
import com.example.df.zhiyun.mvp.model.entity.GradePerscent;
import com.example.df.zhiyun.mvp.model.entity.GrowthTraceItem;
import com.example.df.zhiyun.mvp.model.entity.HistoryKnowledgePoint;
import com.example.df.zhiyun.mvp.model.entity.HomeworkSet;
import com.example.df.zhiyun.mvp.model.entity.HomeworkState;
import com.example.df.zhiyun.mvp.model.entity.KnowledgeGrasp;
import com.example.df.zhiyun.mvp.model.entity.KnowledgePoint;
import com.example.df.zhiyun.mvp.model.entity.MeasureSumary;
import com.example.df.zhiyun.mvp.model.entity.Plan;
import com.example.df.zhiyun.mvp.model.entity.PlanUsage;
import com.example.df.zhiyun.mvp.model.entity.PointAnalysis;
import com.example.df.zhiyun.mvp.model.entity.PutHWDetail;
import com.example.df.zhiyun.mvp.model.entity.PutStudent;
import com.example.df.zhiyun.mvp.model.entity.ScoreCompear;
import com.example.df.zhiyun.mvp.model.entity.ScoreCompearLayer;
import com.example.df.zhiyun.mvp.model.entity.ScoreCompearLevel;
import com.example.df.zhiyun.mvp.model.entity.ScoreCompearSumary;
import com.example.df.zhiyun.mvp.model.entity.SelPaperItem;
import com.example.df.zhiyun.mvp.model.entity.StudentHomework;

import java.util.List;

import io.reactivex.Observable;

import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.model.entity.ClassItem;
import com.example.df.zhiyun.mvp.model.entity.CommentItem;
import com.example.df.zhiyun.mvp.model.entity.GradeItem;
import com.example.df.zhiyun.mvp.model.entity.HomeworkArrange;
import com.example.df.zhiyun.mvp.model.entity.MemberItem;
import com.example.df.zhiyun.mvp.model.entity.SchoolItem;
import com.example.df.zhiyun.mvp.model.entity.StudentImprove;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Teacher {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/putHomeWorkList")
    Observable<BaseResponse<List<HomeworkArrange>>> putHomeWorkList(@Body RequestBody data);  //布置的作业列表

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/teacherBelongClass")
    Observable<BaseResponse<List<BelongClass>>> belongClass(@Body RequestBody data);  //老师所带的班级

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/commentingList")
    Observable<BaseResponse<List<CommentItem>>> commentList(@Body RequestBody data);  //评论列表

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/mySchool")
    Observable<BaseResponse<List<SchoolItem>>> schoolList(@Body RequestBody data);  //我的学校

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/schoolMember")
    Observable<BaseResponse<List<GradeItem>>> gradeList(@Body RequestBody data);  //我的年级

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/gradeMember")
    Observable<BaseResponse<List<ClassItem>>> classList(@Body RequestBody data);  //我的班级

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/classMember")
    Observable<BaseResponse<List<MemberItem>>> memberList(@Body RequestBody data);  //班级成员


    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/unpaidStudent")
    Observable<BaseResponse<List<StudentHomework>>> unpaidStudent(@Body RequestBody data);  //未交学生


    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/isCorrectionStudent")
    Observable<BaseResponse<List<StudentHomework>>> isCorrectionStudent(@Body RequestBody data);  //未批/已批的学生列表

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/homeworkReminder")
    Observable<BaseResponse> homeworkReminder(@Body RequestBody data);  //提醒交作业

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/updateCorrectionStatus")
    Observable<BaseResponse> updateHomeWorkState(@Body RequestBody data);  //修改作业状态

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/selectedPaper")
    Observable<BaseResponse<List<SelPaperItem>>> selectedPaper(@Body RequestBody data);  //精选试卷列表

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/formedPapersList")
    Observable<BaseResponse<List<FormedPaper>>> formedPaperList(@Body RequestBody data);  //已组试卷列表

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/teacherClass")
    Observable<BaseResponse<List<BelongClass>>> teacherClass(@Body RequestBody data);  //老师布置作业班级

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/putHomeWork")
    Observable<BaseResponse> putHomeWork(@Body RequestBody data);  //布置作业

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/revokeAllHomeWork")
    Observable<BaseResponse<String>> revokeHomeWork(@Body RequestBody data);  //取消作业

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/homeWorkDetails")
    Observable<BaseResponse<PutHWDetail>> putHomeWorkDetail(@Body RequestBody data);  //布置详情

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/MyPlanAndResource")
    Observable<BaseResponse<List<Plan>>> myPlan(@Body RequestBody data);  //我的教案和教学设计列表

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/planAndResourcePreview")
    Observable<BaseResponse<List<String>>> planResource(@Body RequestBody data);  //教案和教学设计资源预览

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/commentingDetail")
    Observable<BaseResponse<String>> commentingDetailUrl(@Body RequestBody data);  //讲评分析的url

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/correctionDetails")
    Observable<BaseResponse<CorrectDetail>> correctionDetails(@Body RequestBody data);  //批改作业详情

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/homeworkCorrecting")
    Observable<BaseResponse<HomeworkSet>> homeworkCorrecting(@Body RequestBody data);  //要批改的作业集

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/questionsCorrect")
    Observable<BaseResponse> questionsCorrect(@Body RequestBody data);  //批改某道题

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/completeCorrections")
    Observable<BaseResponse> completeCorrections(@Body RequestBody data);  //完成批改

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/putStudents")
    Observable<BaseResponse<List<PutStudent>>> putStudents(@Body RequestBody data);  //按人布置班级成员列表

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("tikuCommenton/studentImproveHistory")
    Observable<BaseResponse<List<GrowthTraceItem>>> studentImproveHistory(@Body RequestBody data);  //班级下学生的成长轨迹列表

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("dataAnalysis/findAnalysisDict")
    Observable<BaseResponse<List<FilterGrade>>> findAnalysisDict(@Body RequestBody data);  //公共年级，学科，班级条件筛选

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("dataAnalysis/findClassSubjectByStudent")
    Observable<BaseResponse<ComSubjCla>> findClassSubjectByStudent(@Body RequestBody data);  //学生端获取班级，学科公共接口

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/knowledgePointAnalysis")
    Observable<BaseResponse<List<KnowledgePoint>>> knowledgePointAnalysis(@Body RequestBody data);  //学生端知识点统计

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/grades")
    Observable<BaseResponse<List<Grade>>> studentGrades(@Body RequestBody data);  //学生端成绩追踪

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("student/analysisReport")
    Observable<BaseResponse<AnalyReport>> analysisReport(@Body RequestBody data);  //作业分析报告

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("tikuCommenton/studentImprove")
    Observable<BaseResponse<List<StudentImprove>>> studentImprove(@Body RequestBody data);  //学生成长轨迹图

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("tikuCommenton/classImproveCurveInfo")
    Observable<BaseResponse<List<ClassImprove>>> classImproveCurveInfo(@Body RequestBody data); //班级成长轨迹图

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/stuHomeworkState")
    Observable<BaseResponse<List<HomeworkState>>> homeworkState(@Body RequestBody data); //作业提交和批改情况

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/teacher/gotPlanUsageHistoryTable")
    Observable<BaseResponse<List<PlanUsage>>> planUsage(@Body RequestBody data); //教案使用情况

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findHomeworkAnalysisDict")
    Observable<BaseResponse<AnalysisDict>> findHomeworkAnalysisDict(@Body RequestBody data);  //年级分析公用的年级或者班级、学科

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/goTCommentonUsageHistoryTable")
    Observable<BaseResponse<List<PlanUsage>>> goTCommentonUsageHistoryTable(@Body RequestBody data);  //讲评报告使用分析

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("dataAnalysis/findKnowledgeAnalysis")
    Observable<BaseResponse<List<HistoryKnowledgePoint>>> historyKnowledgeAnalysis(@Body RequestBody data);  //历次知识点分析列表

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("teacher/knowledgePointAnalysis")
    Observable<BaseResponse<List<PointAnalysis>>> pointAnalysis(@Body RequestBody data);  //知识点来源

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findHomeworkAnalysis")
    Observable<BaseResponse<List<JsonObject>>> findHomeworkAnalysis(@Body RequestBody data);  //历次作业列表

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findAssignClassByGrade")
    Observable<BaseResponse<List<ClassCorrectInfo>>> findClassCorrectInfo(@Body RequestBody data);  //班级批改详情

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findTestStatus")
    Observable<BaseResponse<MeasureSumary>> findTestStatus(@Body RequestBody data);  //班级数据分析（测评概览

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findKnowledgeScoreAnalysis")
    Observable<BaseResponse<List<KnowledgeGrasp>>> knowledgeGrasp(@Body RequestBody data);  //知识点掌握情况

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findTestPaperAnalysis")
    Observable<BaseResponse<List<DetailTable>>> findTestPaperAnalysis(@Body RequestBody data);  //知识点掌握情况（双向细目表）

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findClassScoreContrast")
    Observable<BaseResponse<ScoreCompear>> findClassScoreContrast(@Body RequestBody data);  //知识点掌握情况（双向细目表）

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findClassQuestionScoreAnalysis")
    Observable<BaseResponse<CompearItem>> findClassQuestionScoreAnalysis(@Body RequestBody data);  //班级成绩对比（小题得分对比）

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findClassKnowledgeScoreAnalysis")
    Observable<BaseResponse<CompearItem>> findClassKnowledgeScoreAnalysis(@Body RequestBody data);  //班级成绩对比（知识点得分对比）

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findTestPaperAnalysis")
    Observable<BaseResponse<List<DetailTable>>> findTestGradePaperAnalysis(@Body RequestBody data);  //年级分析-试卷分析评价

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findScoreOverview")
    Observable<BaseResponse<List<ScoreCompearSumary>>> findScoreOverview(@Body RequestBody data);  //年级成绩分析（成绩概览）

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findGradeRankDistribution")
    Observable<BaseResponse<GradePerscent>> findGradeRankDistribution(@Body RequestBody data);  //年级成绩成绩分析（年级排名分布）

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findAverageContrast")
    Observable<BaseResponse<List<GradeAvg>>> findAverageContrast(@Body RequestBody data);  //年级成绩成绩分析（年级排名分布）

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findGradeDistribution")
    Observable<BaseResponse<List<ScoreCompearLevel>>> findGradeDistribution(@Body RequestBody data);  //年级成绩成绩分析（等级分布）

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("homeworkDataAnalysis/findArrangementContrast")
    Observable<BaseResponse<List<ScoreCompearLayer>>> findArrangementContrast(@Body RequestBody data);  //年级成绩成绩分析（四个层次）

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/v1/teacher/homePage")
    Observable<BaseResponse<HomePageData>> getHomepageData(@Body RequestBody data);
}
