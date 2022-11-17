package com.example.df.zhiyun.mvp.contract;

import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.model.entity.PutHWDetail;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 15:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface PutHWDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        int REQ_SEL = 89;
        String KEY_CLASS_NAME = "className";
        String KEY_PAPER_ID = "paperId";
        String KEY_TYPE = "type";
        String KEY_SYSTEM_ID = "teachSystemId";
        String KEY_LINK_ID = "linkId";
        String KEY_CLASS_ID = "classId";

        void bindDetailInfo(PutHWDetail data);
        void updateClassName(String clName);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<PutHWDetail>> putHomeworkDetail(String homeworkId, String classId, int type);
        Observable<BaseResponse<List<BelongClass>>> homeworkClass(String paperId, int type, String teachSystemId, String linkId);
    }
}
