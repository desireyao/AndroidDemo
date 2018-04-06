package com.demo.view.myview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.demo.R;

public class MyViewActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MyViewActivity";

    private MyRelativelayout view1;
    private MyView view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);

        view1 = (MyRelativelayout) findViewById(R.id.view1);
        view1.setOnClickListener(this);

        view2 = (MyView) findViewById(R.id.view2);
        view2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG,"onClick--->" + v);
    }
}
