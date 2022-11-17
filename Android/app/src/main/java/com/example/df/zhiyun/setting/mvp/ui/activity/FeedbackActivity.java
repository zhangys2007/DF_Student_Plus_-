package com.example.df.zhiyun.setting.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.setting.di.component.DaggerFeedbackComponent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.setting.mvp.contract.FeedbackContract;
import com.example.df.zhiyun.setting.mvp.presenter.FeedbackPresenter;
import com.kaopiz.kprogresshud.KProgressHUD;

import javax.inject.Inject;

import butterknife.BindView;


import com.example.df.zhiyun.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 意见反馈
 * <p>
 * Created by MVPArmsTemplate on 08/17/2019 22:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FeedbackActivity extends BaseStatusActivity<FeedbackPresenter> implements FeedbackContract.View {
    @BindView(R.id.toolbar_left_title)
    TextView tvLeft;
    @BindView(R.id.et_feedback)
    EditText etFeedback;
    @BindView(R.id.tv_submit_suggestion)
    TextView tvSubmit;

    @Inject
    KProgressHUD progressHUD;

    private String strMathML = "";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFeedbackComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_feedback; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        tvLeft.setText(R.string.setting);
//        tvSubmit.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                mPresenter.clickSubmit(etFeedback.getText().toString());
//            }
//        });
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        tvLeft.setText(R.string.setting);
        tvSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mPresenter.clickSubmit(etFeedback.getText().toString());
//                try {
//                    strMathML = etFeedback.getText().toString();
//                    if(TextUtils.isEmpty(strMathML)){
//                        return;
//                    }
//                    AssetManager am = getResources().getAssets();
//                    Source xsltSource = new StreamSource(am.open("mmltex.xsl"));
//                    Source xmlSource = new StreamSource(new ByteArrayInputStream(strMathML.getBytes()));
//
//                    TransformerFactory transFact = TransformerFactory.newInstance();
//                    Transformer trans = transFact.newTransformer(xsltSource);
//                    ByteArrayOutputStream output = new ByteArrayOutputStream();
//                    StreamResult result = new StreamResult(output);
//                    trans.transform(xmlSource, result);
//                    Timber.tag(TAG).d("latex: "+ output.toString());
//                } catch (TransformerConfigurationException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (TransformerFactoryConfigurationError e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (TransformerException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e){
//                    e.printStackTrace();
//                }
            }
        });
    }

    @Override
    public void showLoading() {
        if(progressHUD!=null){
            if(progressHUD.isShowing()){
                progressHUD.dismiss();
            }
            progressHUD.show();
        }
    }

    @Override
    public void hideLoading() {
        if(progressHUD!=null){
            progressHUD.dismiss();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
