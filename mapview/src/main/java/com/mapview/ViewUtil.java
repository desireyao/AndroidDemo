package com.mapview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yaoh on 2018/6/3.
 */

public class ViewUtil {

    public static DisplayMetrics getDisplayMetric(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        return metric;
    }


    public static Bitmap getBitmapFormAsset(Context context, String picName) {
        InputStream open = null;
        try {
            open = context.getResources().getAssets().open(picName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(open);
        return bitmap;
    }

}
