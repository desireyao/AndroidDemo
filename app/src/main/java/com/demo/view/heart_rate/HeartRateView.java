package com.demo.view.heart_rate;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.demo.R;
import com.demo.utils.ViewUtil;

/**
 * Created by Administrator on 2017/07/15 0015.
 */

public class HeartRateView extends View {
    private static final String TAG = "HeartRateView";

    private int mWidth;
    private int mHeight;

    private Paint mPaint;
    private int mPaintWidth;

    public HeartRateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mPaintWidth = ViewUtil.dp2px(15);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mPaint.setStrokeWidth(mPaintWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        /**
         *match_parent , accurate
         */
        if (widthSpecMode == MeasureSpec.EXACTLY) {
            mWidth = widthSpecSize;
        } else {
            int width = ViewUtil.dp2px(250);
            if (widthSpecMode == MeasureSpec.AT_MOST) ;
            {
                mWidth = Math.min(width, widthSpecSize);
            }
        }

        if (heightSpecMode == MeasureSpec.EXACTLY) {
            mHeight = heightSpecSize;
        } else {
            int height = ViewUtil.dp2px(200);
            if (heightSpecMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(height, heightSpecSize);
            }
        }

        Log.e(TAG, "onMeasure--->mWidth = " + mWidth + ",mHeight = " + mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }


    private boolean isFirst = true;

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight);

        mPaint.setStrokeWidth(30);
        mPaint.setStyle(Paint.Style.STROKE);
        int r = mWidth / 2 - mPaintWidth /2;
        RectF rect = new RectF(-r, -r,
                r, r);

        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
//        canvas.drawArc(rect, 180, 180, false, mPaint);

        canvas.drawArc(rect, 180, 55, false, mPaint);
        canvas.drawArc(rect, 180 + 60, 55, false, mPaint);
        canvas.drawArc(rect, 180 + 60 + 60, 60, false, mPaint);

        canvas.rotate(mDegree);

        Path path = new Path();
        int x0 = -mWidth / 2 + 45;
        int y0 = -30;
        int x1 = x0;
        int y1 = 30;
        int x2 = -mWidth / 2 + mPaintWidth /2;
        int y2 = 0;

        path.moveTo(x0, y0); // 此点为多边形的起点
        path.lineTo(x1, y1);
        path.lineTo(x2, y2);
        path.close(); // 使这些点构成封闭的多边形
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.white));
        canvas.drawPath(path, mPaint);
//        canvas.drawLine(0,0,0,-550,mPaint);


        if (isFirst) {
            startAnimation();
            isFirst = false;
        }
    }


    private float mDegree;

    public void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 180);
        animator.setDuration(10000).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDegree = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }
}
