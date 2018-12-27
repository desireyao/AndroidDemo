package com.yaoh.AndroidDemo.coord_demo.custom3;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yaoh.AndroidDemo.R;
import com.yaoh.AndroidDemo.common.widget.CustomScrollView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class Custom3Activity extends AppCompatActivity implements CustomScrollView.OnScrollChangeListener {
    private static final String TAG = "Custom3Activity";

    private SystemBarTintManager mTintManager;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mAlpha > 1)
                return;

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAlpha += 0.1f;
//                    mTintManager.setStatusBarAlpha(mAlpha);
                    toolbar.setAlpha(mAlpha);
                    mHandler.sendEmptyMessage(1);
                }
            }, 1000);
        }
    };

    private float mAlpha = 0.f;

    Toolbar toolbar;
    CustomScrollView customScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusTransParentBarMode(this);

        setContentView(R.layout.activity_custom3);

        setToolbar();
    }

    public void statusTransParentBarMode(Activity activity) {
        Window mWindow = activity.getWindow();
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mTintManager = new SystemBarTintManager(activity);
        mTintManager.setStatusBarTintColor(Color.TRANSPARENT);
        mTintManager.setStatusBarTintEnabled(true);
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.gray));
        toolbar.setAlpha(0f);
        toolbar.setPadding(0, 55, 0, 0);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        customScrollView = (CustomScrollView) findViewById(R.id.customscrollview);
        customScrollView.setOnScrollChangeListener(this);
    }

    @Override
    public void onScrollChanged(CustomScrollView scrollView, int l, int t, int oldl, int oldt) {
        Log.e(TAG, "l = " + l + " t = " + t);
        if (t >= 0)
            toolbar.setAlpha(t / 300.f);
    }
}
