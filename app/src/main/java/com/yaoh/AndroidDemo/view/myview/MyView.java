package com.yaoh.AndroidDemo.view.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by yaoh on 2018/3/25.
 */

public class MyView extends View {

    private static final String TAG = "MyView";

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, " dispatchTouchEvent---> " + event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                return false;
            }
            case MotionEvent.ACTION_UP: {
//                return false;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent---> " + event);
//        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                return false;
//                break;
            }
            case MotionEvent.ACTION_UP: {
//                return false;
                break;
            }
        }
        return true;
    }

}
