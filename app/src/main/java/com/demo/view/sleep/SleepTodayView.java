package com.demo.view.sleep;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoh on 2017/07/30
 */

public class SleepTodayView extends View {
    private static final String TAG = "SleepCurrentView";

    private int mWidth;
    private int mHeight;

    private Paint mPaint;

    private int mColorDeepSleep;
    private int mColorLightSleep;
    private int mColorWakeUp;

    private List<SleepViewData> dataList = new ArrayList<>();

    public SleepTodayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SleepTodayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
        init();
    }

    private void initAttr(Context mContext, AttributeSet attrs, int defStyleAttr) {
        mColorDeepSleep = getResources().getColor(R.color.sleep_deep);
        mColorLightSleep = getResources().getColor(R.color.sleep_light);
        mColorWakeUp = getResources().getColor(R.color.sleep_wakeup);

        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.SleepTodayView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SleepTodayView_deepSleepColor:
                    mColorDeepSleep = a.getColor(attr, mColorDeepSleep);
                    break;
                case R.styleable.SleepTodayView_lightSleepColor:
                    mColorLightSleep = a.getColor(attr, mColorLightSleep);
                    break;
                case R.styleable.SleepTodayView_wakeUpSleepColor:
                    mColorWakeUp = a.getColor(attr, mColorWakeUp);
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
        mPaint.setStrokeWidth(5);

        initData();
    }

    private void initData() {
        SleepViewData data0 = new SleepViewData(0, 0.2f);
        SleepViewData data1 = new SleepViewData(1, 0.3f);
        SleepViewData data2 = new SleepViewData(2, 0.4f);
        SleepViewData data3 = new SleepViewData(0, 0.1f);
        dataList.add(data0);
        dataList.add(data1);
        dataList.add(data2);
        dataList.add(data3);
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

        Log.e(TAG, "onMeasure--->mWidth = " + mWidth + ",mHeight = " + mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG, "onDraw--->mWidth = " + mWidth + ",mHeight = " + mHeight);
        drawSleepRect(canvas);
    }

    private void drawSleepRect(Canvas canvas) {
        int startY = mHeight / 2;
        int endY = mHeight;

        int tempX = 0;
        for (int i = 0; i < dataList.size(); i++) {
            SleepViewData sleepViewData = dataList.get(i);
            if (sleepViewData.getStatus() == 0) {
                mPaint.setColor(mColorLightSleep);
            } else if (sleepViewData.getStatus() == 1) {
                mPaint.setColor(mColorDeepSleep);
                startY = 0;
            } else {
                mPaint.setColor(mColorWakeUp);
                startY = 0;

            }

            int percentX = (int) (sleepViewData.getPercent() * mWidth);
            canvas.drawRect(tempX, startY, tempX + percentX, endY, mPaint);
            tempX += sleepViewData.getPercent() * mWidth;
        }
    }


//    public void setViewData(ArrayList<MTSleepData.MTSleepDetailData> data, long sleep, long getup) {
//        if (data != null && !data.isEmpty()) {
//            mDatas.clear();
//            long totalTime = getup - sleep;
//            for (int i = 0; i < data.size(); i++) {
//                MTSleepData.MTSleepDetailData detailData = data.get(i);
//                float percent = (detailData.getmStatusEndTime() - detailData.getmStatusStartTime()) * 1.0f
//                        / totalTime;
//                SleepViewData sleepViewData = new SleepViewData(detailData.getmStatus(), percent);
//                mDatas.add(sleepViewData);
//
//                LogTool.LogE_DEBUG(TAG, "percent_" + i + "--->" + percent);
//            }
//        }
//        invalidate();
//    }
}
