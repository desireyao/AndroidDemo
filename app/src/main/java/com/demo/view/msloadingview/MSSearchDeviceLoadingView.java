package com.demo.view.msloadingview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import com.andview.refreshview.utils.LogUtils;
import com.demo.R;
import com.demo.utils.ViewUtil;

/**
 * Created by yaoh on 2018/3/28.
 */

public class MSSearchDeviceLoadingView extends View {

    private Context mContext;
    private Paint mPaint;

    private float mItemRadius = 10;
    private int mItemCount = 5;
    private float mItemPadding = 10;
    private float mItemHeight = 10;
    private int mSpeed = 10;

    public MSSearchDeviceLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MSSearchDeviceLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        initAttr(attrs, defStyleAttr);
        initView();
    }

    private void initAttr(AttributeSet attrs, int defStyleAttr) {
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.MSSearchDeviceLoadingView,
                defStyleAttr, 0);

        if (attr == null)
            return;

        mItemRadius = attr.getDimension(R.styleable.MSSearchDeviceLoadingView_itemRadius
                , ViewUtil.dp2px(5));
        mItemCount = attr.getInteger(R.styleable.MSSearchDeviceLoadingView_itemCount
                , 5);
        mItemPadding = attr.getDimension(R.styleable.MSSearchDeviceLoadingView_itemPadding
                , ViewUtil.dp2px(5));
        mItemHeight = attr.getDimension(R.styleable.MSSearchDeviceLoadingView_itemHeight
                , ViewUtil.dp2px(32));
        mSpeed = attr.getInteger(R.styleable.MSSearchDeviceLoadingView_speed
                , 1);
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.gray));//设置画笔颜色
        mPaint.setStrokeWidth(5);              //线宽
        mPaint.setStyle(Paint.Style.FILL);                   //空心效果
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtils.e("width = " + getMeasuredWidth() + " height = " + getMeasuredHeight());
        int width = (int) ((mItemRadius * 2) * mItemCount + mItemPadding * (mItemCount - 1));
        int height = (int) mItemHeight;

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mItemCount; i++) {
            float left = (mItemRadius * 2) * i + mItemPadding * i;
            float top = 0;
            float right = left + mItemRadius * 2;
            float bottom = mItemHeight;
            RectF rectF = new RectF(left, top, right, bottom);                       //RectF对象

            if (index == i) {
                mPaint.setColor(getResources().getColor(R.color.colorGreen));
            } else {
                mPaint.setColor(getResources().getColor(R.color.gray));
            }

            canvas.drawRoundRect(rectF, mItemRadius, mItemRadius, mPaint);        //绘制圆角矩形
        }
    }


    private int index = 0;

    public void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mItemCount);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                index = (int) animation.getAnimatedValue();
                LogUtils.e("index = " + index + "|" + animation.getAnimatedValue());
                postInvalidate();
            }
        });

        valueAnimator.setRepeatCount(Animation.INFINITE);
        valueAnimator.setDuration(mItemCount * 250);
        valueAnimator.start();
    }

}
