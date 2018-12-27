package com.yaoh.AndroidDemo.bluetooth.autopair;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yaoh.AndroidDemo.R;

public class AutoPairActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AutoPairActivity";

    /**
     * Called when the activity is first created.
     */
    private Button autopairbtn = null;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_pair);

        autopairbtn = (Button) findViewById(R.id.button1);
        autopairbtn.setOnClickListener(this);

        BluetoothReceiver bluetoothReceiver = new BluetoothReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(bluetoothReceiver, filter);

    }

    public void onClick(View arg0) {
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();// 异步的，不会等待结果，直接返回。
        } else {
            Log.e(TAG, "startDiscovery--->");
            bluetoothAdapter.startDiscovery();
        }
    }
}
