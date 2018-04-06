package com.demo.view.arc_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo.R;

public class ArcActivity extends AppCompatActivity implements View.OnClickListener {

    private SportProgressView sportview;
    private Button btn_set_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc);

        sportview = (SportProgressView) findViewById(R.id.sportview);
        sportview.setOnClickListener(this);

        btn_set_progress = (Button) findViewById(R.id.btn_set_progress);
        btn_set_progress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sportview: {
                Toast.makeText(getApplicationContext(), "您点击了--->sportview", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.btn_set_progress: {
                sportview.setProgress(80);
                break;
            }

            default:
                break;

        }
    }
}
