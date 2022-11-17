/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.df.zhiyun.app;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.df.zhiyun.app.utils.BarTextColorUtil;
import com.example.df.zhiyun.app.utils.CompoundDrawableUtil;
import com.jaeger.library.StatusBarUtil;
import com.example.df.zhiyun.mvp.model.api.Api;

import com.example.df.zhiyun.R;
import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link Application.ActivityLifecycleCallbacks} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Timber.i(activity + " - onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Timber.i(activity + " - onActivityStarted");
        if (!activity.getIntent().getBooleanExtra("isInitToolbar", false)) {
            //由于加强框架的兼容性,故将 setContentView 放到 onActivityCreated 之后,onActivityStarted 之前执行
            //而 findViewById 必须在 Activity setContentView() 后才有效,所以将以下代码从之前的 onActivityCreated 中移动到 onActivityStarted 中执行
            activity.getIntent().putExtra("isInitToolbar", true);
            //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
            if (activity.findViewById(R.id.toolbar) != null) {
                if (activity instanceof AppCompatActivity) {
                    ((AppCompatActivity) activity).setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
                    ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.toolbar));
                        activity.getActionBar().setDisplayShowTitleEnabled(false);
                    }
                }

                int roleId = Api.TYPE_STUDENT;
                if(AccountManager.getInstance().getUserInfo() != null){
                    roleId = AccountManager.getInstance().getUserInfo().getRoleId();
                }
                if(roleId== Api.TYPE_STUDENT){
                    activity.findViewById(R.id.toolbar).setBackgroundColor(
                            ContextCompat.getColor(activity,R.color.red));
                    StatusBarUtil.setColorNoTranslucent(activity,ContextCompat.getColor(activity,
                            R.color.red));
                }else{
                    BarTextColorUtil.StatusBarLightMode(activity);  //状态栏文字颜色
                    TextView title = (TextView)activity.findViewById(R.id.toolbar_title);
                    if(title != null){
                        title.setTextColor(ContextCompat.getColor(activity,R.color.text_666));
                    }

                    TextView left = (TextView)activity.findViewById(R.id.toolbar_left_title);
                    if(left != null){
                        left.setTextColor(ContextCompat.getColor(activity,R.color.text_666));
                        CompoundDrawableUtil.setCompoundDrawable(activity,left,R.mipmap.arrow_left_grey,
                                0,0,0);
                    }

                    TextView right = (TextView)activity.findViewById(R.id.toolbar_right_title);
                    if(right != null){
                        right.setTextColor(ContextCompat.getColor(activity,R.color.text_666));
                    }
                }
            }
            if (activity.findViewById(R.id.toolbar_title) != null) {
                if(!TextUtils.isEmpty(activity.getTitle())){
                    ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
                }
            }
            if (activity.findViewById(R.id.toolbar_left_title) != null) {
                activity.findViewById(R.id.toolbar_left_title).setOnClickListener(v -> {
                    activity.onBackPressed();
                });
            }
        }
    }


    @Override
    public void onActivityResumed(Activity activity) {
        Timber.i(activity + " - onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.i(activity + " - onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.i(activity + " - onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.i(activity + " - onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.i(activity + " - onActivityDestroyed");
        //横竖屏切换或配置改变时, Activity 会被重新创建实例, 但 Bundle 中的基础数据会被保存下来,移除该数据是为了保证重新创建的实例可以正常工作
        activity.getIntent().removeExtra("isInitToolbar");
    }
}
