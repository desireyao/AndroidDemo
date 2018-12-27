package com.yaoh.AndroidDemo.permission;

import android.Manifest;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yaoh.AndroidDemo.R;
import com.yxp.permission.util.lib.PermissionUtil;

public class PermissonActivity extends AppCompatActivity {

    private Button btn_permisson;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permisson);
        mContext = this;

        btn_permisson = (Button) findViewById(R.id.btn_permisson);
        btn_permisson.setOnClickListener(mClickListener);

        int CAMERA = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int coarse = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int fine = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        Log.e("permissionState：", "CAMERA:" + CAMERA  + "_" + "coarse:"+coarse + "_" + "fine:"+fine);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
//            PermissionUtil.getInstance().request(new String[]{
//                    Manifest.permission.CAMERA,
//                    Manifest.permission.READ_CONTACTS,
//                    Manifest.permission.READ_SMS,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION},new PermissionResultAdapter() {
//
//                @Override
//                public void onPermissionGranted(String... permissions) {
//                    Toast.makeText(PermissonActivity.this, permissions[0] + " is granted", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onPermissionDenied(String... permissions) {
//                    Toast.makeText(PermissonActivity.this, permissions[0] + " is denied", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onRationalShow(String... permissions) {
//                    Toast.makeText(PermissonActivity.this, permissions[0] + " is rational", Toast.LENGTH_SHORT).show();
//
//                }
//            });

              int permisson =   PermissionUtil.getInstance().checkSinglePermission(Manifest.permission.ACCESS_COARSE_LOCATION);


        }
    };

}
