package com.example.df.zhiyun.app;

import android.content.Context;
import android.text.TextUtils;

import com.jess.arms.base.BaseApplication;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.UserService;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.LoginInfo;
import com.example.df.zhiyun.mvp.model.entity.UserInfo;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import timber.log.Timber;

/***
 * 统一管理用户的账户token，用户信息
 */
public class AccountManager {
    public static final String TAG = "AccountManager";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_USERID = "userid";
    public static final String KEY_ACCOUNT = "account";

    private static AccountManager mInstance;
    private UserInfo mUserInfo;

    private AccountManager(){}

    public static AccountManager getInstance(){
        if (mInstance == null){
            synchronized (AccountManager.class){
                if(mInstance == null){
                    mInstance = new AccountManager();
                }
            }
        }
        return mInstance;
    }

    //是否上次登陆还未登出过，
    public boolean hasLogin(Context context){
        String token = DataHelper.getStringSF(context.getApplicationContext(),
                KEY_TOKEN);
        String userId = DataHelper.getStringSF(context.getApplicationContext(),
                KEY_USERID);

        return !TextUtils.isEmpty(token) && !TextUtils.isEmpty(userId);
    }

    public String getLastAccount(Context context){
        return DataHelper.getStringSF(context.getApplicationContext(),
                KEY_ACCOUNT);
    }


    /***
     *
     * @param context
     * @param account
     * @param psw
     * @return
     */
    public Observable<BaseResponse<UserInfo>> login(Context context, final String account, String psw){
        IRepositoryManager repositoryManager =
                ((BaseApplication)context.getApplicationContext()).getAppComponent().repositoryManager();
        Map<String,Object> params = new HashMap<>();
        params.put("userName",account.trim());
        params.put("passWord", psw.trim());

        return repositoryManager
                .obtainRetrofitService(UserService.class)
                .login(ParamsUtils.fromMapRaw(context,params))
                .map(new Function<BaseResponse<UserInfo>, BaseResponse<UserInfo>>() {
                    @Override
                    public BaseResponse<UserInfo> apply(BaseResponse<UserInfo> loginInfoBaseResponse) throws Exception {
                        if(loginInfoBaseResponse.isSuccess() && loginInfoBaseResponse.getData() != null){
                            DataHelper.setStringSF(context.getApplicationContext(),
                                    KEY_TOKEN,loginInfoBaseResponse.getData().getToken());
                            DataHelper.setStringSF(context.getApplicationContext(),
                                    KEY_USERID,loginInfoBaseResponse.getData().getUserId());
                            DataHelper.setStringSF(context.getApplicationContext(),
                                    KEY_ACCOUNT,account);
                            mUserInfo = loginInfoBaseResponse.getData();
                            Timber.tag(TAG).d("save token");
                        }else{
                            DataHelper.removeSF(context.getApplicationContext(),KEY_TOKEN);
                        }

                        return loginInfoBaseResponse;
                    }
                });
    }

    //请求用户信息
    public Observable<BaseResponse<UserInfo>> requestUserInfo(Context context){
        String token = DataHelper.getStringSF(context.getApplicationContext(),
                KEY_TOKEN);
        String userId = DataHelper.getStringSF(context.getApplicationContext(),
                KEY_USERID);
        IRepositoryManager repositoryManager =
                ((BaseApplication)context.getApplicationContext()).getAppComponent().repositoryManager();
        Map<String,Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("token", token);

        return repositoryManager
                .obtainRetrofitService(UserService.class)
                .getUserProfile(ParamsUtils.fromMapRaw(context,params))
                .map(new Function<BaseResponse<UserInfo>, BaseResponse<UserInfo>>() {
                    @Override
                    public BaseResponse<UserInfo> apply(BaseResponse<UserInfo> response) throws Exception {
                        if(response.isSuccess()){
                            mUserInfo = response.getData();
                        }
                        return response;
                    }
                });
    }

    /***
     * 其它地方登陆，这里被迫下线
     */
    public void onForceLogout(Context context){
        DataHelper.removeSF(context.getApplicationContext(),KEY_TOKEN);
        DataHelper.removeSF(context.getApplicationContext(),KEY_USERID);
    }


    //登出
    public Observable<BaseResponse> logout(Context context){
//        DataHelper.removeSF(context.getApplicationContext(),KEY_TOKEN);
        IRepositoryManager repositoryManager =
                ((BaseApplication)context.getApplicationContext()).getAppComponent().repositoryManager();
        Map<String,Object> params = new HashMap<>();
        params.put("userId", getUserInfo().getUserId());

        return repositoryManager
                .obtainRetrofitService(UserService.class)
                .logout(ParamsUtils.fromMap(context,params))
                .map(new Function<BaseResponse, BaseResponse>() {
                    @Override
                    public BaseResponse apply(BaseResponse response) throws Exception {
                        DataHelper.removeSF(context.getApplicationContext(),KEY_TOKEN);
                        DataHelper.removeSF(context.getApplicationContext(),KEY_USERID);
                        return response;
                    }
                });
    }


    public String getToken(Context context){
        if(mUserInfo == null || TextUtils.isEmpty(mUserInfo.getToken())){
            return DataHelper.getStringSF(context.getApplicationContext(),
                    KEY_TOKEN);
        }else{
            return mUserInfo.getToken();
        }

    }


    public UserInfo getUserInfo(){
        return mUserInfo;
    }
}
