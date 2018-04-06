package com.demo.zip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.demo.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZipDemoActivity extends AppCompatActivity {
    private static final String TAG = "ZipDemoActivity";

    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_demo);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_startZip)
    void startZip() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Log.e(TAG, "开始压缩--->" + format.format(new Date(System.currentTimeMillis())));

        StringBuffer sb = new StringBuffer();
        sb.append("开始压缩--->" + format.format(new Date(System.currentTimeMillis())));
        text.setText(sb.toString());
        try {
            boolean success = ZipUtils.zipFile(ZipUtils.RES_FILE_PATH, ZipUtils.ZIP_FILE_PATH);
            Log.e(TAG, "结束压缩--->" + success + "\t" + format.format(new Date(System.currentTimeMillis())));
            sb.append("\n" + "结束压缩--->" + success + " \t" + format.format(new Date(System.currentTimeMillis())));
            text.setText(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
