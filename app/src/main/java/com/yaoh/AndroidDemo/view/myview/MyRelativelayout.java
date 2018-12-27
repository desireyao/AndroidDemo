package com.yaoh.AndroidDemo.view.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


/**
 * Created by yaoh on 2018/3/25.
 */

public class MyRelativelayout extends RelativeLayout {

    private static final String TAG = "MyRelativelayout";

    public MyRelativelayout(Context context, AttributeSet attrs) {
        super(context, attrs);

//        setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.e(TAG,"onTouch --------> event = " + event);
//                return false;
//            }
//        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent---> " + ev);
//        boolean isDispatchTouchEvent = super.dispatchTouchEvent(ev);
//        Log.e(TAG, "dispatchTouchEvent---> " + ev
//                + "\n isDispatchTouchEvent = " + isDispatchTouchEvent);

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.e(TAG, "onInterceptTouchEvent---> event = " + event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                return true;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent---> " + event);
//        boolean isOnTouchEvent = super.onTouchEvent(event);
//        Log.e(TAG, "onTouchEvent---> " + event
//                + "\n view = " + this
//                + "\n isOnTouchEvent = " + isOnTouchEvent);

        return super.onTouchEvent(event);
    }

}
