package com.yaoh.AndroidDemo.mymapview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yaoh.AndroidDemo.R;
import com.mapview.MapPointWithTitleView;
import com.mapview.MapView;
import com.mapview.ViewUtil;

import java.io.IOException;
import java.io.InputStream;

public class MainMyMapViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainMyMapViewActivity";

    private MapView mapView;

    private Button btn_test;
    private Button btn_test1;

    private ImageView img_flag_center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_my_map_view);

        btn_test = (Button) findViewById(R.id.btn_test);
        btn_test.setOnClickListener(this);

        btn_test1 = (Button) findViewById(R.id.btn_test1);
        btn_test1.setOnClickListener(this);

        img_flag_center = (ImageView) findViewById(R.id.img_flag_center);
        mapView = (MapView) findViewById(R.id.myMapView);

        img_flag_center.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "img_flag_center-----> getLeft() = " + img_flag_center.getLeft()
                        + " getRight() = " + img_flag_center.getRight()
                        + " getTop() = " + img_flag_center.getTop()
                        + " getBottom() = " + img_flag_center.getBottom()
                        + " getX() = " + img_flag_center.getX()
                        + " getY() = " + img_flag_center.getY()
                        + " getWidth() = " + img_flag_center.getWidth()
                        + " getHeight() = " + img_flag_center.getHeight());
            }
        });

        try {
            InputStream open = getResources().getAssets().open("zx_office_map.png");
            Bitmap bitmap = BitmapFactory.decodeStream(open);
            mapView.setMapBitmap(bitmap);
            Log.e(TAG, "bitmap------> width = " + bitmap.getWidth() + " height = " + bitmap.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addTestPoint() {
        MapPointWithTitleView mapPoint = new MapPointWithTitleView(this, 2411, 2687,
                ViewUtil.getBitmapFormAsset(this, MapView.ICON_POINT_RED));
//        mapPoint.setPointIcon();
        mapView.addMapPoint(mapPoint);
    }

    @Override
    public void onClick(View v) {
//        myMapView.addMapLine(0, 0);
//        myMapView.addMapLine(1500, 1500);
        int id = v.getId();
        if (id == R.id.btn_test) {
            mapView.addCenterPoint();
        } else if (id == R.id.btn_test1) {
            addTestPoint();
        }
    }

}
