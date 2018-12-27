package com.yaoh.AndroidDemo.java8;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yaoh.AndroidDemo.R;

public class TestJava8Activity extends AppCompatActivity {

    private static final String TAG = "TestJava8Activity";

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_java8);

        mHandler.post(() -> {
            Log.e(TAG, "lambda--->test");
        });
    }


}
