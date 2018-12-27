package com.yaoh.AndroidDemo.service.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by yaoh on 2017/12/20.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    private static final String TAG = MyJobService.class.getSimpleName();

    private Messenger mActivityMessenger;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "Service created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "Service destroyed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mActivityMessenger = intent.getParcelableExtra("messenger");
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "onStartJob  jobId = " + params.getJobId());
        new SimpleTask().execute(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG, "onStopJob jobId = " + params.getJobId());
        return false;
    }

    private class SimpleTask extends AsyncTask<JobParameters, Void, String> {

        private JobParameters mJobParam;

        @Override
        protected String doInBackground(JobParameters... params) {
            mJobParam = params[0];
            sendMessage(JobMainActivity.MSG_JOB_START, mJobParam);
            try {
                Thread.sleep(3000);
                return new String("任务执行结束--->");
            } catch (Exception e) {
                return "ERROR";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            jobFinished(mJobParam, false);
            Log.e(TAG, "获取结果：" + result);
            sendMessage(JobMainActivity.MSG_JOB_STOP, mJobParam);
        }
    }

    private void sendMessage(int messageID, @Nullable Object params) {
        if (mActivityMessenger == null) {
            Log.d(TAG, "Service is bound, not started. There's no callback to send a message to.");
            return;
        }

        Message m = Message.obtain();
        m.what = messageID;
        m.obj = params;
        try {
            mActivityMessenger.send(m);
        } catch (RemoteException e) {
            Log.e(TAG, "Error passing service object back to activity.");
        }
    }
}
