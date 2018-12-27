package com.yaoh.AndroidDemo.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yaoh.AndroidDemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class HandlerActivity extends AppCompatActivity implements FragmentOne.OnFragmentInteractionListener {
    private static final String TAG = "HandlerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        init();
    }


    private void init() {
        FragmentOne fragmentOne = FragmentOne.newInstance("fragmentone");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the container with the new fragment
        ft.add(R.id.contain, fragmentOne);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Execute the changes specified
        ft.commit();

    }


    Handler mMainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "handleMessage--->currentThread().getName = "
                    + Thread.currentThread().getName()
                    + "\t message = " + msg);
        }
    };

    @OnClick(R.id.btn_crash)
    public void startCrash() {
//        throw new RuntimeException("This will crash the app");
//        finish();

//        Message msg = new Message();
        Message msg = Message.obtain();
        msg.what = 100;
        msg.getTarget().sendMessage(msg);

    }

    @OnClick(R.id.btn_handler)
    public void testHandler() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Handler mHandler = new Handler(Looper.myLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        Log.e(TAG, "handleMessage testHandler --->currentThread().getName = "
                                + Thread.currentThread().getName());
                    }
                };
                mHandler.sendEmptyMessage(0);
                Looper.loop();

                mMainHandler.sendEmptyMessage(1);
            }
        }).start();
    }



    @Override
    public void onFragmentInteraction(String reslut) {
        Log.d(TAG, "onFragmentInteraction--->" + reslut);
    }
}
