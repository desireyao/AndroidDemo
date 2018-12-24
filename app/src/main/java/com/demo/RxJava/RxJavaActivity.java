package com.demo.RxJava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.demo.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava原理可总结为：被观察者 （Observable） 通过 订阅（Subscribe） 按顺序发送事件 给观察者 （Observer），
 * 观察者（Observer） 按顺序接收事件 & 作出对应的响应动作
 */

public class RxJavaActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

//        test();
//        test2();
        test7();
    }


    private void test() {

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });


        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "对Next事件作出响应" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        };

        // 1. 创建观察者 （Observer ）对象
//        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
//
//            // 2. 创建对象时通过对应复写对应事件方法 从而 响应对应事件
//            // 观察者接收事件前，默认最先调用复写 onSubscribe（）
//            @Override
//            public void onSubscribe(Subscription s) {
//                Log.d(TAG, "开始采用subscribe连接");
//            }
//
//            // 当被观察者生产Next事件 & 观察者接收到时，会调用该复写方法 进行响应
//            @Override
//            public void onNext(Integer value) {
//                Log.d(TAG, "对Next事件作出响应" + value);
//            }
//
//            // 当被观察者生产Error事件& 观察者接收到时，会调用该复写方法 进行响应
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "对Error事件作出响应");
//            }
//
//            // 当被观察者生产Complete事件& 观察者接收到时，会调用该复写方法 进行响应
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "对Complete事件作出响应");
//            }
//        };

        observable.subscribe(observer);
    }


    private void test2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "对Next事件" + value + "作出响应" + " currentThread = " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }


    private void test3() {

        Observable.just("hello").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept---> s = " + s);
            }
        });
    }


    private void test4() {
        // 1. 设置需要传入的数组
        Integer[] items = {0, 1, 2, 3, 4};
        // 2. 创建被观察者对象（Observable）时传入数组
        // 在创建后就会将该数组转换成Observable & 发送该对象中的所有数据

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Observable.fromIterable(list);

        Observable.fromArray(items)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });

    }

    Integer i = 10;

    private void test5() {

        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });

        // 第2次对i赋值
        i = 15;

        // <-- 3. 观察者开始订阅 ->>
        // 注：此时，才会调用defer（）创建被观察者对象（Observable）
        observable.subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到的整数是" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    private void test6() {
        // 参数说明：
        // 参数1 = 第1次延迟时间；
        // 参数2 = 间隔时间数字；
        // 参数3 = 时间单位；
        // 该例子发送的事件序列特点：延迟3s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个
        Observable.interval(3, 1, TimeUnit.SECONDS);

        // 参数说明：
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 参数3 = 第1次事件延迟发送时间；
        // 参数4 = 间隔时间数字；
        // 参数5 = 时间单位
        Observable.intervalRange(3, 10, 2, 1, TimeUnit.SECONDS);
        // 该例子发送的事件序列特点：
        // 1. 从3开始，一共发送10个事件；
        // 2. 第1次延迟2s发送，之后每隔2秒产生1个数字（从0开始递增）


        // 参数说明：
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 注：若设置为负数，则会抛出异常
        Observable.range(3, 10);
        // 该例子发送的事件序列特点：从3开始发送，每次发送事件递增1，一共发送10个事件

        // 该例子 = 延迟2s后，发送一个long类型数值
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });

        // 注：timer操作符默认运行在一个新线程上
        // 也可自定义线程调度器（第3个参数）：timer(long,TimeUnit,Scheduler)
    }

    private void test7() {
        Observable.interval(2, 1, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, " test7 accept---> aLong = " + aLong);
//                        Observable<Translation> observable = request.getCall();
//                        // d. 通过线程切换发送网络请求
//                        observable.subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
//                                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
//                                .subscribe(new Observer<Translation>() {
//                                    @Override
//                                    public void onSubscribe(Disposable d) {
//                                    }
//
//                                    @Override
//                                    public void onNext(Translation result) {
//                                        // e.接收服务器返回的数据
//                                        result.show() ;
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        Log.d(TAG, "请求失败");
//                                    }
//
//                                    @Override
//                                    public void onComplete() {
//
//                                    }
//                                });

                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, " test7 onSubscribe---> aLong = ");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, " test7 onNext---> aLong = " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


}
