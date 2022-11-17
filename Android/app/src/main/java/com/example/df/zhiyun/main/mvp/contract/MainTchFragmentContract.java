package com.example.df.zhiyun.main.mvp.contract;

import android.content.Context;

import com.example.df.zhiyun.main.mvp.model.entity.FocusStd;
import com.example.df.zhiyun.main.mvp.model.entity.HomePageData;
import com.example.df.zhiyun.main.mvp.model.entity.MsgItem;
import com.example.df.zhiyun.main.mvp.model.entity.PushData;
import com.example.df.zhiyun.main.mvp.model.entity.TodoItem;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.example.df.zhiyun.mvp.model.adapterEntity.MainHWPutMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

import com.example.df.zhiyun.mvp.model.entity.HomeworkArrange;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 10:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MainTchFragmentContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        String KEY_TOD = "todo";
        String KEY_FOCUS = "focus";
        String KEY_PUSH = "push";
        void marqueeNext(MsgItem msg);
        Context getPageContext();
        void showMarque(boolean show);
        void processPush(List<PushData> dat);
        void processTodo(List<TodoItem> dat);
        void processFollow(List<FocusStd> dat);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<HomePageData>> getHomepageData();
    }
}
