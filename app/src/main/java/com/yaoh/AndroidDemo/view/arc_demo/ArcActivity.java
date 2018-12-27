package com.yaoh.AndroidDemo.view.arc_demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yaoh.AndroidDemo.R;

public class ArcActivity extends AppCompatActivity implements View.OnClickListener {

    private SportProgressView sportview;
    private Button btn_set_progress;

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc);

        sportview = (SportProgressView) findViewById(R.id.sportview);
        sportview.setOnClickListener(this);

        btn_set_progress = (Button) findViewById(R.id.btn_set_progress);
        btn_set_progress.setOnClickListener(this);

        img = findViewById(R.id.img);
//        img.setImageBitmap(createBitmap());

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_mode_select_bg2);
        Matrix matrix = new Matrix();
        matrix.postScale(2f, 2f, bitmap.getWidth() / 2f, bitmap.getHeight() / 2f);

        img.setImageMatrix(matrix);
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

    private Bitmap createBitmap() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_mode_select_bg2);

        Matrix matrix = new Matrix();
        matrix.postScale(2f, 2f, bitmap.getWidth(), bitmap.getHeight());
        img.setImageMatrix(matrix);

        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return newBitmap;
    }
}
