package com.yaoh.AndroidDemo.view.sleep;

/**
 * Package com.beacool.morethan.ui.widgets.sleep.
 * Created by yaoh on 2017/07/19.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class SleepViewData {
    private int status;
    private float percent;

    public SleepViewData(int status, float percent) {
        this.status = status;
        this.percent = percent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}
