package com.demo.view.sleep;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.demo.R;

/**
 * Created by yaoh on 2017/07/18
 */

public class SleepCurrentView2 extends View {
    private static final String TAG = "SleepCurrentView";

    private int mWidth;
    private int mHeight;

    private Paint mPaint;
    private int mColorDeepSleep = Color.parseColor("#309ff9");
    private int mColorLightSleep = Color.parseColor("#C2E2F9");
    private int mColorUpNight = Color.parseColor("#F9E1C5");

    private int mPaintWidth = 30;

    public SleepCurrentView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SleepCurrentView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
        init();
    }

    private void initAttr(Context mContext, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.SleepCurrentView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SleepCurrentView_deepSleepColor:
                    mColorDeepSleep = a.getColor(attr, mColorDeepSleep);
                    break;
                case R.styleable.SleepCurrentView_lightSleepColor:
                    mColorLightSleep = a.getColor(attr, mColorLightSleep);
                    break;
                case R.styleable.SleepCurrentView_wakeUpSleepColor:
                    mColorUpNight = a.getColor(attr, mColorUpNight);
                    break;
                case R.styleable.SleepCurrentView_sleepColorHeight:
                    mPaintWidth = a.getDimensionPixelSize(attr, mPaintWidth);
                    Log.e(TAG, "mPaintWidth = " + mPaintWidth);
                    break;
                default:
                    break;
            }
        }
        a.recycle();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mPaintWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.EXACTLY) {
            mWidth = widthSpecSize;
        }

        if (heightSpecMode == MeasureSpec.EXACTLY) {
            mHeight = heightSpecSize;
        }

        mHeight = mPaintWidth;
        Log.e(TAG, "onMeasure--->mWidth = " + mWidth + ",mHeight = " + mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG, "onDraw--->mWidth = " + mWidth + ",mHeight = " + mHeight);
        canvas.translate(getPaddingLeft(), 0);
        int startX = mPaintWidth / 2;
        int startY = mHeight / 2;
        int endX = startX + (mWidth - getPaddingLeft() - getPaddingRight() - mPaintWidth);
        int endY = startY;
        int totalX = endX - startX + mPaintWidth;

        int layerId = canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(mColorUpNight);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(startX, startY, endX, endY, mPaint);

        //使用CLEAR作为PorterDuffXfermode绘制蓝色的矩形
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setColor(mColorDeepSleep);
        canvas.drawLine(0, startY, totalX * 0.5f, endY, mPaint);
        mPaint.setColor(mColorLightSleep);
        canvas.drawLine(totalX * 0.5f, startY, totalX * 0.6f, endY, mPaint);
        mPaint.setColor(mColorDeepSleep);
        canvas.drawLine(totalX * 0.6f, startY, totalX *0.98f, endY, mPaint);

        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);

//        mPaint.setStrokeCap(Paint.Cap.BUTT);
//        mPaint.setColor(mColorDeepSleep);
//        canvas.drawLine(x, startY, totalX * 0.4f, endY, mPaint);
//
//        mPaint.setStrokeCap(Paint.Cap.BUTT);
//        mPaint.setColor(mColorUpNight);
//        canvas.drawLine(totalX * 0.5f, startY, totalX * 0.52f, endY, mPaint);
//
//        mPaint.setStrokeCap(Paint.Cap.BUTT);
//        mPaint.setColor(mColorDeepSleep);
//        canvas.drawLine(totalX * 0.52f, startY, totalX * 0.53f, endY, mPaint);
    }
}
