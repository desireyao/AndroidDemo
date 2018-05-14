package com.demo.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.demo.R;
import com.demo.service.normal.MyService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AIDL1Activity extends AppCompatActivity {

    private static final String TAG = "ServiceTestActivity";

    private boolean mIsBound;
    private MyService mBoundService;

    private Print mPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_startService)
    public void start_Service() {
        Log.e(TAG, "启动service--->");
        startService(new Intent(this, MyService.class));
    }

    @OnClick(R.id.btn_stopService)
    public void stop_Service() {
        Log.e(TAG, "停止service--->");
        stopService(new Intent(this, MyService.class));
    }


    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(TAG, "mConnection--->service = " + service);

//            MyService.MyBinder binder = (MyService.MyBinder) service;
//            mBoundService = binder.getService();

            mPrint = Print.Stub.asInterface(service);
            Log.e(TAG, "onServiceConnected--->");
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            Log.e(TAG, "onServiceDisconnected--->");
        }
    };

    @OnClick(R.id.btn_bindService)
    public void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(this, MyService.class), mConnection,
                Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    @OnClick(R.id.btn_unbindService)
    public void doUnbindService() {
//        if (mIsBound) {
        // Detach our existing connection.
        unbindService(mConnection);
        mIsBound = false;
//        }
    }

    @OnClick(R.id.btn_test)
    public void test() {
//        mBoundService.printText("threadName = " + Thread.currentThread().getName());
        try {
            mPrint.print("threadName = " + Thread.currentThread().getName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
