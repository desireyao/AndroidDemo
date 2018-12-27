package com.yaoh.AndroidDemo.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Package com.yaoh.demo.coord_demo.custom3.
 * Created by yaoh on 2017/10/27.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class CustomScrollView extends ScrollView {

    private OnScrollChangeListener mOnScrollChangeListener;

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        mOnScrollChangeListener = onScrollChangeListener;
    }

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     *
     *定义一个滚动接口
     * */

    public interface OnScrollChangeListener{
        void onScrollChanged(CustomScrollView scrollView,int l, int t, int oldl, int oldt);
    }

    /**
     * 当scrollView滑动时系统会调用该方法,并将该回调放过中的参数传递到自定义接口的回调方法中,
     * 达到scrollView滑动监听的效果
     *
     * */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mOnScrollChangeListener!=null){
            mOnScrollChangeListener.onScrollChanged(this,l,t,oldl,oldt);
        }
    }

}
