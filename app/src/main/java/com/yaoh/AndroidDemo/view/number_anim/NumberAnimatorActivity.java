package com.yaoh.AndroidDemo.view.number_anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.yaoh.AndroidDemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NumberAnimatorActivity extends AppCompatActivity {

    private static final String TAG = "NumberAnimatorActivity";

    @BindView(R.id.tv_number)
    TextView tv_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_animator);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(10, 100);
        animator.setDuration(5000);
//        animator.setInterpolator(new AccelerateInterpolator());
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG, "onAnimationEnd--->");
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int mValue = (int) animation.getAnimatedValue();
                Log.e(TAG, "onAnimationUpdate--->mValue = " + mValue);
                tv_number.setText(String.valueOf(mValue));
            }
        });
        animator.start();
    }
}
