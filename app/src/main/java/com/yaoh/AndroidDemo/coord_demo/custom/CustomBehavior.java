package com.yaoh.AndroidDemo.coord_demo.custom;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Package com.yaoh.demo.coord_demo.custom.
 * Created by yaoh on 2017/10/26.
 * <p/>
 * Description:
 */
public class CustomBehavior extends CoordinatorLayout.Behavior<TextView> {

    private static final String TAG = "CustomBehavior";

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        //告知监听的dependency是Button
        Log.e(TAG, "layoutDependsOn--->");
        return dependency instanceof Button;
    }

    @Override
    //当 dependency(Button)变化的时候，可以对child(TextView)进行操作
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
//        child.setX(dependency.getX()+200);
//        child.setY(dependency.getY()+200);
//        child.setText(dependency.getX()+","+dependency.getY());

        Log.e(TAG, "onDependentViewChanged--->");
        child.setX(dependency.getX() + 200);
        child.setY(dependency.getY() + 200);
        return true;
    }
}
