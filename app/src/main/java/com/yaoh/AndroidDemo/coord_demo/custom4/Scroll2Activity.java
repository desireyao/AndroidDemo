package com.yaoh.AndroidDemo.coord_demo.custom4;

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
import android.widget.TextView;

import com.yaoh.AndroidDemo.R;
import com.yaoh.AndroidDemo.common.widget.CustomScrollView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class Scroll2Activity extends AppCompatActivity implements CustomScrollView.OnScrollChangeListener {
    private static final String TAG = "Scroll2Activity";

    private Toolbar mToolbar;
    private CustomScrollView scrollView;

    private TextView text;
    private TextView include_toolbar_title;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            text.setTextSize(textSize);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusTransParentBarMode(this);
        setContentView(R.layout.activity_scroll2);

        initToolbar();

//        mHandler.postDelayed(task, 1000);
//        new Thread(task).start();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        include_toolbar_title = (TextView) mToolbar.findViewById(R.id.include_toolbar_title);

        scrollView = (CustomScrollView) findViewById(R.id.scrollView);
        scrollView.setOnScrollChangeListener(this);

        text = (TextView) findViewById(R.id.text);
    }

    public static void statusTransParentBarMode(Activity activity) {
        Window mWindow = activity.getWindow();
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        SystemBarTintManager mTintManager = new SystemBarTintManager(activity);
        mTintManager.setStatusBarTintColor(Color.TRANSPARENT);
        mTintManager.setStatusBarTintEnabled(true);
    }


    float textSize;
    @Override
    public void onScrollChanged(CustomScrollView scrollView, int l, int t, int oldl, int oldt) {
        Log.e(TAG, "onScrollChanged--->" + " l = " + l + " t = " + t);

        if (t < 0) {
            t = 0;
        }

        if (t > 260) {
            include_toolbar_title.setVisibility(View.VISIBLE);
            text.setVisibility(View.INVISIBLE);
            mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            include_toolbar_title.setVisibility(View.INVISIBLE);
            text.setVisibility(View.VISIBLE);
            mToolbar.setBackgroundColor(getResources().getColor(R.color.vifrification));

            textSize = 50 - t / 10.f;
            Log.e(TAG, "textSize = " + textSize);
            mHandler.sendEmptyMessage(1);
        }
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) text.getLayoutParams();
//        Log.e(TAG,"params.width = " + params.width + " params.height = " + params.height);
//        params.width = 30;
//        params.height = 30;
//
//        text.setLayoutParams(params);
    }

    private int mSize = 60;

    private boolean isPlus = false;
    Runnable task = new Runnable() {
        @Override
        public void run() {
            while (true) {

                if (mSize <= 0) {
                    isPlus = true;
                } else if (mSize >= 60) {
                    isPlus = false;
                }

                if (isPlus)
                    mSize++;
                else
                    mSize--;

                Log.e(TAG, "run---> mSize = " + mSize);

                mHandler.sendEmptyMessage(1);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
