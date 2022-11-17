package com.example.df.zhiyun.mvp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;
import com.example.df.zhiyun.app.utils.MediaPickerHelper;
import com.example.df.zhiyun.mvp.ui.adapter.PhotoViewAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * 画廊对话框，用来预览图片
 */
public class GallaryDialog extends Dialog implements View.OnClickListener
        ,ViewPager.OnPageChangeListener{
    @BindView(R.id.tv_indicator)
    TextView tvIndicator;
    @BindView(R.id.iv_save)
    ImageView ivSave;
    @BindView(R.id.vp_gallary)
    ViewPager vp;
    @BindView(R.id.ibtn_back)
    ImageView iBtnBack;

    private int mIndex = 0;
    private List<String> mUrls;
    private PhotoViewAdapter mAdapter;

    public GallaryDialog(Context context, int initPosition, List<String> urls) {
        super(context,R.style.canvasDialogTheme);
        mIndex = initPosition;
        mUrls = urls;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_gallary);
        ButterKnife.bind(this);
        initGallaryView();

        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    private void initGallaryView(){
        ivSave.setOnClickListener(this);
        iBtnBack.setOnClickListener(this);
        updateIndicator();
        if(mUrls != null || mUrls.size() != 0){
            mAdapter = new PhotoViewAdapter(getContext(),mUrls);
            vp.setAdapter(mAdapter);
            vp.addOnPageChangeListener(this);
            vp.setCurrentItem(mIndex);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtn_back:
                dismiss();
                break;
            case R.id.iv_save:
                clickSaveImage();
                break;
        }
    }

    private void clickSaveImage(){
        String url = mUrls.get(mIndex);
        if(url.startsWith("data:image/png;base64,")){
            saveImage(Base64BitmapTransformor.getBitmap(url));
        }else{
            Glide.with(getContext()).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    saveImage(resource);
                }

                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    Toast.makeText(getContext(),getContext().getString(R.string.save_faile),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mIndex = i;
        updateIndicator();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void updateIndicator(){
        if(mUrls == null || mUrls.size() == 0){
            tvIndicator.setText("0/0");
        }else{
            tvIndicator.setText(""+(mIndex+1)+"/"+mUrls.size());
        }
    }


    private void saveImage(Bitmap image) {
        try {
            if(TextUtils.equals(Build.MANUFACTURER,"Xiaomi") &&
                    Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1 ){
                save2ExternalStorage(image);
            }else{
                String imageFileName = "ban" + Calendar.getInstance().getTimeInMillis() + ".jpg";
                MediaStore.Images.Media.insertImage(getContext().getContentResolver(),image,imageFileName,"");
                Toast.makeText(getContext(),getContext().getString(R.string.save_success),Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(),getContext().getString(R.string.save_faile),Toast.LENGTH_SHORT).show();
        }
    }

    private void save2ExternalStorage(Bitmap bitmap){
        String DCIMPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        File targetFile = new File(DCIMPath,"ttd_" + Calendar.getInstance().getTimeInMillis() + ".jpg");
        if(targetFile.exists()){
            targetFile.delete();
        }
        String saveImagePath = targetFile.getAbsolutePath();
        try {
            OutputStream fout = new FileOutputStream(targetFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            fout.close();
            MediaPickerHelper.updateAlbum(getContext(),saveImagePath);
            Toast.makeText(getContext(),getContext().getString(R.string.save_success),Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(),getContext().getString(R.string.save_faile),Toast.LENGTH_SHORT).show();
        }
    }
}
