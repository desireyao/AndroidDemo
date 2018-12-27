package com.yaoh.AndroidDemo.listview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yaoh.AndroidDemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    private static final String TAG = "ListViewActivity";

    List<String> mData = new ArrayList<>();
    private int mCount;

    private MyAdapter myAdapter;

    @BindView(R.id.listview)
    ListView mListView;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.bind(this);

        mListView.setOnScrollListener(this);
        myAdapter = new MyAdapter();
        mListView.setAdapter(myAdapter);
        mHandler.postDelayed(mTask, 0);
    }

    Runnable mTask = new Runnable() {
        @Override
        public void run() {
            mCount++;
            for (int i = 0; i < 100; i++) {
                mData.add("this is data " + mCount);
            }

//          mHandler.postDelayed(mTask, 1000);
            myAdapter.notifyDataSetChanged();
            Log.e(TAG, "mData.size() = " + mData.size());
        }
    };

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.e(TAG, "getScrollY--->" + getScrollY());


    }

    public int getScrollY() {
        View c = mListView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview, null);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_name);
            tv_content.setText(mData.get(position));

            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
