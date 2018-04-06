package com.demo.view.attained;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.demo.R;
import com.demo.utils.ViewUtil;

/**
 * Package com.demo.view.attained.
 * Created by yaoh on 2017/07/21.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class AttainedView extends View {
    private static final String TAG = "ContinueAttainedView";
    private Paint mPaint;

    private Paint mTextPaint;

    private int mWidth;
    private int mHeight;

    private int mColorStart = Color.parseColor("#ffffff");
    private int mColorEnd = Color.parseColor("#49BCCE");
    private int mItemWidth = 50;
    private int mPartCount = 7;

    private int mBottomLineWidth;

    public AttainedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AttainedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
        init();
    }

    private void initAttr(Context mContext, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.AttainedView,
                defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.AttainedView_attainItemStartColor:
                    mColorStart = a.getColor(attr, mColorStart);
                    break;
                case R.styleable.AttainedView_attainItemEndColor:
                    mColorEnd = a.getColor(attr, mColorEnd);
                    break;
                case R.styleable.AttainedView_attainItemWidth:
                    mItemWidth = a.getDimensionPixelSize(attr, mItemWidth);
                    break;
                default:
                    break;
            }
        }
        a.recycle();
    }

    private void init() {
        mBottomLineWidth = ViewUtil.dp2px(3);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setColor(getResources().getColor(R.color.white));
        mTextPaint.setTextSize(ViewUtil.sp2px(14));
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

    private int mSpace;
    private int mTotalx;

    @Override
    protected void onDraw(Canvas canvas) {
        mTotalx = mWidth - getPaddingLeft() - getPaddingRight();
        mSpace = (mTotalx - mPartCount * mItemWidth) / (mPartCount + 1);
        Log.e(TAG, "mTotalx = " + mTotalx + ",mSpace = " + mSpace);
        drawAttainLineAndText(canvas);
        drawBottomLine(canvas);

        drawAttainItem(canvas);
        drawAxisText(canvas);
    }

    private void drawBottomLine(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(mBottomLineWidth);
        int color0 = getResources().getColor(R.color.gray_light);
        int color1 = getResources().getColor(R.color.gray_mid);
        int[] colors = new int[]{color0, color1};
        LinearGradient linearGradient = new LinearGradient(0, mHeight - mBottomLineWidth, 0, mHeight, colors,
                null, LinearGradient.TileMode.CLAMP);
        paint.setShader(linearGradient);
        canvas.drawLine(getPaddingLeft(),
                mHeight - mBottomLineWidth / 2,
                mWidth - getPaddingRight(),
                mHeight - mBottomLineWidth / 2, paint);
    }

    private void drawAttainLineAndText(Canvas canvas) {
        Path mPath = new Path();
        mPath.moveTo(getPaddingLeft(), mHeight / 2);
        mPath.lineTo(mWidth - getPaddingRight(), mHeight / 2);
        Paint mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.gray_dark));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        canvas.drawPath(mPath, mPaint);
    }

    private void drawAttainItem(Canvas canvas) {
        int[] colors = new int[]{mColorStart, mColorEnd};

        int tempEndX = getPaddingLeft();
        for (int i = 0; i < mPartCount; i++) {
            int startX = mSpace + tempEndX;
            int endX = startX + mItemWidth;
            int endY = i * 60;

            LinearGradient linearGradient = new LinearGradient(0, endY, 0, mHeight, colors,
                    null, LinearGradient.TileMode.CLAMP);
            mPaint.setShader(linearGradient);
            RectF rect = new RectF(startX, endY, endX, mHeight);
            canvas.drawRoundRect(rect, 10, 10, mPaint);
            tempEndX = endX;
        }
    }

    private void drawAxisText(Canvas canvas) {
        String text = "1/7";
        int tempEndX = getPaddingLeft();
        for (int i = 0; i < mPartCount; i++) {
            if (i == mPartCount - 1) {
                text = "今天";
            }
            float textWidth = mTextPaint.measureText(text);
            int startX = mSpace + mItemWidth + tempEndX;

            Paint.FontMetrics fm = mTextPaint.getFontMetrics();
            float baseline = mHeight - fm.bottom;
            canvas.drawText(text, startX - mItemWidth / 2 - textWidth / 2, baseline, mTextPaint);
            tempEndX = startX;
        }
    }
}
