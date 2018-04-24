package com.demo.service.job;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.R;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class JobMainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "JobMainActivity";

    private JobScheduler mJobScheduler;
    private IncomingMessageHandler mHandler;

    private Button btn_startService1;
    private Button btn_startService2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_main);

        btn_startService1 = (Button) findViewById(R.id.btn_startService1);
        btn_startService2 = (Button) findViewById(R.id.btn_startService2);
        btn_startService1.setOnClickListener(this);
        btn_startService2.setOnClickListener(this);

        init();
    }

    private void init() {
        mHandler = new IncomingMessageHandler(this);
        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        Intent startServiceIntent = new Intent(this, MyJobService.class);
        Messenger messengerIncoming = new Messenger(mHandler);
        startServiceIntent.putExtra("messenger", messengerIncoming);
        startService(startServiceIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void startJobService1() {
        Log.e(TAG, "startJobService--->with Periodic");
        startJob(true);
    }

    public void startJobService2() {
        Log.e(TAG, "startJobService--->with Deadline");
        startJob(false);
    }

    private int jobId = 0;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startJob(boolean isPeriodic) {
        jobId++;
        ComponentName jobService = new ComponentName(this, MyJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(jobId, jobService);

        if (isPeriodic) {
            builder.setPeriodic(3000);
        } else {
            builder.setMinimumLatency(1000);
            builder.setOverrideDeadline(1000 * 30);
        }

        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE); // 设置网络条件
        builder.setRequiresCharging(false);    // 设置是否充电
        builder.setRequiresDeviceIdle(false);  // 设置手机是否空闲
        JobInfo jobInfo = builder.build();

        mJobScheduler.schedule(jobInfo);
    }

    public static final int MSG_JOB_START = 0;
    public static final int MSG_JOB_STOP = 1;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_startService1) {
            startJobService1();
        } else if (id == R.id.btn_startService2) {
            startJobService2();
        }
    }

   static class IncomingMessageHandler extends Handler {
        private WeakReference<JobMainActivity> mActivity;
        private String mContent = "";

        IncomingMessageHandler(JobMainActivity activity) {
            this.mActivity = new WeakReference<>(activity);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            JobMainActivity mainActivity = mActivity.get();
            if (mainActivity == null) {
                return;
            }

            TextView tv_content = (TextView) mainActivity.findViewById(R.id.tv_content);

            JobParameters parameters = (JobParameters) msg.obj;
            int jobId = parameters.getJobId();
            switch (msg.what) {
                case MSG_JOB_START:
                    mContent += "MSG_JOB_START---> jobId = " + jobId
                            + " time = " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(new Date(System.currentTimeMillis())) + "\n";
                    tv_content.setText(mContent);
                    break;
                case MSG_JOB_STOP:
                    mContent += "MSG_JOB_STOP---> jobId = " + jobId
                            + " time = " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(new Date(System.currentTimeMillis())) + "\n \n";
                    tv_content.setText(mContent);

            }
        }
    }
}
