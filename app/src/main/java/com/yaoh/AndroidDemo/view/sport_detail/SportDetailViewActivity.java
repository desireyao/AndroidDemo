package com.yaoh.AndroidDemo.view.sport_detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yaoh.AndroidDemo.R;

public class SportDetailViewActivity extends AppCompatActivity {
    private static final String TAG = "SportDetailViewActivity";

    private SportDetailView sportview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_detail_view);

        sportview = (SportDetailView) findViewById(R.id.sportview);
        sportview.setSelectedListener(new SportDetailView.TouchSelectedListener() {
            @Override
            public void onTouchSelected(int time, int step) {
                Log.e(TAG,"onTouchSelected--->" + ",time = " + time + ",step = " + step);
            }
        });
    }
}
