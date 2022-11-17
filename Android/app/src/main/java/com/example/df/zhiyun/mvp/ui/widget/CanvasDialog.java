package com.example.df.zhiyun.mvp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.jess.arms.utils.DeviceUtils;
import com.example.df.zhiyun.mvp.ui.widget.fabricview.FabricView;

import com.example.df.zhiyun.R;

/***
 * 画板对话框,
 */
public class CanvasDialog {
    public static Dialog getCanvasDialog(Context context,ICanvasEnvent iCanvasEnvent ){
        Dialog dialog = new Dialog(context, R.style.canvasDialogTheme);
        dialog.setCancelable(false);
        View viewContent = LayoutInflater.from(context).inflate(R.layout.view_canvas,null);
        dialog.addContentView(viewContent, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        ImageButton ibCancel = viewContent.findViewById(R.id.ib_cancel);
        ImageButton ibUndo = viewContent.findViewById(R.id.ib_undo);
        ImageButton ibDelete = viewContent.findViewById(R.id.ib_delete);
        ImageButton ibDone = viewContent.findViewById(R.id.ib_done);
        FabricView fabricView = viewContent.findViewById(R.id.fabricview);
        fabricView.setBackgroundColor(ContextCompat.getColor(context,R.color.mask));
        fabricView.setColor(ContextCompat.getColor(context,R.color.colorPrimary));
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.ib_cancel:
                        dialog.dismiss();
                        break;
                    case R.id.ib_undo:
                        fabricView.undo();
                        break;
                    case R.id.ib_delete:
                        fabricView.cleanPage();
                        break;
                    case R.id.ib_done:
                        if(iCanvasEnvent != null){
                            float scale = calculateScale(fabricView.getWidth(), fabricView.getHeight());
                            iCanvasEnvent.onBitmapCreate(fabricView.getCanvasCompressBitmap(scale));
                        }
                        dialog.dismiss();
                        break;
                }
            }
        };
        ibCancel.setOnClickListener(clickListener);
        ibUndo.setOnClickListener(clickListener);
        ibDelete.setOnClickListener(clickListener);
        ibDone.setOnClickListener(clickListener);

        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = (int)(DeviceUtils.getScreenHeight(context)-DeviceUtils.getStatuBarHeight(context));
        window.setAttributes(lp);
        return dialog;
    }

    public static float calculateScale(float srcW, int srcH){
        if(srcW <= 0 || srcH <= 0){
            return 1;
        }

        float scale = srcW / 480f;

        if(scale > 1){
            return 1/scale;
        }else{
            return 1;
        }
    }

    public interface ICanvasEnvent{
        void onBitmapCreate(Bitmap bitmap);
    }
}
