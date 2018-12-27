package com.yaoh.AndroidDemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yaoh.AndroidDemo.RxJava.RxJavaActivity;
import com.yaoh.AndroidDemo.aidl.AIDL1Activity;
import com.yaoh.AndroidDemo.beacon.BeaconTestActivity;
import com.yaoh.AndroidDemo.bitmap.BitmapActivity;
import com.yaoh.AndroidDemo.bluetooth.autopair.AutoPairActivity;
import com.yaoh.AndroidDemo.bluetooth.normal.BluetoothActivity;
import com.yaoh.AndroidDemo.coord_demo.ScrollingActivity;
import com.yaoh.AndroidDemo.coord_demo.custom2.CustomCoord2Activity;
import com.yaoh.AndroidDemo.coord_demo.custom4.Scroll2Activity;
import com.yaoh.AndroidDemo.display.DisplayDemoActivity;
import com.yaoh.AndroidDemo.dm.view.TestMVPActivity;
import com.yaoh.AndroidDemo.listview.ListViewActivity;
import com.yaoh.AndroidDemo.mymapview.MainMyMapViewActivity;
import com.yaoh.AndroidDemo.permission.PermissonActivity;
import com.yaoh.AndroidDemo.radiobutton.RadioButtonActivity;
import com.yaoh.AndroidDemo.handler.HandlerActivity;
import com.yaoh.AndroidDemo.service.normal.ServiceTestActivity;
import com.yaoh.AndroidDemo.service.job.JobMainActivity;
import com.yaoh.AndroidDemo.view.arc_demo.ArcActivity;
import com.yaoh.AndroidDemo.view.attained.ContinueAttainedActivity;
import com.yaoh.AndroidDemo.view.heart_rate.HeartRateActivity;
import com.yaoh.AndroidDemo.view.myview.MyViewActivity;
import com.yaoh.AndroidDemo.view.number_anim.NumberAnimatorActivity;
import com.yaoh.AndroidDemo.view.recy.RecyPullRefreshActivity;
import com.yaoh.AndroidDemo.view.scrlloview.ScrollViewActivity;
import com.yaoh.AndroidDemo.view.seekbar.SeekBarActivity;
import com.yaoh.AndroidDemo.view.sleep.SleepViewActivity;
import com.yaoh.AndroidDemo.view.sport_detail.SportDetailViewActivity;
import com.yaoh.AndroidDemo.view.msloadingview.TestViewActivity;
import com.yaoh.AndroidDemo.view.xfermode.XferModeActivity;
import com.yaoh.AndroidDemo.xrecyclerview.XRecyclerViewActivity;
import com.yaoh.AndroidDemo.zip.ZipDemoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_arc;
    private Button btn_sport_detail;
    private Button btn_sport_history;
    private Button btn_heart_rate;
    private Button btn_current_sleep;
    private Button btn_xfermode;
    private Button btn_attained;
    private Button btn_recyclerview_pull_refresh;
    private Button btn_mvp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolBar();

        initView();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView tv = (TextView) findViewById(R.id.toolbar_title);
        tv.setText("AndroidDemo");

//        toolbar.inflateMenu(R.menu.base_toolbar_menu);  //设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_item1) {
//                    Toast.makeText(MainActivity.this, "item_01", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    private void initView() {
        btn_arc = (Button) findViewById(R.id.btn_arc);
        btn_arc.setOnClickListener(this);

        btn_sport_detail = (Button) findViewById(R.id.btn_sport_detail);
        btn_sport_detail.setOnClickListener(this);

        btn_sport_history = (Button) findViewById(R.id.btn_sport_history);
        btn_sport_history.setOnClickListener(this);

        btn_heart_rate = (Button) findViewById(R.id.btn_heart_rate);
        btn_heart_rate.setOnClickListener(this);

        btn_current_sleep = (Button) findViewById(R.id.btn_current_sleep);
        btn_current_sleep.setOnClickListener(this);

        btn_xfermode = (Button) findViewById(R.id.btn_xfermode);
        btn_xfermode.setOnClickListener(this);

        btn_attained = (Button) findViewById(R.id.btn_attained);
        btn_attained.setOnClickListener(this);

        btn_recyclerview_pull_refresh = (Button) findViewById(R.id.btn_recyclerview_pull_refresh);
        btn_recyclerview_pull_refresh.setOnClickListener(this);

        btn_mvp = (Button) findViewById(R.id.btn_mvp);
        btn_mvp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_arc:
                startActivity(new Intent(this, ArcActivity.class));
                break;
            case R.id.btn_sport_detail:
                startActivity(new Intent(this, SportDetailViewActivity.class));
                break;
            case R.id.btn_sport_history:
                startActivity(new Intent(this, TestViewActivity.class));
                break;
            case R.id.btn_heart_rate:
                startActivity(new Intent(this, HeartRateActivity.class));
                break;
            case R.id.btn_current_sleep:
                startActivity(new Intent(this, SleepViewActivity.class));
                break;
            case R.id.btn_xfermode:
                startActivity(new Intent(this, XferModeActivity.class));
                break;
            case R.id.btn_attained:
                startActivity(new Intent(this, ContinueAttainedActivity.class));
                break;
            case R.id.btn_recyclerview_pull_refresh:
                startActivity(new Intent(this, RecyPullRefreshActivity.class));
                break;
            case R.id.btn_mvp:
                startActivity(new Intent(this, TestMVPActivity.class));
                break;
