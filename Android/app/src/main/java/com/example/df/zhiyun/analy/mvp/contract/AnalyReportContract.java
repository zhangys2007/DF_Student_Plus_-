package com.example.df.zhiyun.analy.mvp.contract;

import com.example.df.zhiyun.mvp.model.entity.AnalyReport;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/25/2019 15:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface AnalyReportContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        String KEY_ID = "id";
        void bindData(AnalyReport data);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<AnalyReport>> analysisReport(String id);  //作业分析报告
    }
}
