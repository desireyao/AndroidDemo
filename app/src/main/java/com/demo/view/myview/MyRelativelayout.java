package com.demo.view.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


/**
 * Created by yaoh on 2018/3/25.
 */

public class MyRelativelayout extends RelativeLayout{

    private static final String TAG = "MyRelativelayout";


    public MyRelativelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG,"dispatchTouchEvent--->action：" +ev.getAction() + "  view: " + this);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG,"onInterceptTouchEvent--->action：" +ev.getAction() + "  view: " + this);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent--->action：" + event.getAction() + "  view: " + this);
        return super.onTouchEvent(event);
    }

}
