package com.demo.waveview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.R;

public class WaveViewActivity extends AppCompatActivity {

    private MyWaveView myWaveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_view);

//        myWaveView = (MyWaveView) findViewById(R.id.myWaveView);
//        myWaveView.startMove();
    }



}
