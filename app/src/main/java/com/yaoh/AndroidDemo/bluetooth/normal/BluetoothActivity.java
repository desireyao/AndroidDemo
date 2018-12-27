package com.yaoh.AndroidDemo.bluetooth.normal;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.yaoh.AndroidDemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BluetoothActivity extends AppCompatActivity {

    private static final String TAG = "BluetoothActivity";

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;

    private MyService myService;

    //    private static final String BLE_ADDRESS = "6C:A8:28:D1:2C:5D";
    private static final String BLE_ADDRESS = "FF:1E:85:79:BC:7A";
//    private static final String BLE_ADDRESS = "02:02:55:5C:A2:B5";


    @BindView(R.id.tv_content)
    TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        ButterKnife.bind(this);
        init();

//        startService(new Intent(this, MyService.class));
        bindService(new Intent(this, MyService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    private void init() {
        mBluetoothManager = (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();


        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(MyService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(MyService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(MyService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(MyService.EXTRA_DATA);
        registerReceiver(mGattUpdateReceiver, intentFilter);
    }

    @OnClick(R.id.btn_startScan)
    public void startScan() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mBluetoothAdapter.stopLeScan(mLeScanCallback);
//                mBluetoothAdapter.startLeScan(mLeScanCallback);
//            }
//        }).start();
        scanLeDevice(true);
    }

    @OnClick(R.id.btn_stopScan)
    public void stopScan() {
        scanLeDevice(false);
    }


    @OnClick(R.id.btn_disconnect)
    public void disconnect() {
        myService.disconnect();
    }


    private boolean mScanning;

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }


    public static String LogBytes2Hex(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        if (bytes != null) {
            for (int i = 0; i < bytes.length; i++) {
                buffer.append(String.format("%02X ", bytes[i]));
            }
        }
        return buffer.toString();
    }

    BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
//            Log.e(TAG, "onLeScan---> name = " + device.getName()
//                    + "\t address = " + device.getAddress()
//                    + "\t rssi = " + rssi);

            Log.e(TAG, "DeviceName = " + device.getName() + " "  + LogBytes2Hex(scanRecord));

//            if (TextUtils.equals(device.getAddress(), BLE_ADDRESS)) {
//                if (mScanning) {
//                    Log.e(TAG, "开始连接--->" + device.getAddress());
//                    mScanning = false;
//                }
//
//                mBluetoothAdapter.stopLeScan(mLeScanCallback);
//                myService.connect(device);
//            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {

            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            myService = binder.getService();
            if (!myService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            Log.e(TAG, "onServiceConnected--->");
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.e(TAG, "onServiceDisconnected--->");
        }
    };

    private int mCount;
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.e(TAG, "onReceive---> action = " + action);
            if (MyService.ACTION_GATT_CONNECTED.equals(action)) {
                scanLeDevice(false);
                mCount++;
                tv_content.setText("连接成功--->" + mCount + "次");
                myService.disconnect();
            } else if (MyService.ACTION_GATT_DISCONNECTED.equals(action)) {
//                tv_content.setText("连接失败--->");
                scanLeDevice(true);
            }
        }
    };
}
