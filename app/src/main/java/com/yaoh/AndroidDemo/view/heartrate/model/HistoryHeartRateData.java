package com.yaoh.AndroidDemo.view.heartrate.model;

/**
 * Package com.yaoh.demo.view.heartrate.model.
 * Created by yaoh on 2017/03/15.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:心率数据
 */
public class HistoryHeartRateData {
    private int time;
    private int rate;

    public HistoryHeartRateData(int time, int rate) {
        this.time = time;
        this.rate = rate;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
