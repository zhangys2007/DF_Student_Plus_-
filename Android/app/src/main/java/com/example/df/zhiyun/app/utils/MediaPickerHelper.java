package com.example.df.zhiyun.app.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.ui.widget.CommonDialog.CommonDialogs;
import com.example.df.zhiyun.mvp.ui.widget.GlideImageEngine;

import id.zelory.compressor.Compressor;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class MediaPickerHelper {
    //拍照后更新相册

    public static void updateAlbum(Context context, Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        context.sendBroadcast(mediaScanIntent);
    }
    public static void updateAlbum(Context context, String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static void lunchCamera(Activity activity,int reqCode,Uri uri){
        RxPermissions rxPermissions = new RxPermissions( (FragmentActivity)activity);
        if(!rxPermissions.isGranted( Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                !rxPermissions.isGranted( Manifest.permission.CAMERA) ){
            ArmsUtils.snackbarTextWithLong("请到系统设置中开启相机权限");
            return;
        }


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(takePictureIntent, reqCode);
    }

    public static void lunchCamera(Fragment fragment, int reqCode, Uri uri){
        RxPermissions rxPermissions = new RxPermissions(fragment);
        if(!rxPermissions.isGranted( Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                !rxPermissions.isGranted( Manifest.permission.CAMERA) ){
            ArmsUtils.snackbarTextWithLong("请到系统设置中开启相机权限");
            return;
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        fragment.startActivityForResult(takePictureIntent, reqCode);
    }


    public static void lunchAlbum(Fragment fragment,int reqCode){
        RxPermissions rxPermissions = new RxPermissions(fragment);
        if(!rxPermissions.isGranted( Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            ArmsUtils.snackbarTextWithLong("请到系统设置中开启存储空间权限");
            return;
        }
        Matisse
                .from(fragment)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.8f)
                //蓝色主题
                .theme(R.style.AlbumTheme)
                .imageEngine(new GlideImageEngine())
                //请求码
                .forResult(reqCode);
    }

    public static void lunchAlbum(Fragment fragment,int reqCode, int limite){
        RxPermissions rxPermissions = new RxPermissions(fragment);
        if(!rxPermissions.isGranted( Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            ArmsUtils.snackbarTextWithLong("请到系统设置中开启存储空间权限");
            return;
        }
        Matisse
                .from(fragment)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(limite)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.8f)
                //蓝色主题
                .theme(R.style.AlbumTheme)
                .imageEngine(new GlideImageEngine())
                //请求码
                .forResult(reqCode);
    }

    public static void lunchAlbum(Activity activity,int reqCode){
        RxPermissions rxPermissions = new RxPermissions((FragmentActivity)activity);
        if(!rxPermissions.isGranted( Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            ArmsUtils.snackbarTextWithLong("请到系统设置中开启存储空间权限");
            return;
        }
        Matisse
                .from(activity)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.8f)
                //蓝色主题
                .theme(R.style.AlbumTheme)
                .imageEngine(new GlideImageEngine())
                //请求码
                .forResult(reqCode);
    }


    public static Observable<Bitmap> getCompressBitmapFromUris(Context context, Uri uri){
        return Observable.just(uri)
                .map(new Function<Uri, File>() {
                    @Override
                    public File apply(Uri uri) throws Exception {
                        return FileUriUtil.uriToFile(uri,context);
                    }
                }).map(new Function<File,Bitmap>(){
                    @Override
                    public Bitmap apply(File file) throws Exception {
                        Bitmap bitmap = new Compressor.Builder(context.getApplicationContext())
                                .setMaxWidth(640)
                                .setMaxHeight(480)
                                .setQuality(75)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .build().compressToBitmap(file);
                        return bitmap;
                    }
                });
    }


    public static Observable<List<Bitmap>> getCompressBitmapFromFiles(Context context, List<File> files){
        return Observable.fromIterable(files)
                .map(new Function<File,Bitmap>(){
                    @Override
                    public Bitmap apply(File file) throws Exception {
                        Bitmap bitmap = new Compressor.Builder(context.getApplicationContext())
                                .setMaxWidth(480)
                                .setMaxHeight(640)
                                .setQuality(75)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .build().compressToBitmap(file);
                        return bitmap;
                    }
                }).buffer(files.size());
    }

    //从uri中返回经过压缩过的图片
    public static Observable<List<Bitmap>> getCompressBitmapFromUris(Context context, List<Uri> uriList){
        return Observable.fromIterable(uriList)
                .map(new Function<Uri, File>() {
                    @Override
                    public File apply(Uri uri) throws Exception {
                        return FileUriUtil.uriToFile(uri,context);
                    }
                }).map(new Function<File,Bitmap>(){
                    @Override
                    public Bitmap apply(File file) throws Exception {
                        Bitmap bitmap = new Compressor.Builder(context.getApplicationContext())
                                .setMaxWidth(640)
                                .setMaxHeight(480)
                                .setQuality(75)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .build().compressToBitmap(file);
                        return bitmap;
                    }
                }).buffer(uriList.size());
    }


    /***
     * 头像选取方式对话框
     */
    public static Dialog getPicSelDialog(Context context, String title,final IMediaPicker iMediaPicker){
        List<String> items = Arrays.asList(context.getResources().getStringArray(R.array.array_pic_from));
        Dialog mDialog = new CommonDialogs(context)
                .setListView(items,new AdapterView.OnItemClickListener (){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(position == 0){
                            iMediaPicker.selectCamera();
                        }else if(position == 1){
                            iMediaPicker.selectAlbum();
                        }
                    }
                })
                .setTitle(title)
                .builder();
        return mDialog;
    }

    public interface IMediaPicker{
        void selectCamera();
        void selectAlbum();
    }
}
