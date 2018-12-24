package com.mapview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义 地图 坐标 点
 * Created by Administrator on 2016/6/30.
 */
public class MapPointWithTitleView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "MapPointWithTitleView";

    public static final int RED_POINT = 0;
    public static final int DEFAULT_POINT = 1;

    private ImageView pointIcon;
    private TextView pointTitle;
    private Bitmap mPointIconBitmap;

    // 初始 位置
    private double firstX;
    private double firstY;

    // 点的 边界
    private double borderTop;
    private double borderLeft;

    // 点 的 显示 状态
    private int state;

    // 点 的 名称
    private String title;

    // 是否 显示 点 名称
    private boolean isTitleShow;

    public MapPointWithTitleView(Context context) {
        super(context);
    }

    public MapPointWithTitleView(Context context, double pointX, double pointY, Bitmap pointIconBitmap) {
        super(context);
        firstX = pointX;
        firstY = pointY;
        mPointIconBitmap = pointIconBitmap;
        init();
    }

    public MapPointWithTitleView(Context context, double pointX, double pointY, int state, boolean isTitleShow, String title) {
        super(context);
        firstX = pointX;
        firstY = pointY;
        state = state;
        title = title;
        isTitleShow = isTitleShow;
        init();
    }

    public void setFirstXShow(float x) {
        x -= borderLeft;
        setX(x);
    }

    public void setFirstYShow(float y) {
        y -= borderTop;
        setY(y);
    }

    public void setFirstX(double firstX) {
        this.firstX = firstX;
    }

    public void setFirstY(double firstY) {
        this.firstY = firstY;
    }

    public void init() {
        pointIcon = new ImageView(getContext());
        pointIcon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        pointTitle = new TextView(getContext());
        pointTitle.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        pointTitle.setGravity(Gravity.CENTER);

        setOrientation(VERTICAL);
        addView(pointIcon);
        addView(pointTitle);

        pointIcon.setImageBitmap(mPointIconBitmap);

        // 测量 边界
        measureBorder();

        // 设置 监听
        setOnClickListener(this);
    }


    /**
     * 测量 地图点 的 边界
     */
    private void measureBorder() {
        int height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

        pointIcon.measure(width, height);
        pointTitle.measure(width, height);
        measure(width, height);

        if (!isTitleShow) {
            pointTitle.setVisibility(INVISIBLE);
        } else {
            pointTitle.setVisibility(VISIBLE);
        }

        this.borderLeft = (getMeasuredWidth() - pointIcon.getMeasuredWidth()) / 2;
        this.borderTop = getMeasuredHeight() - pointIcon.getMeasuredHeight() - pointTitle.getMeasuredHeight();

        Log.e(TAG, "measureBorder------> borderLeft = " + borderLeft + " borderTop = " + borderTop);
    }

    /**
     * 设置地图上点的图片
     *
     * @param bitmap
     */
    public void setPointIcon(Bitmap bitmap) {
        pointIcon.setImageBitmap(bitmap);
    }

    /**
     * 设置 显示 图标
     */

    public double getFirstX() {
        return firstX;
    }

    public double getFirstY() {
        return firstY;
    }

    public float getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), this.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
