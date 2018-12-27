package com.yaoh.AndroidDemo.view.arc_demo;

import android.app.Application;

import com.yaoh.AndroidDemo.utils.ViewUtil;

/**
 * Package com.yaoh.demo.view.arc_demo.
 * Created by yaoh on 2017/07/13.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ViewUtil.init(this);
    }
}
