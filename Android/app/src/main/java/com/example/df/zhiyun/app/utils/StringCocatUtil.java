package com.example.df.zhiyun.app.utils;

import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;

public class StringCocatUtil {
    public static CharSequence concat(CharSequence... text) {
        if (text.length == 0) {
            return "";
        }

        if (text.length == 1) {
            return text[0];
        }

        boolean spanned = false;
        for (CharSequence piece : text) {
            if (piece instanceof Spanned) {
                spanned = true;
                break;
            }
        }

        if (spanned) {
            final SpannableStringBuilder ssb = new SpannableStringBuilder();
            for (CharSequence piece : text) {
                // If a piece is null, we append the string "null" for compatibility with the
                // behavior of StringBuilder and the behavior of the concat() method in earlier
                // versions of Android.
                ssb.append(piece == null ? "" : piece);
            }
            return new SpannedString(ssb);
        } else {
            final StringBuilder sb = new StringBuilder();
            for (CharSequence piece : text) {
                sb.append(piece == null ? "" : piece);
            }
            return sb.toString();
        }
    }

    public static String getStringExtraDefault(Intent intent, String key){
        String value = intent.getStringExtra(key);
        return TextUtils.isEmpty(value)?"":value;
    }
}