//            case R.id.btn_zip:
//                startActivity(new Intent(this, ZipDemoActivity.class));
//                break;
        }
    }

    @OnClick(R.id.btn_zip)
    void startToZipDemoActivity() {
        startActivity(new Intent(this, ZipDemoActivity.class));
    }

    @OnClick(R.id.btn_radio_group)
    void startToRadioGoupActivity() {
        startActivity(new Intent(this, RadioButtonActivity.class));
    }

    @OnClick(R.id.btn_TEST)
    void startToTestActivity() {
        startActivity(new Intent(this, HandlerActivity.class));
    }

    @OnClick(R.id.btn_service_test)
    void startToServiceTestActivity() {
        startActivity(new Intent(this, ServiceTestActivity.class));
    }

    @OnClick(R.id.btn_bluetooth)
    void startTobluetoothActivity() {
        startActivity(new Intent(this, BluetoothActivity.class));
    }

    @OnClick(R.id.btn_bluetooth2)
    void startTobluetoothActivity2() {
        startActivity(new Intent(this, AutoPairActivity.class));
    }

    @OnClick(R.id.btn_listview)
    void startToListView() {
        startActivity(new Intent(this, ListViewActivity.class));
    }

    @OnClick(R.id.btn_scrolling)
    void startToScroling() {
        startActivity(new Intent(this, ScrollingActivity.class));
    }

    @OnClick(R.id.btn_Coording)
    void startToCooring() {
        startActivity(new Intent(this, CustomCoord2Activity.class));
    }

    @OnClick(R.id.btn_scroll2)
    void startToScroll2() {
        startActivity(new Intent(this, Scroll2Activity.class));
    }

    @OnClick(R.id.btn_num_anim)
    void startToNumAnim() {
        startActivity(new Intent(this, NumberAnimatorActivity.class));
    }


    @OnClick(R.id.btn_scrollview)
    void startToScrollviewActivty() {
        startActivity(new Intent(this, ScrollViewActivity.class));
    }

    @OnClick(R.id.btn_jobservice)
    void startToJobMainActivty() {
        startActivity(new Intent(this, JobMainActivity.class));
    }

    @OnClick(R.id.btn_permisson)
    void startToPermissonActivity() {
        startActivity(new Intent(this, PermissonActivity.class));
    }

    @OnClick(R.id.btn_bitmap)
    void startToBitmapActivity() {
        startActivity(new Intent(this, BitmapActivity.class));
    }

    @OnClick(R.id.btn_xrecyclerview)
    void startToXRecyclerviewActivity() {
        startActivity(new Intent(this, XRecyclerViewActivity.class));
    }

    @OnClick(R.id.btn_waveviewactivity)
    void startToWaveViewActivity() {
//        startActivity(new Intent(this, WaveView2Activity.class));
    }

    @OnClick(R.id.btn_seekbaractivity)
    void startToSeekbarActivity() {
        startActivity(new Intent(this, SeekBarActivity.class));
    }

    @OnClick(R.id.btn_aidl_activity)
    void startToAidlActivity() {
        startActivity(new Intent(this, AIDL1Activity.class));
    }

    @OnClick(R.id.btn_bean_activity)
    void startToBeaconActivity() {
        startActivity(new Intent(this, BeaconTestActivity.class));
    }

    @OnClick(R.id.btn_beacool_map_activity)
    void startToBeaconMapActivity() {
        startActivity(new Intent(this, MainMyMapViewActivity.class));
    }

    @OnClick(R.id.btn_view_handler)
    void startToMyViewActivity() {
        startActivity(new Intent(this, MyViewActivity.class));
    }

    @OnClick(R.id.btn_rxjava)
    void startToRxJavaActivity() {
        startActivity(new Intent(this, RxJavaActivity.class));
    }

    @OnClick(R.id.btn_display_demo)
    void startToDisplayActivity() {
        startActivity(new Intent(this, DisplayDemoActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        return true;
    }
}
