package com.demo.view.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by yaoh on 2018/3/25.
 */

public class MyView extends View{

    private static final String TAG = "MyView";


    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG,"dispatchTouchEvent--->action：" +ev.getAction() + "  view: " + this);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent--->action：" + event.getAction() + "  view: " + this);
        boolean result = super.onTouchEvent(event);
        Log.e(TAG,"MyView---> result：" + result);
        return result;
    }

}
