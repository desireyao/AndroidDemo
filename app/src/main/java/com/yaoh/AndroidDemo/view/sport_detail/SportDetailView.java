package com.yaoh.AndroidDemo.view.sport_detail;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.yaoh.AndroidDemo.R;
import com.yaoh.AndroidDemo.utils.ViewUtil;

/**
 * Package com.yaoh.demo.view.sport_detail.
 * Created by yaoh on 2017/07/13.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class SportDetailView extends View {
    private static final String TAG = "SportTetailView";

    private Paint mPaint;
    private Paint mPaintText;

    private int mXaxisTextSize;

    private int mWidth;
    private int mHeight;
    private int mPaintWidth;
    private int mXaxisTextWidth;

    private int marginBottom; // 图表距离底部的距离
    private int marginLR;     // 图标距离左右的距离

    private SparseArray<Integer> datas = new SparseArray<>(); // 分钟 步数
    private int mSelectedKey;

    private String[] textArray = new String[]{"00:00", "06:00", "12:00", "18:00", "23:59"};

    private int total_time;
    private int total_xaxis;

    public SportDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
        init();
    }

    private void initData() {
        marginBottom = ViewUtil.dp2px(16);
        marginLR = ViewUtil.dp2px(8);
        mPaintWidth = ViewUtil.dp2px(3);
        mXaxisTextSize = ViewUtil.sp2px(8);

        datas.put(0 * 60, 220);
        datas.put(6 * 60, 120);
        datas.put(12 * 60, 200);
        datas.put(18 * 60, 350);
        datas.put(24 * 60, 100);
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(mPaintWidth);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.setColor(getResources().getColor(R.color.gray_dark));
        mPaintText.setTextSize(mXaxisTextSize);

        mXaxisTextWidth = (int) mPaintText.measureText(textArray[textArray.length - 1]);
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

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG, "onDraw--->mWidth = " + mWidth + ",mHeight = " + mHeight);
        mPaint.setColor(getResources().getColor(R.color.gray));
        canvas.drawRect(new RectF(0, 0, mWidth, mHeight - marginBottom), mPaint);
        drawXaxisText(canvas);
        drawMainChart(canvas);
    }

    private void drawMainChart(Canvas canvas) {
        int startY = mHeight - marginBottom;
        total_time = 24 * 60;
        total_xaxis = mWidth - marginLR * 2 - mXaxisTextWidth;

        for (int i = 0; i < datas.size(); i++) {
            int x = (int) ((datas.keyAt(i) * 1.0f / total_time) * total_xaxis + marginLR + mXaxisTextWidth / 2);
            int y = datas.valueAt(i);
            if (x == mSelectedKey) {
                mPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            } else {
                mPaint.setColor(getResources().getColor(R.color.colorPrimary));
            }
            canvas.drawLine(x, startY, x, startY - y, mPaint);

            float radus = mPaintWidth / 2f;
            float cx = x;
            float cy = startY - y;
            canvas.drawCircle(cx, cy, radus, mPaint);
        }
    }

    private void drawXaxisText(Canvas canvas) {
        int xInterval = (mWidth - marginLR * 2 - mXaxisTextWidth) / (textArray.length - 1);
        for (int i = 0; i < textArray.length; i++) {
            int x = xInterval * i + marginLR;
            if (i == textArray.length - 1) {
                canvas.drawText(textArray[i], x, mHeight - ViewUtil.dp2px(5), mPaintText);
            } else {
                canvas.drawText(textArray[i], x, mHeight - ViewUtil.dp2px(5), mPaintText);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                float x = event.getX();
                float y = event.getY();
                touchSelectedState(x, y);
//                Log.e(TAG, "ACTION_DOWN---> x = " + x + ",y = " + y);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                float x = event.getX();
                float y = event.getY();
//                Log.e(TAG, "ACTION_MOVE---> x = " + x + ",y = " + y);
                touchSelectedState(x, y);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 触摸选中状态
     *
     * @param x
     * @param y
     */
    private void touchSelectedState(float x, float y) {
        for (int i = 0; i < datas.size(); i++) {
            int positionX = (int) ((datas.keyAt(i) * 1.0f / total_time) * total_xaxis + marginLR + mXaxisTextWidth / 2);
            if (Math.abs(x - positionX) <= mPaintWidth / 2f) {
                mSelectedKey = positionX;
                if (selectedListener != null) {
                    selectedListener.onTouchSelected(datas.keyAt(i), datas.valueAt(i));
                }
                invalidate();
            }
        }
    }

    public void setSelectedListener(TouchSelectedListener selectedListener) {
        this.selectedListener = selectedListener;
    }

    private TouchSelectedListener selectedListener;

    public interface TouchSelectedListener {
        void onTouchSelected(int time, int step);
    }
}
