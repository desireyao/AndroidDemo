package com.demo.coord_demo.custom2;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.R;

/**
 * Package com.demo.coord_demo.custom.
 * Created by yaoh on 2017/10/26.
 * <p/>
 * Description:
 */
public class Custom2Behavior extends CoordinatorLayout.Behavior<Toolbar> {

    private static final String TAG = "CustomBehavior";

    private TextView tv_title;

    public Custom2Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        //告知监听的dependency是Button
//        Log.e(TAG, "layoutDependsOn---> dependency = " + dependency + " child = " + child);
        return dependency instanceof AppBarLayout;
    }

    @Override
    //当 dependency(Button)变化的时候，可以对child(TextView)进行操作
    public boolean onDependentViewChanged(CoordinatorLayout parent, Toolbar child, View dependency) {
//        child.setX(540);
//        if (dependency.getY() + 500 > 0)
//            child.setY(dependency.getY() + 500);

//        child.setText(dependency.getX()+","+dependency.getY());

        Log.e(TAG, "onDependentViewChanged--->"
                + "\ndependency.getX() = " + dependency.getX()
                + "\ndependency.getY() = " + dependency.getY());

        if(tv_title == null){
            tv_title = (TextView) dependency.findViewById(R.id.tv_title);
        }
        tv_title.setY(dependency.getY() + 500);

        return true;
    }
}
