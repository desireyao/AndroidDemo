package com.yaoh.AndroidDemo.service.normal;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.yaoh.AndroidDemo.aidl.Print;

public class MyService extends Service {
    private static final String TAG = "MyService";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e(TAG, "onBind--->");
        return new MyBinder();

//        return printBinder;
    }

    class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    IBinder printBinder = new Print.Stub() {
        @Override
        public void print(String text) throws RemoteException {
            Log.e(TAG, "printText---> text = " + text);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate--->");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand--->");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy--->");

//        startService(new Intent(this, MyService.class));
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind--->");
        return super.onUnbind(intent);
    }

//    public void printText(String text) {
//        Log.e(TAG, "printText---> text = " + text);
//    }
}
