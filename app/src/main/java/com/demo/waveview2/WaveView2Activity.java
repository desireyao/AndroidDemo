package com.demo.waveview2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.R;

public class WaveView2Activity extends AppCompatActivity {

    private WaveHelper mWaveHelper;

    private int mBorderColor = Color.parseColor("#44FFFFFF");
    private int mBorderWidth = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_view2);

        WaveView waveView = (WaveView) findViewById(R.id.waveView);
        waveView.setShapeType(WaveView.ShapeType.SQUARE);

        waveView.setWaveColor(
                Color.parseColor("#28f16d7a"),
                Color.parseColor("#3cf16d7a"));
        mBorderColor = Color.parseColor("#44FFFFFF");
        waveView.setBorder(mBorderWidth, mBorderColor);

        mWaveHelper = new WaveHelper(waveView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mWaveHelper.start();
    }
}
