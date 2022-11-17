/*
 * Copyright (C) 2014-2016 Dominik Schürmann <dominik@dominikschuermann.de>
 * Copyright (C) 2013 Antarix Tandon
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

package com.example.df.zhiyun.mvp.ui.widget;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;

import java.net.URI;

import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;

public class HtmlHttpBase64ImageGetter implements ImageGetter {
    TextView container;
    URI baseUri;
    boolean matchParentWidth;

    private boolean compressImage = false;
    private int qualityImage = 50;

    public HtmlHttpBase64ImageGetter(TextView textView) {
        this.container = textView;
        this.matchParentWidth = false;
    }

    public HtmlHttpBase64ImageGetter(TextView textView, String baseUrl) {
        this.container = textView;
        if (baseUrl != null) {
            this.baseUri = URI.create(baseUrl);
        }
    }

    public HtmlHttpBase64ImageGetter(TextView textView, String baseUrl, boolean matchParentWidth) {
        this.container = textView;
        this.matchParentWidth = matchParentWidth;
        if (baseUrl != null) {
            this.baseUri = URI.create(baseUrl);
        }
    }

    public void enableCompressImage(boolean enable) {
        enableCompressImage(enable, 50);
    }

    public void enableCompressImage(boolean enable, int quality) {
        compressImage = enable;
        qualityImage = quality;
    }

    private float getBase64BitmapScale(Drawable drawable){
        float scale = Resources.getSystem().getDisplayMetrics().density;
        int drawableWidth = drawable.getIntrinsicWidth();
        float widthLimit = DeviceUtils.getScreenWidth(container.getContext()) - 3.5f*ArmsUtils.dip2px(container.getContext(),25);

        if(scale * drawableWidth > widthLimit){
            scale = widthLimit/drawableWidth;
        }

        return scale;
    }

    public Drawable getDrawable(String source) {
        if(source.startsWith("data:image/png;base64,")){
            String strBase64 = source.replace("data:image/png;base64,","");
            Bitmap bitmap = Base64BitmapTransformor.getBitmap(strBase64,container.getContext());
            BitmapDrawable bitmapDrawable;

            if(bitmap != null){
                bitmapDrawable = new BitmapDrawable(bitmap);
                bitmapDrawable.setBounds(0, 0, (int) (bitmapDrawable.getIntrinsicWidth() * getBase64BitmapScale(bitmapDrawable)),
                        (int) (bitmapDrawable.getIntrinsicHeight()  *getBase64BitmapScale(bitmapDrawable)));
            }else{
                bitmapDrawable = new BitmapDrawable();
            }

            return bitmapDrawable;
        }else{
            UrlDrawable urlDrawable = new UrlDrawable();

            Glide.with(container.getContext()).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    if (urlDrawable == null || resource == null || container == null) {
                        return;
                    }
                    BitmapDrawable result = new BitmapDrawable(container.getResources(), resource);
                    float scale = getBase64BitmapScale(result);

                    result.setBounds(0, 0, (int) (result.getIntrinsicWidth() * scale),
                            (int) (result.getIntrinsicHeight() * scale));

                    urlDrawable.setBounds(0, 0, (int) (result.getIntrinsicWidth() * scale),
                            (int) (result.getIntrinsicHeight() * scale));
                    urlDrawable.drawable = result;

                    container.invalidate();
                    container.setText(container.getText());
                }
            });
            return urlDrawable;
        }
    }

    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }
} 
