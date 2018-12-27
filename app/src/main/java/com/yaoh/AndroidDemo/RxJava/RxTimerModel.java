package com.yaoh.AndroidDemo.RxJava;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yaoh on 2018/12/24.
 */

public class RxTimerModel {

    private static final String TAG = "RxTimerModel";

    public static RxTimerModel get() {
        return new RxTimerModel();
    }

    public void startTimer() {
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, " onSubscribe---> ");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, " onNext---> aLong : " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, " onNext---> onComplete");
                    }
                });
    }

    public void stopTimer() {

    }

    public void startTimer2() {
        int count_time = 10;
        Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
//                .take(count_time)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        Log.e(TAG, " apply ---> aLong = " + aLong + " currentThreadName : " + Thread.currentThread().getName());
//                        return count_time - aLong;
                        return 100L;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, " onSubscribe ---> ");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.e(TAG, " onNext---> value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, " onError ---> ");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, " onComplete ---> ");
                    }
                });
    }

}
