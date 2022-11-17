package com.example.df.zhiyun.correct.mvp.model;

import android.app.Application;

import com.example.df.zhiyun.correct.mvp.contract.CorrectHWlistenContract;
import com.example.df.zhiyun.mvp.model.api.service.CommonService;
import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/19/2019 11:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class CorrectHWlistenModel extends BaseModel implements CorrectHWlistenContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CorrectHWlistenModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<File> downloadFile(String url, File saveFile) {
        return mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .downloadFile(url)
                .flatMap(new Function<ResponseBody, ObservableSource<File>>() {
                    @Override
                    public ObservableSource<File> apply(ResponseBody responseBody) throws Exception {
                        InputStream is = responseBody.byteStream();
                        FileOutputStream fos = new FileOutputStream(saveFile);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = bis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                            fos.flush();
                        }
                        fos.close();
                        bis.close();
                        is.close();
                        return Observable.just(saveFile);
                    }
                });
    }
}