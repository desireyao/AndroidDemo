package com.yaoh.AndroidDemo.view.msloadingview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yaoh.AndroidDemo.R;

public class TestViewActivity extends AppCompatActivity {

    private MSSearchDeviceLoadingView msSearchDeviceLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);

        msSearchDeviceLoadingView = (MSSearchDeviceLoadingView) findViewById(R.id.laodingView);
        msSearchDeviceLoadingView.startAnimation();
    }
}
