package com.yaoh.AndroidDemo.view.heartrate;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.yaoh.AndroidDemo.R;
import com.yaoh.AndroidDemo.view.heartrate.model.HistoryHeartRateData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoh on 2017/03/15.
 * <p>
 * Description:显示历史心率的view
 */

public class HistoryHeartRateChart extends View {
    private final static String TAG = HistoryHeartRateChart.class.getSimpleName();

    private int mWidth;
    private int mHeight;                // 此view的宽高
    private int mXPart = 5;
    private int mYpart = 5;             // x,y轴分的段数

    private int xAxisMarginLeft = 24;
    private int yAxisMarginTop = 3;     // 此单位为dp
    private int mYAxisFontSize = 16;

    private Paint axisTextPaint;
    private Paint yPartLinePaint;
    private Paint pointPaint;

    private int Y_AXIS_MIN = 60;
    private int Y_AXIS_MAX = 220;
    private int X_AXIS_MAX = 3600 * 24;
    private int mLineWidth = 3;
    private int mPointRadius = 6;

    private Path linePath;
    private Path mCurrentPath;
    private float mLength;

    private PathMeasure mPathMeasure; // 用来截取片段

    private String[] xAxisTextArray = new String[]{"00:00", "06:00", "12:00", "18:00", "23:59"};

    private List<HistoryHeartRateData> data = new ArrayList<>();

    public HistoryHeartRateChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistoryHeartRateChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        axisTextPaint = new Paint();
        axisTextPaint.setAntiAlias(true);
        axisTextPaint.setTextSize(sp2px(mYAxisFontSize));
        axisTextPaint.setColor(getResources().getColor(R.color.gray_text_light));

        yPartLinePaint = new Paint();
        yPartLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        yPartLinePaint.setAntiAlias(true);
        yPartLinePaint.setColor(getResources().getColor(R.color.red_sports_text));
        yPartLinePaint.setStrokeWidth(1);

        pointPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setStrokeWidth(mLineWidth);
        pointPaint.setAntiAlias(true);
//        pointPaint.setColor(getResources().getColor(R.color.color_heart_rate_red));

        linePath = new Path();
        mCurrentPath = new Path();
        mPathMeasure = new PathMeasure();

    }

    public void setData(List<HistoryHeartRateData> data) {
        this.data = data;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            throw new IllegalArgumentException("width must be EXACTLY");
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else if (widthMeasureSpec == MeasureSpec.AT_MOST) {
            throw new IllegalArgumentException("height must be EXACTLY");
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMainContentBg(canvas);
        drawXaxisText(canvas);
        drawYaxisText(canvas);
        drawPoint(canvas);
        drawLineAnimation(canvas);
    }

    private void drawPoint(Canvas canvas) {
        pointPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < data.size(); i++) {
            HistoryHeartRateData heartRateData = data.get(i);
            float cx = heartRateData.getTime() * 1.0f / X_AXIS_MAX * (mWidth - dp2px(80)) + dp2px(40);
            float cy = mHeight - heartRateData.getRate() * 1.0f / Y_AXIS_MAX * mHeight + dp2px(25);
            canvas.drawCircle(cx, cy, mPointRadius, pointPaint);
        }
    }

    /**
     * 画折线和圆点
     */
    private void drawLineAnimation(Canvas canvas) {
        if (mLength == 0) {
            for (int i = 0; i < data.size(); i++) {
                HistoryHeartRateData heartRateData = data.get(i);
                float cx = heartRateData.getTime() * 1.0f / X_AXIS_MAX * (mWidth - dp2px(80)) + dp2px(40);
                float cy = mHeight - heartRateData.getRate() * 1.0f / Y_AXIS_MAX * mHeight + dp2px(25);
                if (i == 0) {
                    linePath.moveTo(cx, cy);
                }
                linePath.lineTo(cx, cy);
            }
            mPathMeasure.setPath(linePath, false);
            mLength = (int) mPathMeasure.getLength();
            startPathAnim(2000, mLength);
        } else {
            pointPaint.setStyle(Paint.Style.STROKE);
            mPathMeasure.getSegment(0, value, mCurrentPath, true);
            canvas.drawPath(mCurrentPath, pointPaint);
        }
    }

    private void drawMainContentBg(Canvas canvas) {
        Paint mainContentPaint = new Paint();
        mainContentPaint.setStyle(Paint.Style.FILL);
        mainContentPaint.setColor(getResources().getColor(R.color.gray_light));
        Rect rect = new Rect(0, 0, mWidth, mHeight - getTextHeight(axisTextPaint));
        canvas.drawRect(rect, mainContentPaint);
    }

    private void drawXaxisText(Canvas canvas) {
        int xInterval = mWidth / mXPart;
        for (int i = 0; i < xAxisTextArray.length; i++) {
            int x = xInterval * i + dp2px(16);
            canvas.drawText(xAxisTextArray[i], x, mHeight - dp2px(5), axisTextPaint);
        }
    }

    private void drawYaxisText(Canvas canvas) {
        int yInterval = (mHeight - getTextHeight(axisTextPaint)) / mYpart;
        for (int i = 0; i < mYpart; i++) {
            String yAxisText = String.valueOf((Y_AXIS_MAX - Y_AXIS_MIN) / (mYpart - 1) * (mYpart - i - 1) + Y_AXIS_MIN);

            int y = yInterval * i + yInterval / 2 + getTextHeight(axisTextPaint) / 2;
            canvas.drawText(yAxisText, xAxisMarginLeft, y, axisTextPaint);
            if (i < mYpart - 1) {
                canvas.drawLine(0, y + yInterval / 2 - getTextHeight(axisTextPaint) / 2,
                        mWidth, y + yInterval / 2 - getTextHeight(axisTextPaint) / 2, yPartLinePaint);
            }
        }
    }

    private int dp2px(int dip) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    private int sp2px(float spValue) {
        float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private int getTextHeight(Paint mPaintText) {
        Paint.FontMetricsInt fontMetrics = mPaintText.getFontMetricsInt();
//        int textHeight = fontMetrics.bottom - fontMetrics.top;
        int textHeight = fontMetrics.descent - fontMetrics.ascent;
        return textHeight;
    }

    private float getTextWidth(String str, Paint paint) {
        float textWidth = paint.measureText(str);
        return textWidth;
    }

    float value;

    // 开启路径动画
    public void startPathAnim(long duration, float length) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, length);
        valueAnimator.setDuration(duration);
//        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (Float) animation.getAnimatedValue();
//                mPathMeasure.getSegment(0, value, mCurrentPath, true);
                postInvalidate();
            }
        });
        valueAnimator.start();
    }
}
