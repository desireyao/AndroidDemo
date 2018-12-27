package com.yaoh.AndroidDemo.coord_demo.custom;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yaoh.AndroidDemo.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;


public class CustomCoordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusTransParentBarMode(this);


        setContentView(R.layout.activity_custom_coord);
    }

    public static void statusTransParentBarMode(Activity activity) {
        Window mWindow = activity.getWindow();
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        SystemBarTintManager mTintManager = new SystemBarTintManager(activity);
        mTintManager.setStatusBarTintColor(Color.TRANSPARENT);
        mTintManager.setStatusBarTintEnabled(true);
    }
}
