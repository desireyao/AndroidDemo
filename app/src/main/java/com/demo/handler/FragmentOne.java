package com.demo.handler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentOne extends Fragment {
    private static final String TAG = "FragmentOne";

    private static final String ARG_PARAM1 = "param";
    private String mParam;
    private int mCount;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.text)
    TextView text;

    public FragmentOne() {
        // Required empty public constructor
    }


//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Log.e(TAG, "isAdded = " + isAdded());
//            if (isAdded()) {
//                Log.e(TAG, getString(R.string.app_name) + "," + mCount);
//            }
//        }
//    };

    private MyHandler mHandler;
    static class MyHandler extends Handler {
        private final WeakReference<Activity> mActivity;

        public MyHandler(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = mActivity.get();
            Log.e(TAG, activity.getString(R.string.app_name) + ",");
//          text.setText(getString(R.string.app_name) + "," + mCount);
        }
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentOne newInstance(String param1) {
        FragmentOne fragment = new FragmentOne();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM1);
            Log.d(TAG, "mParam = " + mParam);
        }

        mHandler = new MyHandler(getActivity());
        new Thread(new MyTask()).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e(TAG, "onCreateView" + " getActivity = " + getActivity());
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.button)
    public void onButton() {
        String result = "onButton--->";
        if (mListener != null) {
            mListener.onFragmentInteraction(result);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach, context = " + context + ",getActivity = " + getActivity());

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String result);
    }

    class MyTask implements Runnable {
        @Override
        public void run() {
            try {
                mCount++;
                Log.e(TAG, "mCount = " + mCount);
                mHandler.sendEmptyMessageDelayed(0, 5 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy--->");
        mHandler.removeCallbacksAndMessages(null);
    }
}
